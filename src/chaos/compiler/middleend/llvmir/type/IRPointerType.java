package chaos.compiler.middleend.llvmir.type;

public class IRPointerType extends IRBaseType {
    public IRBaseType pointedType;
    public int dimension;

    public static IRPointerType NULL = new IRPointerType(IRIntergerType.INT8);
    public static IRPointerType StringType = new IRPointerType(IRIntergerType.INT8);
    public IRPointerType(IRBaseType pointed) {
        this.pointedType = pointed;
        if (pointed instanceof IRPointerType) this.dimension = ((IRPointerType) pointed).dimension + 1;
        else this.dimension = 1;
    }

    @Override
    public boolean match(IRBaseType other) {
        if (other instanceof IRPointerType) {
            return (((IRPointerType) other).dimension == dimension && ((IRPointerType) other).pointedType.match(pointedType)) || other.equals(NULL);
        } else if (other instanceof IRArrayType) {
            return this.match(StringType);
        }
        return false;
    }

    @Override
    public int size() {
        return 8;
    }

    @Override
    public String toString() {
        return pointedType + "*";
    }
}
