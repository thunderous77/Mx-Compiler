package chaos.compiler.backend.optimization;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.inst.AsmALUInst;
import chaos.compiler.backend.asm.inst.AsmBaseInst;
import chaos.compiler.backend.asm.operand.AsmImmediate;

import java.util.HashSet;
import java.util.Objects;

public class CoalesceAddInst {

    private boolean isValidAddi(AsmBaseInst inst) {
        return inst instanceof AsmALUInst && Objects.equals(((AsmALUInst) inst).op, "add") && inst.imm != null && inst.rd.color == inst.rs1.color;
    }

    public void runOnFunc(AsmFunction function) {
        for (AsmBlock block : function.blockList) {
            HashSet<AsmBaseInst> toRemoveSet = new HashSet<>();

            for (int i = 0; i < block.instList.size() - 1; i++)
                if (isValidAddi(block.instList.get(i)) &&
                        isValidAddi(block.instList.get(i + 1)) &&
                        block.instList.get(i).rd.color == block.instList.get(i + 1).rd.color) {
                    toRemoveSet.add(block.instList.get(i));
                    block.instList.get(i + 1).imm = new AsmImmediate(block.instList.get(i).imm.value + block.instList.get(i + 1).imm.value);
                }

            block.instList.removeAll(toRemoveSet);
        }
    }

}
