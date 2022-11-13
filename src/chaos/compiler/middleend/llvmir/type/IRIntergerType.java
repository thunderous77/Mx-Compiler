package chaos.compiler.middleend.llvmir.type;

public class IRIntergerType extends IRBaseType {

    public final int bitWidth;

    public static final IRIntergerType INT1 = new IRIntergerType(1);
    public static final IRIntergerType INT8 = new IRIntergerType(8);
    public static final IRIntergerType INT16 = new IRIntergerType(16);
    public static final IRIntergerType INT32 = new IRIntergerType(32);
    public static final IRIntergerType INT64 = new IRIntergerType(64);

    public IRIntergerType() {
        this.bitWidth = 32;
    }

    public IRIntergerType(int bitWidth) {
        this.bitWidth = bitWidth;
    }

    @Override
    public boolean match(IRBaseType other) {
        return other instanceof IRIntergerType && ((IRIntergerType) other).bitWidth == bitWidth;
    }

    @Override
    public int size() {
        return bitWidth / 8;
    }

    @Override
    public String toString() {
        return "i" + bitWidth;
    }

}
