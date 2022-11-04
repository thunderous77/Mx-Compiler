package chaos.compiler.frontend.ast.node.expr;

import java.util.ArrayList;

import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.utility.Position;

public class FuncCallExprNode extends ExprBaseNode {

    public ExprBaseNode funcCallNode;
    public ArrayList<ExprBaseNode> argNodes;

    public FuncCallExprNode(Position pos, ExprBaseNode funcCallNode) {
        super(pos);
        this.funcCallNode = funcCallNode;
        this.argNodes = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor vis) {
        vis.visit(this);
    }

    @Override
    public boolean isLeftValue() {
        return false;
    }

}