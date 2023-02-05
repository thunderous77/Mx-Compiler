package chaos.compiler.middleend.llvmir.optimization;

import chaos.compiler.middleend.analyzer.CFGBuilder;
import chaos.compiler.middleend.analyzer.DomTreeBuilder;
import chaos.compiler.middleend.llvmir.IRUse;
import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.constant.IRNullPointerConstant;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.hierarchy.IRGlobalVariable;
import chaos.compiler.middleend.llvmir.instruction.*;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

import java.util.*;


public class Mem2Reg {

    private final ArrayList<IRBaseInst> allocated = new ArrayList<>();

    private final Map<String, Stack<IRValue>> nameStack = new HashMap<>();

    private final Map<IRPhiInst, String> phiAllocaName = new HashMap<>();

    private void collectAllocated(IRFunction function) {
        for (IRBaseInst inst : function.entryBlock.instList)
            if (inst instanceof IRAllocaInst) allocated.add(inst);
    }

    private ArrayList<IRBaseInst> collectAllocaDefs(IRBaseInst allocaInst) {
        ArrayList<IRBaseInst> ret = new ArrayList<>();
        // alloca itself is a def
        ret.add(allocaInst);
        for (IRUse use : allocaInst.useList) {
            if (use.user instanceof IRStoreInst && ((IRStoreInst) use.user).storePtr() == allocaInst) {
                // store to %allocaPtr
                ret.add((IRBaseInst) use.user);
            }
        }
        return ret;
    }

    public void runOnFunc(IRFunction function) {
        new CFGBuilder().runOnFunc(function);
        new DomTreeBuilder(false).runOnFunc(function);
        phiInsertion(function);
        variableRenaming(function.entryBlock);
    }

    private void phiInsertion(IRFunction function) {
        collectAllocated(function);

        for (IRBaseInst allocaVar : allocated) {
            Queue<IRBlock> workQueue = new LinkedList<>();

            var defs = collectAllocaDefs(allocaVar);
            var visited = new HashSet<IRBlock>();

            defs.forEach(defInst -> workQueue.offer(defInst.parentBlock));

            while (!workQueue.isEmpty()) {
                var runner = workQueue.poll();
                for (var frontier : runner.dtNode.domFrontier) {
                    if (visited.contains(frontier)) continue;
                    visited.add(frontier);

                    workQueue.offer(frontier);
                    var phi = new IRPhiInst(((IRPointerType) allocaVar.type).pointedType, null);
                    frontier.addPhiInst(phi);
                    phiAllocaName.put(phi, allocaVar.name);
                }
            }
        }
    }

    private IRValue getReplace(String name) {
        if (!nameStack.containsKey(name) || nameStack.get(name).empty()) {
            return new IRNullPointerConstant();
        }
        return nameStack.get(name).lastElement();
    }

    private void updateReplace(String name, IRValue replace) {
        if (!nameStack.containsKey(name)) nameStack.put(name, new Stack<>());
        nameStack.get(name).push(replace);
    }

    private void variableRenaming(IRBlock block) {
        ArrayList<String> rollbackRecord = new ArrayList<>();

        for (var phi : block.phiInstList) {
            if (phiAllocaName.containsKey(phi)) {
                // Log.report("phi: ", phi.format(), phiAllocaName.get(phi));
                updateReplace(phiAllocaName.get(phi), phi);
                rollbackRecord.add(phiAllocaName.get(phi));
            }
        }

        var it = block.instList.iterator();

        while (it.hasNext()) {
            var inst = it.next();

            if (inst instanceof IRAllocaInst) {
                // remove alloca
                it.remove();
            }
            else if (inst instanceof IRLoadInst) {
                if (allocated.contains(((IRLoadInst) inst).loadPtr())) {
                    String name = ((IRLoadInst) inst).loadPtr().name;
                    IRValue replace = getReplace(name);
                    if (replace.identifier().equals("null")) {
                        // use before def, there are two conditions:
                        // 1. usage of uninitiated
                        // 2. global localization

                        // ignore Glo2Loc load inst
                        if (!(((IRLoadInst) inst).loadPtr() instanceof IRGlobalVariable)) {
                            // no global
                            it.remove();
                            inst.replaceAllUsesWith(replace);
                        }
                    } else {
                        // Log.report("load r", ((IRLoadInst) inst).loadPtr().identifier(), name, replace.identifier());
                        it.remove(); // remove load
                        inst.replaceAllUsesWith(replace);
                    }
                }
            }
            else if (inst instanceof IRStoreInst) {
                if (allocated.contains(((IRStoreInst) inst).storePtr())) {
                    String name = ((IRStoreInst) inst).storePtr().name;
                    // Log.report("store r", ((IRStoreInst) inst).storeValue().identifier(), name);
                    updateReplace(name, ((IRStoreInst) inst).storeValue());
                    rollbackRecord.add(name);
                    it.remove(); // remove store
                }
            }
        }

        for (IRBlock suc : block.nextBlockList) {
            for (var sucPhi : suc.phiInstList) {
                if (phiAllocaName.containsKey(sucPhi)) {
                    sucPhi.addBranch(getReplace(phiAllocaName.get(sucPhi)), block);
                }
            }
        }

        block.dtNode.sons.forEach(son -> variableRenaming(son.fromBlock));

        rollbackRecord.forEach(name -> nameStack.get(name).pop());
    }

}
