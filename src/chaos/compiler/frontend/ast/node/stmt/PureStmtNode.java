package chaos.compiler.frontend.ast.node.stmt;

import chaos.utility.Position;
import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.compiler.frontend.ast.node.expr.ExprBaseNode;

public class PureStmtNode extends StmtBaseNode {

    public ExprBaseNode exprNode;

    public PureStmtNode(Position pos) {
        super(pos);
        this.exprNode = null;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}