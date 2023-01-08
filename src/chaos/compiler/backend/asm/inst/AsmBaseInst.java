package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.operand.AsmImmediate;
import chaos.compiler.backend.asm.operand.AsmRegister;


public abstract class AsmBaseInst {

    public AsmRegister rd, rs1, rs2;
    public AsmImmediate imm;

    public AsmBaseInst(AsmRegister rd, AsmRegister rs1, AsmRegister rs2, AsmImmediate imm, AsmBlock parentBlock) {
        this.rd = rd;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.imm = imm;
        if (parentBlock != null) parentBlock.addInst(this);
    }

    public abstract String format();

    public abstract void accept(AsmInstVisitor visitor);

}
