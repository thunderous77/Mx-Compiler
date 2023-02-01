package chaos.compiler.backend.asm.operand;

public class AsmOffsetStack extends AsmImmediate{

    public enum StackType{callerArgs, alloca, spill, calleeArgs, lowerSp, raiseSp}

    public StackType stackType;

    public AsmOffsetStack(int offset, StackType stackType) {
        super(offset);
        this.stackType = stackType;
    }



}
