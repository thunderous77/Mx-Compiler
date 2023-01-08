package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;

public class AsmJmpInst extends AsmBaseInst{

    public AsmBlock target;

    public AsmJmpInst(AsmBlock target, AsmBlock parentBlock) {
        super(null, null, null, null, parentBlock);
        this.target = target;
    }

    @Override
    public String format() {
        return String.format("%s\t%s", "j", target);
    }

    @Override
    public void accept(AsmInstVisitor visitor) {
        visitor.visit(this);
    }

}
