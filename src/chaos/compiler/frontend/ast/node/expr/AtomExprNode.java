package chaos.compiler.frontend.ast.node.expr;

import chaos.compiler.frontend.parser.MxParser;
import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.utility.Position;

public class AtomExprNode extends ExprBaseNode {

    public MxParser.AtomContext context;

    public AtomExprNode(Position pos, MxParser.AtomContext context) {
        super(pos);
        this.context=context;
    }



    // ir strConstant
    public String getString() {
        String str=context.StringConstant().toString();
        return str.substring(1, str.length() - 1).replace("\\\\", "\\")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\\"", "\"");
    }

    @Override
    public void accept(ASTVisitor vis) {
        vis.visit(this);
    }

    @Override
    public boolean isLeftValue() {
        return context.Identifier() != null;
    }

}