package chaos.compiler.frontend.utility.type;

import java.util.Objects;

import chaos.utility.Error;
import chaos.compiler.frontend.parser.MxParser;
import chaos.utility.Position;

public class VarType extends BaseType {

    public String name;

    public int dimensize;

    public VarType(BuiltinType type) {
        super(type);
        dimensize = 0;
        name = "not a class";
    }

    public VarType(String className) {
        super(BuiltinType.CLASS);
        this.name = className;
        dimensize = 0;
    }

    public VarType(MxParser.VarDefTypeContext context, boolean isFuncRetType) {
        super(BuiltinType.NULL);
        dimensize = context.LeftBracket().size();
        name = "not a class";

        if (context.VoidType() != null) builtinType = BuiltinType.VOID;
        else if (context.builtinType() != null) {
            if (context.builtinType().IntType() != null) builtinType = BuiltinType.INT;
            else if (context.builtinType().BoolType() != null) builtinType = BuiltinType.BOOL;
            else if (context.builtinType().StringType() != null) builtinType = BuiltinType.STRING;
            else if (context.builtinType().Identifier() != null) {
                builtinType = BuiltinType.CLASS;
                name = context.builtinType().Identifier().toString();
            }
        }
    }

    public VarType(MxParser.VarDefTypeContext context) {
        super(BuiltinType.NULL);
        dimensize = context.LeftBracket().size();
        name = "not a class";

        if (context.VoidType() != null)
            throw new Error(new Position(context), "\"void\" builtinType cannot be used in variable declaration");
        else if (context.builtinType() != null) {
            if (context.builtinType().IntType() != null) builtinType = BuiltinType.INT;
            else if (context.builtinType().BoolType() != null) builtinType = BuiltinType.BOOL;
            else if (context.builtinType().StringType() != null) builtinType = BuiltinType.STRING;
            else if (context.builtinType().Identifier() != null) {
                builtinType = BuiltinType.CLASS;
                name = context.builtinType().Identifier().toString();
            }
        }
    }

    public VarType(MxParser.NewExpContext context) {
        super(BuiltinType.NULL);
        dimensize = context.arraySizeDef().size();
        name = "not a class";
        boolean isAllNull = (dimensize > 0);
        for (int i = 0; i < dimensize; i++)
            if (context.arraySizeDef(i).expr() != null)
                isAllNull = false;
        if (isAllNull) throw new Error(new Position(context), "at least declare one outer size for the array");
        for (int i = 0; i < dimensize - 1; i++) {
            if (context.arraySizeDef(i).expr() == null && context.arraySizeDef(i + 1).expr() != null)
                throw new Error(new Position(context.arraySizeDef(i)), "the outer size should be declared at first");
        }
        if (context.builtinType() != null) {
            if (context.builtinType().IntType() != null) builtinType = BuiltinType.INT;
            else if (context.builtinType().BoolType() != null) builtinType = BuiltinType.BOOL;
            else if (context.builtinType().StringType() != null) builtinType = BuiltinType.STRING;
            else if (context.builtinType().Identifier() != null) {
                builtinType = BuiltinType.CLASS;
                name = context.builtinType().Identifier().toString();
            }
        } else if (context.VoidType() != null)
            throw new Error(new Position(context), "\"void\" builtinType cannot be used in variable declaration");
    }

    @Override
    public boolean match(BaseType other) {
        if (other instanceof VarType) {
            if ((dimensize > 0 || builtinType == BuiltinType.CLASS) && other.builtinType == BuiltinType.NULL)
                return true;
            return builtinType == other.builtinType && Objects.equals(name, ((VarType) other).name) &&
                    dimensize == ((VarType) other).dimensize;
        }
        return false;
    }

    @Override
    public boolean match(BuiltinType other) {
        if ((dimensize > 0 || builtinType == BuiltinType.CLASS) && other == BuiltinType.NULL)
            return true;
        return builtinType == other && dimensize == 0;
    }

    @Override
    public boolean isArray() {
        return this.dimensize > 0;
    }

    @Override
    public BaseType copy() {
        VarType ret = new VarType(builtinType);
        ret.dimensize = dimensize;
        ret.name = name;
        return ret;
    }

    public String toString() {
        StringBuilder ret = null;

        if (builtinType == BuiltinType.CLASS) ret = new StringBuilder(name);
        else ret = new StringBuilder(builtinType.toString());

        for (int i = 0; i < dimensize; ++i) ret.append("[]");
        return ret.toString();
    }

}