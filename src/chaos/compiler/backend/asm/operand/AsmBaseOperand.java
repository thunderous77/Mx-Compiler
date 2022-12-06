package chaos.compiler.backend.asm.operand;

public abstract class AsmBaseOperand {

    public String identifier;

    public AsmBaseOperand(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier;
    }

}
