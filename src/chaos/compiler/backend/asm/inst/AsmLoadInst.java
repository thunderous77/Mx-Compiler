package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.AsmTranslator;
import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.operand.AsmImmediate;
import chaos.compiler.backend.asm.operand.AsmRegister;

public class AsmLoadInst extends AsmBaseInst {

    private final int width;

    public AsmLoadInst(int width, AsmRegister rd, AsmRegister rs1, AsmImmediate imm, AsmBlock parentBlock) {
        super(rd, rs1, null, imm, parentBlock);
        this.width = width;
    }

    @Override
    public AsmBaseInst copy() {
        return new AsmLoadInst(width, rd, rs1, imm, null);
    }

    @Override
    public String format() {
        return String.format("%s\t%s, %s(%s)", "l" + AsmTranslator.translateWidth(width), rd, imm, rs1);
    }

}
