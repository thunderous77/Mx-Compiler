package chaos.compiler.backend.asm.operand;

import chaos.compiler.backend.regalloca.RIG;

public class AsmRegister extends AsmBaseOperand {

    public AsmPhysicalReg color;
    public AsmOffsetStack offsetStack;
    public RIG.Node node = new RIG.Node();

    public AsmRegister(String identifier) {
        super(identifier);
    }

    @Override
    public String toString() {
        if (color == null) {
            return identifier;
        }
        return color.identifier;
    }

}
