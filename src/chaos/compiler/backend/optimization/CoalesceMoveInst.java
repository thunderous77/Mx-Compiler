package chaos.compiler.backend.optimization;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.inst.AsmBaseInst;
import chaos.compiler.backend.asm.inst.AsmMvInst;

import java.util.HashSet;

public class CoalesceMoveInst {

    public void runOnFunc(AsmFunction function) {
        for (AsmBlock block : function.blockList) {
            block.instList.removeIf(inst -> inst instanceof AsmMvInst && inst.rd.color == inst.rs1.color);
        }

        for (AsmBlock block : function.blockList) {
            HashSet<AsmBaseInst> toRemoveSet = new HashSet<>();

            for (int i = 0; i < block.instList.size()-1; i++)
                // move twice
                if (block.instList.get(i) instanceof AsmMvInst && block.instList.get(i+1) instanceof AsmMvInst &&
                        block.instList.get(i).rd.color == block.instList.get(i+1).rd.color) {
                    toRemoveSet.add(block.instList.get(i));
                }

            block.instList.removeAll(toRemoveSet);
        }
    }

}
