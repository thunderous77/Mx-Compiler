package chaos.compiler.middleend.llvmir.type;

public abstract class IRBaseType {

    abstract public boolean match(IRBaseType other);

    // byte
    abstract public int size();

    abstract public String toString();

}
