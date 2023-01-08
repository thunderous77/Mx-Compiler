package chaos.compiler.backend.asm.operand;

public class AsmGlobalReg extends AsmRegister {

    public String identifier = null;

    public AsmGlobalReg(String name) {
        super(name);
    }

    public AsmGlobalReg(String name, String identifier) {
        super(name);
        this.identifier = identifier;
    }

    public String Format() {
        return identifier.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\"", "\\\"");
    }

}
