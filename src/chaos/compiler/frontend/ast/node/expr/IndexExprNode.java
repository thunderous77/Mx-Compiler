package chaos.compiler.frontend.ast.node.expr;

import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.utility.Position;

public class IndexExprNode extends ExprBaseNode {

    public ExprBaseNode arrayNode, indexNode;

    public IndexExprNode(Position pos, ExprBaseNode arrayNode, ExprBaseNode indexNode) {
        super(pos);
        this.arrayNode = arrayNode;
        this.indexNode = indexNode;
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