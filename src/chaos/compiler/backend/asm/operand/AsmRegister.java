package chaos.compiler.backend.asm.operand;

public class AsmRegister extends AsmBaseOperand {

    public String name;
    public int color = 0;
    public AsmImmediate offset;

    public AsmRegister(String name) {
        this.name = name;
        offset = new AsmImmediate(0);
    }

    public void setOffset(AsmImmediate offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return name;
    }
}
