package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.operand.AsmImmediate;
import chaos.compiler.backend.asm.operand.AsmRegister;

public class AsmLiInst extends AsmBaseInst{

    public AsmLiInst(AsmRegister rd, AsmImmediate imm, AsmBlock parentBlock) {
        super(rd, null, null, imm, parentBlock);
    }

    @Override
    public String format() {
        return String.format("%s\t%s, %s", "li", rd, imm);
    }

}
