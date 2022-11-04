package chaos.compiler.frontend.ast.node.stmt;

import chaos.utility.Position;
import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.compiler.frontend.ast.node.expr.ExprBaseNode;

public class ReturnStmtNode extends StmtBaseNode {

    public ExprBaseNode retNode;

    public ReturnStmtNode(Position pos) {
        super(pos);
        this.retNode = null;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}