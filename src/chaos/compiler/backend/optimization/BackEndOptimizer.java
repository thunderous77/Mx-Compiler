package chaos.compiler.backend.optimization;

import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.hierarchy.AsmModule;

public class BackEndOptimizer {

    public void runOnModule(AsmModule module) {
        for (AsmFunction function : module.functionList) {
            new CoalesceMoveInst().runOnFunc(function);
        }
    }

}
