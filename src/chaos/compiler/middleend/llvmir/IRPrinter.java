package chaos.compiler.middleend.llvmir;

import chaos.compiler.middleend.llvmir.constant.StrConstant;
import chaos.compiler.middleend.llvmir.hierarchy.*;
import chaos.compiler.middleend.llvmir.instruction.IRBaseInst;
import chaos.compiler.middleend.llvmir.type.IRFunctionType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

import java.io.PrintStream;

public class IRPrinter {

    public static final String INDENT = "  ";

    private final PrintStream printStream;

    private final String fileName;

    public IRPrinter(String fileName, PrintStream printStream) {
        this.fileName = fileName;
        this.printStream = printStream;
    }

//    public boolean isEmptyGlobalInit(IRFunction function) {
//        return function.name == "global_var_init" && function.blockList.size() == 2 && function.blockList.get(0).instList.size() == 1 && function.blockList.get(1).instList.size() == 1;
//    }

    public void printFunction(IRFunction function) {
        // skip global variable initiate function if there is no new expression
//        if (isEmptyGlobalInit(function)) return;
        printStream.println(funcDefPrint(function) + " {");
        for (int i = 0; i < function.blockList.size(); ++i) {
            printBlock(function.blockList.get(i));
            if (i != function.blockList.size() - 1) printStream.print("\n");
        }
        printStream.print("}\n\n");
    }

    public void printBlock(IRBlock block) {
        printStream.println(block.name + ":");
        for (IRBaseInst inst : block.phiInstList)
            printStream.println(INDENT + instPrint(inst));
        for (IRBaseInst inst : block.instList) {
            printStream.println(INDENT + instPrint(inst));
        }
    }

    public void printModule(IRModule module) {
//        printStream.printf("; ModuleID = '%s'%n", fileName);
//        printStream.printf("source_filename = \"%s\"%n", fileName);
//        printStream.println("target triple = \"riscv32\"\n");

        for (IRFunction function : module.builtinFuncList) printStream.println(funcDeclarePrint(function));
        printStream.print('\n');

        for (IRGlobalVariable globalVariable : module.globalVarList)
            printStream.println(globalVarInitPrint(globalVariable));
        if (module.globalVarList.size() > 0) printStream.println('\n');

        for (StrConstant strConstant : module.strConstList)
            printStream.println(strConstantInitPrint(strConstant));
        if (module.strConstList.size() > 0) printStream.print('\n');

        for (IRStruct struct : module.classList) printStream.println(classInitPrint(struct));
        if (module.classList.size() > 0) printStream.print('\n');

        for (IRFunction func : module.funcList) printFunction(func);
    }

    // @.str.1 = private unnamed_addr constant [4 x i8] c"<< \00", align 1
    public static String strConstantInitPrint(StrConstant strConstant) {
        return strConstant.identifier() + " = private unnamed_addr constant " + ((IRPointerType) strConstant.type).pointedType + " c" + strConstant.constDataPrint() + ", align " + ((IRPointerType) strConstant.type).pointedType.size();
    }

    // @n = global i32 0, align 4
    // llvm: @n = dso_local global i32 0, align 4
    public static String globalVarInitPrint(IRGlobalVariable globalVariable) {
        return globalVariable.identifier() + " = global " + globalVariable.pointedType() + " zeroinitializer" + ", align " + globalVariable.pointedType().size();
    }

    public static String classInitPrint(IRStruct struct) {
        StringBuilder ret = new StringBuilder(struct.identifier() + " = type {");
        for (int i = 0; i < struct.type().varTypeList.size(); i++) {
            ret.append(struct.type().varTypeList.get(i));
            if (i != struct.type().varTypeList.size() - 1) ret.append(", ");
        }
        ret.append("}");
        return ret.toString();
    }

    public static String funcDeclarePrint(IRFunction function) {
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
    public static String funcDefPrint(IRFunction function) {
        StringBuilder ret = new StringBuilder("define " + function.typedIdentifier() + "(");
        for (int i = 0; i < function.getOperandSize(); ++i) {
            ret.append(function.getArg(i).typedIdentifier());
            if (i != function.getOperandSize() - 1) ret.append(", ");
        }
        ret.append(")");
        return ret.toString();
    }

    public static String instPrint(IRBaseInst inst) {
        return inst.print();
    }

}
