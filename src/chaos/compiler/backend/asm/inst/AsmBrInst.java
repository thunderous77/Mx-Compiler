package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.operand.AsmRegister;

public class AsmBrInst extends AsmBaseInst{

    String op;
    public AsmBlock target;

    public AsmBrInst(String op, AsmRegister rs1, AsmRegister rs2, AsmBlock target, AsmBlock parentBlock) {
        super(null, rs1, rs2, null, parentBlock);
        this.op = op;
        this.target = target;
    }

    @Override
    public String format() {
        // beq rs1, rs2, target
        return String.format("%s\t%s, %s, %s", "b" + op, rs1, rs2, target);
    }

}
