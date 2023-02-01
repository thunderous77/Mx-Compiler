import chaos.compiler.backend.asm.AsmBuilder;
import chaos.compiler.backend.asm.AsmPrinter;
import chaos.compiler.backend.asm.hierarchy.AsmModule;
import chaos.compiler.backend.regalloca.RegAllocator;
import chaos.compiler.backend.regalloca.StackAllocator;
import chaos.compiler.frontend.ast.ASTBuilder;
import chaos.compiler.frontend.ast.node.RootNode;
import chaos.compiler.frontend.parser.MxLexer;
import chaos.compiler.frontend.parser.MxParser;
import chaos.compiler.frontend.semantic.SemanticChecker;
import chaos.compiler.middleend.llvmir.IRBuilder;
import chaos.compiler.middleend.llvmir.IRPrinter;
import chaos.compiler.middleend.llvmir.hierarchy.IRModule;
import chaos.compiler.middleend.llvmir.optimization.MiddleEndOptimizer;
import chaos.utility.Error;
import chaos.utility.MxErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;



public class Compiler {
    public static void main(String[] args) throws Exception {
        try {
//            FileInputStream input = new FileInputStream("D:\\Sam\\program\\Mx-Compiler\\testcases\\codegen\\e1.mx");
//            PrintStream output = new PrintStream("D:\\Sam\\program\\Mx-Compiler\\debug\\test.s");
            InputStream input = System.in;
            PrintStream output  = System.out;

            // FrontEnd
            // get lexer
            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());

            // get parser
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());

            ParseTree treeRoot = parser.mxProgram();
            ASTBuilder astBuilder = new ASTBuilder();
            RootNode rootNode = (RootNode) astBuilder.visit(treeRoot);

            SemanticChecker semanticChecker = new SemanticChecker();
            semanticChecker.visit(rootNode);

            // MiddleEnd
            // llvm ir
            IRModule irModule = new IRBuilder(rootNode).module;

            // MiddleEnd Optimizer
            new MiddleEndOptimizer().runOnModule(irModule);

            //  IR Print
//            IRPrinter irPrinter = new IRPrinter("test", output);
//            irPrinter.printModule(irModule);

            // BackEnd
            AsmBuilder asmBuilder = new AsmBuilder(irModule);
            AsmModule asmModule = asmBuilder.asmModule;

            // Graph Coloring
            new RegAllocator().runOnModule(asmModule);

            // Stack Allocate
            new StackAllocator().runOnModule(asmModule);

            // ASM Print
            AsmPrinter asmPrinter = new AsmPrinter(output);
            asmPrinter.runOnModule(asmModule);

            output.close();
        } catch (Error e) {
            e.show_error();
            throw new RuntimeException();
        }
        System.exit(0);
    }
}