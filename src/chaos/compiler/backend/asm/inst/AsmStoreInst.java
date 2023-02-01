package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.AsmTranslator;
import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.operand.AsmImmediate;
import chaos.compiler.backend.asm.operand.AsmRegister;

public class AsmStoreInst extends AsmBaseInst {

    private final int width;

    public AsmStoreInst(int width, AsmRegister rs1, AsmRegister rs2, AsmImmediate imm, AsmBlock parentBlock) {
        super(null, rs1, rs2, imm, parentBlock);
        this.width = width;
    }

    @Override
    public String format() {
        return String.format("%s\t%s, %s(%s)", "s" + AsmTranslator.translateWidth(width), rs2, imm, rs1);
    }

}
