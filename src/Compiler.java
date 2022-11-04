import chaos.compiler.frontend.ast.ASTBuilder;
import chaos.compiler.frontend.ast.node.RootNode;
import chaos.compiler.frontend.parser.MxLexer;
import chaos.compiler.frontend.parser.MxParser;
import chaos.compiler.frontend.semantic.SemanticChecker;
import chaos.utility.Error;
import chaos.utility.MxErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

//import java.io.File;
//import java.io.FileInputStream;
import java.io.InputStream;


public class Compiler {
    public static void main(String[] args) throws Exception {
        try {
            //File testCode = new File("Compiler-2021-testcases/sema/basic-package/basic-1.mx");

//            InputStream testCodeStream = new FileInputStream(system);

            InputStream testCodeStream = System.in;

            // get lexer
            MxLexer lexer = new MxLexer(CharStreams.fromStream(testCodeStream));
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
        } catch (Error e) {
            e.show_error();
            throw new RuntimeException();
        }
        System.exit(0);
    }
}