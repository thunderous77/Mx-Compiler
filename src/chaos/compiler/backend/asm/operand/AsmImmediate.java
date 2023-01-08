package chaos.compiler.backend.asm.operand;

public class AsmImmediate extends AsmBaseOperand{

    public int value;

    public AsmImmediate(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
