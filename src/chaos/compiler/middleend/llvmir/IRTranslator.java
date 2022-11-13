package chaos.compiler.middleend.llvmir;

import chaos.compiler.frontend.ast.node.expr.BinaryExprNode;
import chaos.compiler.frontend.utility.scope.GlobalScope;
import chaos.compiler.frontend.utility.type.BaseType;
import chaos.compiler.frontend.utility.type.FuncType;
import chaos.compiler.frontend.utility.type.VarType;
import chaos.compiler.middleend.llvmir.type.*;

import java.util.Objects;

public class IRTranslator {

    private GlobalScope globalScope;

    public void setGlobalScope(GlobalScope scope) {
        this.globalScope = scope;
    }

    public static String transBinaryOp(BinaryExprNode.binaryOp opcode) {
        switch (opcode) {
            case ADD:
                return "add";
            case SUB:
                return "sub";
            case MUL:
                return "mul";
            case DIV:
                return "sdiv";
            case MOD:
                return "srem";
            case SHIFT_LEFT:
                return "shl";
            case SHIFT_RIGHT:
                return "ashr";
            case BIT_AND:
            case LOGIC_AND:
                return "and";
            case BIT_OR:
            case LOGIC_OR:
                return "or";
            case BIT_XOR:
                return "xor";
            case GREATER:
                return "sgt";
            case GREATER_EQUAL:
                return "sge";
            case LESS:
                return "slt";
            case LESS_EQUAL:
                return "sle";
            case EQUAL:
                return "eq";
            case NOT_EQUAL:
                return "ne";
        }
        return "";
    }

    // str: add -> cat
    public static String transStrBinaryOp(BinaryExprNode.binaryOp opcode) {
        if (Objects.equals(opcode, BinaryExprNode.binaryOp.ADD)) return "cat";
        return transBinaryOp(opcode);
    }

    public IRBaseType translateBuiltinType(BaseType mxType) {
        switch (mxType.builtinType) {
            case INT:
                return IRIntergerType.INT32;
            case BOOL:
                return new IRBoolType();
            case STRING:
                return IRPointerType.StringType;
            case CLASS:
                return new IRPointerType(globalScope.queryClass(((VarType) mxType).name).value.type);
            default:
                return new IRVoidType();
        }
    }

    public IRFunctionType transFuncType(FuncType funcType, IRBaseType methodfrom) {
        IRFunctionType ret = new IRFunctionType(transVarType(funcType.retType), methodfrom);
        if (methodfrom != null) ret.argTypes.add(methodfrom);
        for (BaseType argType : funcType.funcArgsType)
            ret.argTypes.add(transVarType(argType));
        return ret;
    }

    public IRBaseType transVarType(BaseType mxType) {
        IRBaseType ret;
        assert mxType instanceof VarType;
        ret = translateBuiltinType(mxType);
        // array -> pointer
        for (int i = 1; i <= ((VarType) mxType).dimensize; i++)
            ret = new IRPointerType(ret);
        return ret;
    }

    public IRBaseType transAllocaType(BaseType mxType) {
        IRBaseType ret;
        if (mxType.builtinType == BaseType.BuiltinType.BOOL)
            ret = new IRMemBoolType();
        else return transVarType(mxType);
        for (int i = 1; i <= ((VarType) mxType).dimensize; i++)
            ret = new IRPointerType(ret);
        return ret;
    }

}