package chaos.compiler.backend.optimization;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.inst.AsmALUInst;
import chaos.compiler.backend.asm.inst.AsmBaseInst;
import chaos.compiler.backend.asm.operand.AsmPhysicalReg;

public class RemoveAddZeroInst {

    public void runOnFunc(AsmFunction function) {

        for (AsmBlock block : function.blockList) {
            var it = block.instList.iterator();

            while (it.hasNext()) {
                AsmBaseInst inst = it.next();

                if (inst instanceof AsmALUInst) {
                    String op = ((AsmALUInst) inst).op;

                    switch (op) {
                        case "add":
                        case "sub":
                        case "or":
                        case "xor":
                        case "sll":
                        case "sra": {
                            if (inst.rd.color == inst.rs1.color) {
                                if (inst.imm != null && inst.imm.value == 0) it.remove();
                                if (inst.rs2 != null && !(inst.rs2.color != AsmPhysicalReg.reg("zero"))) it.remove();
                            }
                        }
                    }
                }
            }
        }

    }

}
