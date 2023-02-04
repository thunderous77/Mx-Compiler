package chaos.compiler.backend.optimization;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.inst.AsmMvInst;

public class CoalesceMoveInst {

    public void runOnFunc(AsmFunction function) {
        for (AsmBlock block : function.blockList) {
            block.instList.removeIf(inst -> inst instanceof AsmMvInst && inst.rd.color == inst.rs1.color);
        }
    }

}
