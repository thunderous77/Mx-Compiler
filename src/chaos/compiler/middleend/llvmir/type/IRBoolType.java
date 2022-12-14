package chaos.compiler.middleend.llvmir.type;

public class IRBoolType extends IRBaseType {

    @Override
    public boolean match(IRBaseType other) {
        return other instanceof IRBoolType;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public String toString() {
        return "i1";
    }

}
