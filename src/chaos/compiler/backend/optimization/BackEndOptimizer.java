package chaos.compiler.backend.optimization;

import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.hierarchy.AsmModule;

public class BackEndOptimizer {

    public void runOnModule(AsmModule module) {
        for (AsmFunction function : module.functionList) {
            new CoalesceAddInst().runOnFunc(function);
            new CoalesceMoveInst().runOnFunc(function);
            new MergeBlock().runOnFunc(function);
//            new RemoveAddZeroInst().runOnFunc(function);
            new RemoveDefInst().runOnFunc(function);
            new RemoveLoadStoreInst().runOnFunc(function);
        }
    }

}
