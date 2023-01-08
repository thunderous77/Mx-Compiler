package chaos.compiler.backend.asm.operand;

public class AsmPhysicalReg extends AsmRegister{

    public AsmPhysicalReg(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
