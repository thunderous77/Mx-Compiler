package chaos.compiler.backend.asm;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.hierarchy.AsmModule;

import java.io.PrintStream;

public class AsmPrinter {

    public final static String TAB = "\t";

    private final PrintStream ps;
    public AsmPrinter(PrintStream ps) {
        this.ps = ps;
    }

    public void runOnModule(AsmModule module) {
        ps.println(TAB + ".text");

        module.functionList.forEach(this::runOnFunc);

        module.globalVarList.forEach(globalVar -> {
            AsmFormatter.globalVariableFormat(globalVar).forEach(ps::println);
            ps.println();
        });
        module.strConstList.forEach(stringConst -> {
            AsmFormatter.stringConstFormat(stringConst).forEach(ps::println);
            ps.println();
        });
    }

    public void runOnFunc(AsmFunction function) {
        AsmFormatter.functionHeaderFormat(function).forEach(ps::println);
        ps.println(function.identifier + ":");
        function.blockList.forEach(this::runOnBlock);
        ps.println(TAB + ".size" + TAB + function + ", .-" + function);
        ps.println("                                        # -- End function");
    }

    public void runOnBlock(AsmBlock block) {
        ps.println(block.identifier + ":");
        block.instList.forEach(inst -> ps.println(AsmFormatter.instFormat(inst)));
    }

}
