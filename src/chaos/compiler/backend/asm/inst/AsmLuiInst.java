package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.operand.AsmImmediate;
import chaos.compiler.backend.asm.operand.AsmRegister;

public class AsmLuiInst extends AsmBaseInst{

    public AsmLuiInst(AsmRegister rd, AsmImmediate imm, AsmBlock parentBlock) {
        super(rd, null, null, imm, parentBlock);
    }

    @Override
    public AsmBaseInst copy() {
        return new AsmLuiInst(rd, imm, null);
    }

    @Override
    public String format() {
        return String.format("%s\t%s, %s", "lui", rd, imm);
    }

}
