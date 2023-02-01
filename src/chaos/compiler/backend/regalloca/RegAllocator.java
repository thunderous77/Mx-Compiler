package chaos.compiler.backend.regalloca;


import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.hierarchy.AsmModule;
import chaos.compiler.backend.asm.inst.AsmBaseInst;
import chaos.compiler.backend.asm.inst.AsmLoadInst;
import chaos.compiler.backend.asm.inst.AsmMvInst;
import chaos.compiler.backend.asm.inst.AsmStoreInst;
import chaos.compiler.backend.asm.operand.AsmOffsetStack;
import chaos.compiler.backend.asm.operand.AsmPhysicalReg;
import chaos.compiler.backend.asm.operand.AsmRegister;
import chaos.compiler.backend.asm.operand.AsmVirtualReg;
import chaos.utility.UnionSet;

import java.util.*;

import static chaos.compiler.backend.asm.operand.AsmPhysicalReg.phyRegMap;

public class RegAllocator {

    private static final int K = AsmPhysicalReg.assignableList.size();

    private AsmFunction curFunction;

    //reg container
    //Registers can only present in one of these
    //Registers in coalescedNodes and selectStack are "deleted"
    private final HashSet<AsmRegister>
            precolored = new LinkedHashSet<>(phyRegMap.values()),
            initial = new LinkedHashSet<>(),
            simplifyWorklist = new LinkedHashSet<>(),
            freezeWorklist = new LinkedHashSet<>(),
            spillWorklist = new LinkedHashSet<>(),
            spilledNodes = new LinkedHashSet<>(),
            coalescedNodes = new LinkedHashSet<>(),
            coloredNodes = new LinkedHashSet<>();

    private final Stack<AsmRegister> selectStack = new Stack<>();

    // moves
    // coalescedMoves: have been coalesced.
    // constrainedMoves: rd and rs have an edge
    // frozenMoves: have been frozen, no need to consider it.
    // worklistMoves and activeMoves are moves "exists"
    private final HashSet<AsmMvInst>
            coalescedMoves = new LinkedHashSet<>(),
            constrainedMoves = new LinkedHashSet<>(),
            frozenMoves = new LinkedHashSet<>(),
            worklistMoves = new LinkedHashSet<>(),
            activeMoves = new LinkedHashSet<>();

    // graph
    private final RIG G = new RIG();

    // utils
    private final UnionSet<AsmRegister> unionSet = new UnionSet<AsmRegister>();
    private final Set<AsmRegister> introducedTemp = new HashSet<>();

    public void runOnModule(AsmModule module) {
        module.functionList.forEach(this::runOnFunction);
    }

    public void runOnFunction(AsmFunction function) {
        curFunction = function;
        while (true) {
            init();

            new LivenessAnalyzer().runOnFunc(function);
            build();

            makeWorklist();

            do {
                if (!simplifyWorklist.isEmpty()) simplify();
                else if (!worklistMoves.isEmpty()) coalesce();
                else if (!freezeWorklist.isEmpty()) freeze();
                else if (!spillWorklist.isEmpty()) selectSpill();
            } while (!simplifyWorklist.isEmpty() || !worklistMoves.isEmpty() || !freezeWorklist.isEmpty() || !spillWorklist.isEmpty());

            assignColors();

            if (!spilledNodes.isEmpty()) rewriteProgram();
            else return;
        }
    }

    private void init() {
        initial.clear();
        simplifyWorklist.clear();
        freezeWorklist.clear();
        spillWorklist.clear();
        spilledNodes.clear();
        coalescedNodes.clear();
        coloredNodes.clear();
        selectStack.clear();

        coalescedMoves.clear();
        constrainedMoves.clear();
        frozenMoves.clear();
        worklistMoves.clear();
        activeMoves.clear();

        G.init();

        precolored.forEach(reg -> {
            reg.color = (AsmPhysicalReg) reg;
            reg.node.init(true);
        });

        curFunction.blockList.forEach(block -> block.instList.forEach(inst -> {
            initial.addAll(inst.uses());
            initial.addAll(inst.defs());
        }));
        initial.removeAll(precolored);
        initial.forEach(reg -> {
            reg.color = null;
            reg.node.init(false);
            unionSet.remove(reg);
        });

        for (AsmBlock block : curFunction.blockList) {
            double weight = Math.pow(10, block.loopDepth);
            block.instList.forEach(inst -> {
                inst.defs().forEach(def -> def.node.priority += weight);
                inst.uses().forEach(use -> use.node.priority += weight);
            });
        }
    }

    private void build() {

        for (AsmBlock block : curFunction.blockList) {
            HashSet<AsmRegister> lives = block.out;

            for (int i = block.instList.size()-1; i >= 0; i--) {
                AsmBaseInst inst = block.instList.get(i);
                if (inst instanceof AsmMvInst) {
                    lives.removeAll(inst.uses());
                    HashSet<AsmRegister> moveRelated = new HashSet<>(inst.defs());
                    moveRelated.addAll(inst.uses());
                    moveRelated.forEach(reg -> reg.node.moveList.add((AsmMvInst) inst));
                    worklistMoves.add((AsmMvInst) inst);
                }

                lives.add(AsmPhysicalReg.reg("zero"));
                lives.addAll(inst.defs());

                for (AsmRegister def : inst.defs())
                    for (AsmRegister live : lives)
                        G.addEdge(new RIG.Edge(def, live));

                lives.removeAll(inst.defs());
                lives.addAll(inst.uses());
            }
        }
    }

    private void makeWorklist() {
        var it = initial.iterator();
        while (it.hasNext()) {
            AsmRegister reg = it.next();
            it.remove();
            if (reg.node.degree >= K) spillWorklist.add(reg);
            else if (moveRelated(reg)) freezeWorklist.add(reg);
            else simplifyWorklist.add(reg);
        }
    }

    private void enableMoves(Set<AsmRegister> regs) {
        for (AsmRegister reg : regs)
            for (AsmMvInst move : nodeMoves(reg))
                if (activeMoves.contains(move)) {
                    activeMoves.remove(move);
                    worklistMoves.add(move);
                }
    }

    private void decrementDegree(AsmRegister reg) {
        int d = reg.node.degree;
        reg.node.degree--;
        if (d == K) {
            HashSet<AsmRegister> enableMovesWorklist = new HashSet<>(adjacent(reg));
            enableMovesWorklist.add(reg);
            enableMoves(enableMovesWorklist);
            spillWorklist.remove(reg);
            if (moveRelated(reg)) freezeWorklist.add(reg);
            else simplifyWorklist.add(reg);
        }
    }

    private void simplify() {
        var it = simplifyWorklist.iterator();
        AsmRegister reg = it.next();
        it.remove();
        selectStack.push(reg);
        adjacent(reg).forEach(this::decrementDegree);
    }

    private void addWorklist(AsmRegister reg) {
        if (!reg.node.precolored && !moveRelated(reg) && reg.node.degree < K) {
            freezeWorklist.remove(reg);
            simplifyWorklist.add(reg);
        }
    }

    private void combine(AsmRegister u, AsmRegister v) {
        if (freezeWorklist.contains(v)) freezeWorklist.remove(v);
        else spillWorklist.remove(v);
        coalescedNodes.add(v);
        unionSet.setAlias(v, u);
        u.node.moveList.addAll(v.node.moveList);
        enableMoves(Collections.singleton(v));

        for (AsmRegister t : adjacent(v)) {
            G.addEdge(new RIG.Edge(t, u));
            decrementDegree(t);
        }

        if (u.node.degree >= K && freezeWorklist.contains(u)) {
            freezeWorklist.remove(u);
            spillWorklist.add(u);
        }
    }

    private void coalesce() {
        var it = worklistMoves.iterator();
        AsmMvInst move = it.next();

        AsmRegister rdAlias = unionSet.getAlias(move.rd), rs1Alias = unionSet.getAlias(move.rs1);
        RIG.Edge edge;
        if (rs1Alias.node.precolored) edge = new RIG.Edge(rs1Alias, rdAlias);
        else edge = new RIG.Edge(rdAlias, rs1Alias);
        it.remove();

        if (edge.isLoop()) {
            coalescedMoves.remove(move);
            addWorklist(edge.u);
        }
        else if (edge.v.node.precolored || G.edgeSet.contains(edge)) {
            constrainedMoves.add(move);
            addWorklist(edge.u);
            addWorklist(edge.v);
        }
        else if ((edge.u.node.precolored && georgeCriterion(edge.u, edge.v))
                || (!edge.u.node.precolored && briggsCriterion(edge.u, edge.v))) {
            coalescedMoves.add(move);

            combine(edge.u, edge.v);
            addWorklist(edge.u);
        }
        else {
            activeMoves.add(move);
        }
    }

    private void freezeMoves(AsmRegister u) {
        for (AsmMvInst move : nodeMoves(u)) {
            AsmRegister v;
            if (unionSet.getAlias(u) == unionSet.getAlias(move.rs1)) v = unionSet.getAlias(move.rd);
            else v = unionSet.getAlias(move.rs1);
            activeMoves.remove(move);
            frozenMoves.add(move);
            if (nodeMoves(v).isEmpty() && v.node.degree < K) {
                freezeWorklist.remove(v);
                simplifyWorklist.add(v);
            }
        }
    }

    private void freeze() {
        var it = freezeWorklist.iterator();
        AsmRegister reg = it.next();
        it.remove();
        simplifyWorklist.add(reg);
        freezeMoves(reg);
    }

    private void selectSpill() {

        AsmRegister minReg = null;
        double minCost = Double.POSITIVE_INFINITY;
        for (AsmRegister reg : spillWorklist) {
            if (introducedTemp.contains(reg)) continue;
            double regCost = reg.node.priority / reg.node.degree;
            if (regCost < minCost) {
                minReg = reg;
                minCost = regCost;
            }
        }
        if (minReg == null) {
            for (AsmRegister reg : spillWorklist) {
                double regCost = reg.node.priority / reg.node.degree;
                if (regCost < minCost) {
                    minReg = reg;
                    minCost = regCost;
                }
            }
        }

        spillWorklist.remove(minReg);
        simplifyWorklist.add(minReg);
        freezeMoves(minReg);
    }

    private void assignColors() {
        while (!selectStack.empty()) {
            AsmRegister reg = selectStack.pop();
            ArrayList<AsmPhysicalReg> okColors = new ArrayList<>(AsmPhysicalReg.assignableList);

            for (AsmRegister neighbor : reg.node.adjacentNodeList) {
                var neiborAlias = unionSet.getAlias(neighbor);
                if (neiborAlias.node.precolored || coloredNodes.contains(neiborAlias))
                    okColors.remove(neiborAlias.color);
            }

            if (okColors.isEmpty()) spilledNodes.add(reg);
            else {
                coloredNodes.add(reg);
                reg.color = okColors.iterator().next();
            }
        }

        for (AsmRegister reg : coalescedNodes) {
            reg.color = unionSet.getAlias(reg).color;
        }
    }

    private void rewriteProgram() {

        for (AsmRegister reg : spilledNodes) {
            reg.offsetStack = new AsmOffsetStack(curFunction.spillStackUse, AsmOffsetStack.StackType.spill);
            curFunction.spillStackUse += 4;
        }

        for (AsmBlock block : curFunction.blockList) {
            ListIterator<AsmBaseInst> it = block.instList.listIterator();

            while (it.hasNext()) {
                AsmBaseInst inst = it.next();

                for (AsmRegister use : inst.uses()) {
                    if (use.offsetStack == null) continue;

                    if (!inst.defs().contains(use)) {
                        if (inst instanceof AsmMvInst && inst.rd.offsetStack == null) {
                            assert use.equals(inst.rs1);
                            AsmBaseInst loadInst = new AsmLoadInst(((AsmVirtualReg) use).size, inst.rd, AsmPhysicalReg.reg("sp"), use.offsetStack, null);
                            it.set(loadInst);
                        }
                        else {
                            AsmVirtualReg temp = new AsmVirtualReg(((AsmVirtualReg) use).size);
                            AsmBaseInst loadInst = new AsmLoadInst(temp.size, temp, AsmPhysicalReg.reg("sp"), use.offsetStack, null);
                            inst.replaceUse(use, temp);
                            it.previous();
                            it.add(loadInst);
                            it.next();
                            introducedTemp.add(temp);
                        }
                    }
                    else {
                        AsmVirtualReg temp = new AsmVirtualReg(((AsmVirtualReg) use).size);
                        AsmBaseInst loadInst = new AsmLoadInst(temp.size, temp, AsmPhysicalReg.reg("sp"), use.offsetStack, null);
                        AsmBaseInst storeInst = new AsmStoreInst(temp.size, temp, AsmPhysicalReg.reg("sp"), use.offsetStack, null);
                        inst.replaceUse(use, temp);
                        inst.replaceDef(use, temp);
                        it.previous();
                        it.add(loadInst);
                        it.next();
                        it.add(storeInst);
                        introducedTemp.add(temp);
                    }
                }

                for (AsmRegister def : inst.defs()) {
                    if (def.offsetStack == null) continue;

                    if (inst.uses().contains(def)) continue;
                    if (inst instanceof AsmMvInst && inst.rs1.offsetStack == null) {
                        AsmBaseInst storeInst = new AsmStoreInst(((AsmVirtualReg) def).size, AsmPhysicalReg.reg("sp"), inst.rs1, def.offsetStack, null);
                        it.set(storeInst);
                    } else {
                        AsmVirtualReg temp = new AsmVirtualReg(((AsmVirtualReg) def).size);
                        inst.replaceDef(def, temp);
                        AsmBaseInst storeInst = new AsmStoreInst(((AsmVirtualReg) def).size, AsmPhysicalReg.reg("sp"), temp, def.offsetStack, null);
                        it.add(storeInst);
                        introducedTemp.add(temp);
                    }
                }
            }
        }
    }

    private LinkedHashSet<AsmRegister> adjacent(AsmRegister reg) {
        LinkedHashSet<AsmRegister> ret = new LinkedHashSet<>(reg.node.adjacentNodeList);
        selectStack.forEach(ret::remove);
        ret.removeAll(coloredNodes);
        return ret;
    }

    private HashSet<AsmMvInst> nodeMoves(AsmRegister reg) {
        HashSet<AsmMvInst> ret = new HashSet<>();
        reg.node.moveList.forEach(move -> {
            if (activeMoves.contains(move) || worklistMoves.contains(move))
                ret.add(move);
        });
        return ret;
    }

    private boolean moveRelated(AsmRegister reg) {
        return !nodeMoves(reg).isEmpty();
    }

    private boolean ok(AsmRegister t, AsmRegister r) {
        return t.node.degree < K || t.node.precolored || G.edgeSet.contains(new RIG.Edge(t, r));
    }

    private boolean georgeCriterion(AsmRegister u, AsmRegister v) {
        for (AsmRegister t : adjacent(v)) if (!ok(t, u)) return false;
        return true;
    }
    
    private boolean briggsCriterion(AsmRegister u, AsmRegister v) {
        int k = 0;

        Set<AsmRegister> commonAdj = new HashSet<>(adjacent(u));
        commonAdj.addAll(adjacent(v));

        for (var n : commonAdj)
            if (n.node.degree >= K) k++;

        return k < K;
    }

}
