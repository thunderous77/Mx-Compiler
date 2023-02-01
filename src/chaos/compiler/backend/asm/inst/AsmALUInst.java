package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.operand.AsmImmediate;
import chaos.compiler.backend.asm.operand.AsmRegister;

public class AsmALUInst extends AsmBaseInst {

    public final String op;

    public AsmALUInst(String op, AsmRegister rd, AsmRegister rs1, AsmRegister rs2, AsmBlock parentBlock) {
        super(rd, rs1, rs2, null, parentBlock);
        this.op = op;
    }

    public AsmALUInst(String op, AsmRegister rd, AsmRegister rs1, AsmImmediate imm, AsmBlock parentBlock) {
        super(rd, rs1, null, imm, parentBlock);
        this.op = op;
    }

    public AsmALUInst(String op, AsmRegister rd, AsmRegister rs1, AsmBlock parentBlock) {
        super(rd, rs1, null, null, parentBlock);
        this.op = op;
    }

    @Override
    public String format() {
        // add rd, rs1, rs2
        if (this.imm != null) return String.format("%s\t%s, %s, %s", op + "i", rd, rs1, imm);
        // addi rd, rs1, imm
        else if (this.rs2 != null) return String.format("%s\t%s, %s, %s", op, rd, rs1, rs2);
        else return String.format("%s\t%s, %s", op, rd, rs1);
    }

}
