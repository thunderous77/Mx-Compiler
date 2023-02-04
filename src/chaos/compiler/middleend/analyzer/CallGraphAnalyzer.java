package chaos.compiler.middleend.analyzer;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.hierarchy.IRGlobalVariable;
import chaos.compiler.middleend.llvmir.hierarchy.IRModule;
import chaos.compiler.middleend.llvmir.instruction.IRBaseInst;
import chaos.compiler.middleend.llvmir.instruction.IRCallInst;
import chaos.compiler.middleend.llvmir.instruction.IRStoreInst;

import java.util.HashSet;
import java.util.Stack;

public class CallGraphAnalyzer {

    public static class Node {
        public IRFunction fromFunc;
        public HashSet<IRValue> glbUses = new HashSet<>(), glbDefs = new HashSet<>();

        public HashSet<IRCallInst> call = new HashSet<>();
        public HashSet<IRFunction> caller = new HashSet<>(), callee = new HashSet<>();

        // A call A, or A call B, B call A ...
        public boolean cyclic = false;

        public Node(IRFunction fromFunc) {
            this.fromFunc = fromFunc;
        }

        public void init() {
            glbUses = new HashSet<>();
            glbDefs = new HashSet<>();
            call = new HashSet<>();
            caller = new HashSet<>();
            callee = new HashSet<>();
            cyclic = false;
        }
    }

    private HashSet<IRFunction> visited = new HashSet<>();
    private Stack<IRFunction> callStack = new Stack<>();

    private void init(IRModule module) {
        module.funcList.forEach(function -> function.node.init());
    }

    private void callGraphBuild(IRModule module) {
        for (IRFunction function : module.funcList)
            for (IRBlock block : function.blockList)
                for (IRBaseInst inst : block.instList) {
                    if (inst instanceof IRCallInst) {
                        function.node.call.add((IRCallInst) inst);

                        IRFunction callee = ((IRCallInst) inst).callFunc();
                        callee.node.caller.add(function);
                        function.node.callee.add(callee);
                    }
                    // glb use: load or store
                    inst.operandList.forEach(operand -> {
                        if (operand.value instanceof IRGlobalVariable) function.node.glbUses.add(operand.value);
                    });
                    // glb def: store
                    if (inst instanceof IRStoreInst && ((IRStoreInst) inst).storePtr() instanceof IRGlobalVariable)
                        function.node.glbDefs.add(((IRStoreInst) inst).storePtr());
                }

        // my callee's use is my use
        boolean changed = true;
        while (changed) {
            changed = false;
            for (IRFunction function : module.funcList)
                for (IRFunction callee : function.node.callee)
                    for (IRValue use : callee.node.glbUses)
                        if (!function.node.glbUses.contains(use)) {
                            function.node.glbUses.add(use);
                            changed = true;
                        }
        }

        // def is not necessary to propagation because it will be considered in callee
    }

    private void callGraphAnalysis(IRFunction function) {
        if (visited.contains(function)) return;
        visited.add(function);
        callStack.push(function);

        for (IRFunction caller : callStack)
            if (function.node.callee.contains(caller)) {
                function.node.cyclic = true; // caller -> ... -> function -> caller
                break;
            }

        function.node.callee.forEach(this::callGraphAnalysis);
        callStack.pop();
    }

    public void runOnModule(IRModule module) {
        init(module);
        callGraphBuild(module);
        for (IRFunction function : module.funcList)
            if (!visited.contains(function)) callGraphAnalysis(function);
    }

}
