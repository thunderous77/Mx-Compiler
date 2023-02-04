package chaos.compiler.middleend.llvmir.optimization;

import chaos.compiler.middleend.analyzer.CFGBuilder;
import chaos.compiler.middleend.analyzer.CallGraphAnalyzer;
import chaos.compiler.middleend.llvmir.IRUser;
import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.hierarchy.IRModule;
import chaos.compiler.middleend.llvmir.instruction.*;
import com.sun.jdi.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inline {

    private IRModule module;
    private final boolean forced;

    private static final int CalleeInstNumThreshold = 500,
            ForcedCalleeInstNumThreshold = 300,
            CallerInstNumThreshold = 1000,
            BlockNumThreshold = 300;

    private final ArrayList<IRCallInst> inlineAbleSet = new ArrayList<>();
    private final Map<IRFunction, Integer> instNum = new HashMap<>();

    public Inline(boolean forced) {
        this.forced = forced;
    }

    private boolean isNecessary(IRFunction function) {
        return function.name.equals("main") || module.builtinFuncList.contains(function);
    }

    private boolean canInline(IRFunction caller, IRFunction callee) {
        return !isNecessary(callee) &&
                // this is for correct order of inlining, do not use cyclic
                callee.node.call.isEmpty() &&
                instNum.get(caller) <= CallerInstNumThreshold &&
                instNum.get(callee) <= CalleeInstNumThreshold &&
                caller.blockList.size() <= BlockNumThreshold &&
                callee.blockList.size() <= BlockNumThreshold;
    }

    private boolean canForceInline(IRFunction caller, IRFunction callee) {
        return  !isNecessary(callee) &&
                instNum.get(caller) <= CallerInstNumThreshold &&
                instNum.get(callee) <= ForcedCalleeInstNumThreshold &&
                caller.blockList.size() <= BlockNumThreshold &&
                callee.blockList.size() <= BlockNumThreshold;
    }

    private void collectAbleSet() {
        inlineAbleSet.clear();
        instNum.clear();

        for (IRFunction function : module.funcList) {
            instNum.putIfAbsent(function, 0);
            for (IRBlock block : function.blockList) {
                instNum.put(function, instNum.get(function) + block.instList.size());
                // Log.info("inst num", function.name, instNum.get(function));
            }
        }

        // TR will be optimized, so no need to inline
        for (IRFunction function : module.funcList) {
            for (IRCallInst call : function.node.call)
                if (!call.isTailRecursive() && ((!forced && canInline(function, call.callFunc()))
                        || (forced && canForceInline(function, call.callFunc()))))
                    inlineAbleSet.add(call);
        }
    }

    private void replaceOperand(IRUser user, Map<IRValue, IRValue> replaceMap) {
        for (int i = 0; i< user.operandList.size(); i++) {
            if (replaceMap.containsKey(user.getOperand(i)))
                user.resetOperand(i, replaceMap.get(user.getOperand(i)));
        }
    }

    // callee's code will be inserted to caller
    private void inlining(IRCallInst call) {
        IRFunction caller = call.parentBlock.parentFunction,
                callee = call.callFunc();

        if ((forced && !canForceInline(caller, callee)) || (!forced && !canInline(caller, callee)))
            return;

        Map<IRValue, IRValue> replaceValueMap = new HashMap<>();
        Map<IRBlock, IRBlock> replaceBlockMap = new HashMap<>();

        IRBlock inlineEntry = call.parentBlock;

        // step 1. replicate the function body

        // backup the block to avoid concurrent exception in self-recursion
        ArrayList<IRBlock> calleeblockList = new ArrayList<>(callee.blockList);

        for (IRBlock block : calleeblockList) {
            IRBlock inlinedBlock = new IRBlock(block.name + ".i", caller);

            // Log.info("inline", block.identifier(), inlinedBlock.identifier());

            replaceValueMap.put(block, inlinedBlock);
            replaceBlockMap.put(block, inlinedBlock);

            // phi first, because the block will be terminated after normal inst inserted
            for (IRPhiInst phi : block.phiInstList) {
                IRPhiInst newPhi = (IRPhiInst) phi.copy();
                newPhi.setParentBlock(inlinedBlock);
                replaceValueMap.put(phi, newPhi);
            }

            for (IRBaseInst inst : block.instList) {
                IRBaseInst newInst = inst.copy();
                newInst.setParentBlock(inlinedBlock);
                replaceValueMap.put(inst, newInst);
            }
        }

        for (int i = 0; i < callee.getArgNum(); i++)
            replaceValueMap.put(callee.getArg(i), call.getArg(i));

        for (IRBlock oldBlock : replaceBlockMap.keySet()) {
            IRBlock newBlock = replaceBlockMap.get(oldBlock);

            for (IRBaseInst inst : newBlock.instList)
                replaceOperand(inst, replaceValueMap);

            for (IRPhiInst phi : newBlock.phiInstList)
                replaceOperand(phi, replaceValueMap);
        }

        //step 2. relink the block

        IRBlock inlineExit = new IRBlock("split", caller);

        // split the parentBlock of call
        boolean splitStart = false;
        var it = inlineEntry.instList.iterator();

        while (it.hasNext()) {
            IRBaseInst inst = it.next();
            if (inst == call) splitStart = true;
            if (!splitStart) continue;
            if (inst != call) inst.setParentBlock(inlineExit);
            it.remove();
        }

        // call parentBlock to inline.entry
        inlineEntry.addInstAtLast(new IRBrInst(replaceBlockMap.get(callee.entryBlock), null));

        // split to inlineEntry and inlineExit, redirect the suc to inlineExit
        inlineEntry.nextBlockList.forEach(suc -> suc.redirectPreBlock(inlineEntry, inlineExit));

        // ret -> replaceAllUses
        IRRetInst ret = (IRRetInst) replaceBlockMap.get(callee.exitBlock).terminator();
        if (!callee.isVoid()) {
            call.replaceAllUsesWith(ret.retValue());
        }

        // ret - > jump to split
        replaceBlockMap.get(callee.exitBlock).terminator().removedFromAllUsers();
        replaceBlockMap.get(callee.exitBlock).tReplaceTerminator(
                new IRBrInst(inlineExit, null)
        );

        if (caller.exitBlock == inlineEntry) caller.exitBlock = inlineExit;

        new CFGBuilder().runOnFunc(caller);
    }

    public void runOnModule(IRModule module) {
        this.module = module;
        while (true) {
            new CallGraphAnalyzer().runOnModule(module);
            collectAbleSet();

            if (inlineAbleSet.isEmpty()) break;

            // notice: pending the normal call first
            for (IRCallInst pendingCall : inlineAbleSet) {
                if (pendingCall.callFunc() != pendingCall.parentBlock.parentFunction)
                    inlining(pendingCall);
            }

            for (IRCallInst pendingCall : inlineAbleSet) {
                if (pendingCall.callFunc() == pendingCall.parentBlock.parentFunction)
                    inlining(pendingCall);
            }
        }

        new CallGraphAnalyzer().runOnModule(module);

        // remove dead function
        module.funcList.removeIf(function -> !isNecessary(function) && function.node.caller.size() == 0);
    }

}
