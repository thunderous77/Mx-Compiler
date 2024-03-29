package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.operand.AsmRegister;

public class AsmMvInst extends AsmBaseInst{

    public AsmMvInst(AsmRegister rd, AsmRegister rs1, AsmBlock parentBlock) {
        super(rd, rs1, null, null, parentBlock);
    }

    @Override
    public AsmBaseInst copy() {
        return new AsmMvInst(rd, rs1, null);
    }

    @Override
    public String format() {
        // mv rd, rs1
        return String.format("%s\t%s, %s", "mv", rd, rs1);
    }

}
