package chaos.compiler.frontend.ast.node.expr;

import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.utility.Position;

public class AssignExprNode extends ExprBaseNode {
    public ExprBaseNode lNode, rNode;

    public AssignExprNode(Position pos, ExprBaseNode lNode, ExprBaseNode rNode) {
        super(pos);
        this.lNode = lNode;
        this.rNode = rNode;
    }

    @Override
    public void accept(ASTVisitor vis) {
        vis.visit(this);
    }

    @Override
    public boolean isLeftValue() {
        return true;
    }

}