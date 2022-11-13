package chaos.compiler.middleend.llvmir.type;

public class IRVoidType extends IRBaseType {

    public static final IRVoidType VOID = new IRVoidType();

    @Override
    public boolean match(IRBaseType other) {
        return other instanceof IRVoidType;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return "void";
    }

}
