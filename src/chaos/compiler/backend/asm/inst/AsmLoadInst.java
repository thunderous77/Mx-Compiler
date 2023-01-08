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
    public String format() {
        return String.format("%s\t%s, %s(%s)", "l" + AsmTranslator.widthTranslate(width), rd, imm, rs1);
    }

    @Override
    public void accept(AsmInstVisitor visitor) {
        visitor.visit(this);
    }

}
