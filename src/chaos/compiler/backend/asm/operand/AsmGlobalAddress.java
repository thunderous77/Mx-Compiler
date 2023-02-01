package chaos.compiler.backend.asm.operand;

// deal with global variable
public class AsmGlobalAddress extends AsmImmediate{
    public enum HiLo {hi, lo};

    public AsmGlobalReg reg;

    public AsmGlobalAddress(AsmGlobalReg reg, HiLo hilo) {
        super("%"+String.format("%s(%s)", hilo, reg));
        this.reg = reg;
    }
}
