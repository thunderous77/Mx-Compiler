package chaos.compiler.middleend.llvmir.type;

// only use it to implement string
// real array is compiled to Pointer.

public class IRArrayType extends IRBaseType {
    public IRBaseType elementType;
    public int length;

    public IRArrayType(IRBaseType elementType, int length) {
        this.elementType = elementType;
        this.length = length;
    }

    @Override
    public boolean match(IRBaseType other) {
        return false;
    }

    // align size
    @Override
    public int size() {
        return elementType.size();
    }

    @Override
    public String toString() {
        return "[" + length + " x " + elementType + "]";
    }
}
