package chaos.compiler.backend.asm.operand;

public class AsmVirtualReg extends AsmRegister{

    public AsmVirtualReg(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return name + this.hashCode();
    }
}
