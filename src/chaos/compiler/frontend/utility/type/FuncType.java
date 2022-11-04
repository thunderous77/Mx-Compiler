package chaos.compiler.frontend.utility.type;

import java.util.ArrayList;

import chaos.compiler.frontend.ast.node.expr.ExprBaseNode;

public class FuncType extends BaseType {

    public VarType retType;
    public ArrayList<VarType> funcArgsType;

    public FuncType() {
        super(BuiltinType.FUNC);
        retType = null;
        funcArgsType = new ArrayList<>();
    }

    @Override
    public boolean match(BaseType other) {
        return false;
    }

    @Override
    public boolean match(BuiltinType other) {
        return other == BuiltinType.FUNC;
    }

    @Override
    public boolean isArray() {
        return false;
    }

    @Override
    public BaseType copy() {
        FuncType ret = new FuncType();
        ret.retType = (VarType) retType.copy();
        for (int i = 0; i < funcArgsType.size(); ++i)
            ret.funcArgsType.add((VarType) funcArgsType.get(i).copy());
        return ret;
    }

    //  0 -> no error
    // -1 -> size not match
    // -2 -> type not match
    public int funcCallMatch(ArrayList<ExprBaseNode> argsNode) {
        if (funcArgsType.size() != argsNode.size())
            return -1;
        for (int i = 0; i < funcArgsType.size(); ++i) {
            if (!funcArgsType.get(i).match(argsNode.get(i).type))
                return -2;
        }
        return 0;
    }

    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("func, retType:").append(retType).append(" args:");
        for (int i = 0; i < funcArgsType.size(); ++i) {
            ret.append(funcArgsType.get(i).toString() + " ");
        }
        return ret.toString();
    }

}