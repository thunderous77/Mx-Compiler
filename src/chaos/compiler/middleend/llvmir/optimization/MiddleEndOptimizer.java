package chaos.compiler.middleend.llvmir.optimization;

import chaos.compiler.middleend.analyzer.CallGraphAnalyzer;
import chaos.compiler.middleend.analyzer.LoopAnalyzer;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.hierarchy.IRModule;

public class MiddleEndOptimizer {


    public void runOnModule(IRModule module) {
        new CallGraphAnalyzer().runOnModule(module);

        for (IRFunction function : module.funcList) {
            new Mem2Reg().runOnFunc(function);
        }

        new Inline(true).runOnModule(module);

        for (IRFunction function : module.funcList) {
            new SSADestructor().runOnFunc(function);
            new LoopAnalyzer().runOnFunc(function);
        }

        new Inline(true).runOnModule(module);
    }

}
