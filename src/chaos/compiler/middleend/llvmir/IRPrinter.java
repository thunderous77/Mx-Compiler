package chaos.compiler.middleend.llvmir;

import chaos.compiler.middleend.llvmir.constant.StrConstant;
import chaos.compiler.middleend.llvmir.hierarchy.*;
import chaos.compiler.middleend.llvmir.instruction.IRBaseInst;

import java.io.PrintStream;

public class IRPrinter {

    public static final String INDENT = "  ";

    private final PrintStream print;

    private final String fileName;

    public IRPrinter(String fileName, PrintStream print) {
        this.fileName = fileName;
        this.print = print;
    }

    public void printFunction(IRFunction function) {
        print.println(IRFormatter.funcDefFormat(function) + " {");
        for (int i = 0; i < function.blockList.size(); ++i) {
            printBlock(function.blockList.get(i));
            if (i != function.blockList.size() - 1) print.print("\n");
        }
        print.print("}\n\n");
    }

    public void printBlock(IRBlock block) {
        print.println(block.name + ":");
        for (IRBaseInst inst : block.phiInstList)
            print.println(INDENT + IRFormatter.instFormat(inst));
        for (IRBaseInst inst : block.instList) {
            print.println(INDENT + IRFormatter.instFormat(inst));
        }
    }

    public void printModule(IRModule module) {
        print.printf("; ModuleID = '%s'%n", fileName);
        print.printf("source_filename = \"%s\"%n", fileName);
        print.println("target triple = \"riscv32\"\n");

        for (IRFunction function : module.builtinFuncList) print.println(IRFormatter.funcDeclareFormat(function));
        print.print('\n');

        for (IRGlobalVariable globalVariable : module.globalVarList)
            print.println(IRFormatter.globalVarInitFormat(globalVariable));
        if (module.globalVarList.size() > 0) print.println('\n');

        for (StrConstant strConstant : module.strConstList)
            print.println(IRFormatter.strConstantInitFormat(strConstant));
        if (module.strConstList.size() > 0) print.print('\n');

        for (IRStruct struct : module.classList) print.println(IRFormatter.classInitFormat(struct));
        if (module.classList.size() > 0) print.print('\n');

        for (IRFunction func : module.funcList) printFunction(func);
    }

}
