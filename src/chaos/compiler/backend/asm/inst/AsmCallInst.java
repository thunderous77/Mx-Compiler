package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;

public class AsmCallInst extends AsmBaseInst{

    public final AsmFunction callFunc;

    public AsmCallInst(AsmFunction callFunc, AsmBlock parentBlock) {
        super(null, null, null, null, parentBlock);
        this.callFunc = callFunc;
    }

    @Override
    public String format() {
        return String.format("%s\t%s", "call", callFunc.toString());
    }

    @Override
    public void accept(AsmInstVisitor visitor) {
        visitor.visit(this);
    }

}
