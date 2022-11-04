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

    @Override
    public void accept(ASTVisitor vis) {
        vis.visit(this);
    }

    @Override
    public boolean isLeftValue() {
        return context.Identifier() != null;
    }

}