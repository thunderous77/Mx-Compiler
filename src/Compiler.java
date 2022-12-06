import chaos.compiler.frontend.ast.ASTBuilder;
import chaos.compiler.frontend.ast.node.RootNode;
import chaos.compiler.frontend.parser.MxLexer;
import chaos.compiler.frontend.parser.MxParser;
import chaos.compiler.frontend.semantic.SemanticChecker;
import chaos.compiler.middleend.llvmir.IRBuilder;
import chaos.compiler.middleend.llvmir.IRPrinter;
import chaos.compiler.middleend.llvmir.hierarchy.IRModule;
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
//            FileInputStream input = new FileInputStream("D:\\Sam\\program\\Mx-Compiler\\testcases\\codegen\\t8.mx");
//            PrintStream output = new PrintStream("D:\\Sam\\program\\Masterball\\judge\\testcases\\codegen\\my_llvm_out.ll");
            InputStream input = System.in;
            PrintStream output  = System.out;


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

            // llvm ir
            IRModule irModule = new IRBuilder(rootNode).module;
            IRPrinter printer = new IRPrinter("test",output);
            printer.printModule(irModule);
            output.close();
        } catch (Error e) {
            e.show_error();
            throw new RuntimeException();
        }
        System.exit(0);
    }
}