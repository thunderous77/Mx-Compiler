package chaos.compiler.backend.asm.operand;

public class AsmImmediate extends AsmBaseOperand{

    public int value;

    public AsmImmediate(int value) {
        super(String.valueOf(value));
        this.value = value;
    }

    // especially for AsmGlobalAddress
    public AsmImmediate(String identifier) {
        super(identifier);
        this.value = 0;
    }
}
