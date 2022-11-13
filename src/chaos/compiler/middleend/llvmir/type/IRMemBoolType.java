package chaos.compiler.middleend.llvmir.type;

public class IRMemBoolType extends IRBoolType {

    @Override
    public boolean match(IRBaseType other) {
        return other instanceof IRMemBoolType;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public String toString() {
        return "i8";
    }

}
