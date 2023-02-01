package chaos.compiler.backend.asm.operand;

public class AsmVirtualReg extends AsmRegister{

    public static int virtualRegNum = 0;

    public static void resetRegNum() {
        virtualRegNum = 0;
    }

    public final int num, size;

    public AsmVirtualReg() {
        super("v"+virtualRegNum);
        this.num = virtualRegNum;
        this.size = 4;
        virtualRegNum++;
    }

    public AsmVirtualReg(int size) {
        super("v"+virtualRegNum);
        this.num = virtualRegNum;
        this.size = size;
        virtualRegNum++;
    }

}
