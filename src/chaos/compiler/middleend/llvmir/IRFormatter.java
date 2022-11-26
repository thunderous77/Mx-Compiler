package chaos.compiler.middleend.llvmir;

import chaos.compiler.middleend.llvmir.constant.StrConstant;
import chaos.compiler.middleend.llvmir.hierarchy.IRGlobalVariable;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.instruction.IRBaseInst;
import chaos.compiler.middleend.llvmir.type.IRFunctionType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

public class IRFormatter {

    // @.str.1 = private unnamed_addr constant [4 x i8] c"<< \00", align 1
    public static String strConstantInitFormat(StrConstant strConstant) {
        return strConstant.identifier() + " = private unnamed_addr constant " + ((IRPointerType) strConstant.type).pointedType + strConstant.constDataFormat() + ", align " + ((IRPointerType) strConstant.type).pointedType.size();
    }

    // @n = global i32 0, align 4
    // llvm: @n = dso_local global i32 0, align 4
    public static String globalVarInitFormat(IRGlobalVariable globalVariable) {
        return globalVariable.identifier() + " = global " + globalVariable.pointedType() + ", align " + globalVariable.pointedType().size();
    }

    public static String classInitFormat(IRStruct struct) {
        String ret = " ";
        return ret;
    }

    public static String funcDeclareFormat(IRFunction function) {
        // declare void @print(i8*)
        StringBuilder ret = new StringBuilder("declare " + function.typedIdentifier() + "(");
        for (int i = 0; i < ((IRFunctionType) function.type).argTypes.size(); i++) {
            ret.append(function.getArgType(i));
            if (i != ((IRFunctionType) function.type).argTypes.size() - 1) ret.append(", ");
        }
        ret.append(")");
        return ret.toString();
    }

    // define i32 @foo(i32 %a, i64 %b)
    public static String funcDefFormat(IRFunction function) {
        StringBuilder ret = new StringBuilder("define " + function.typedIdentifier() + "(");
        for (int i = 0; i < function.getOperandSize(); ++i) {
            ret.append(function.getArg(i).typedIdentifier());
            if (i != function.getOperandSize() - 1) ret.append(", ");
        }
        ret.append(")");
        return ret.toString();
    }

    public static String instFormat(IRBaseInst inst) {
        return inst.format();
    }

}
