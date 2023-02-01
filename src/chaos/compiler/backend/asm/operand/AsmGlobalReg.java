package chaos.compiler.backend.asm.operand;

public class AsmGlobalReg extends AsmRegister {

    public String stringConst = null;

    public AsmGlobalReg(String identifier) {
        super(identifier);
    }

    public AsmGlobalReg(String identifier, String stringConst) {
        super(identifier);
        this.stringConst = stringConst;
    }

    public String stringFormat() {
        return stringConst.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\0", "")
                .replace("\t", "\\t")
                .replace("\"", "\\\"");
    }

}
