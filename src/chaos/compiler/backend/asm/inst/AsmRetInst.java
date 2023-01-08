package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.operand.AsmRegister;

public class AsmRetInst extends AsmBaseInst{

    public AsmRetInst(AsmBlock parentBlock) {
        super(null, null, null, null, parentBlock);
    }

    @Override
    public String format() {
        return "ret";
    }

    @Override
    public void accept(AsmInstVisitor visitor) {
        visitor.visit(this);
    }

}
