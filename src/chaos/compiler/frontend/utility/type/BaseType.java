package chaos.compiler.frontend.utility.type;

public abstract class BaseType {

    public enum BuiltinType {
        NULL, INT, BOOL, STRING, VOID, CLASS, FUNC
    }

    public BuiltinType type;

    public BaseType(BuiltinType type) {
        this.type = type;
    }

    public abstract boolean match(BuiltinType other);

    public abstract  boolean match(BaseType other);

    public abstract boolean isArray();

    public abstract BaseType copy();

}