package chaos.compiler.frontend.ast.node.expr;

import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.utility.Position;

public class PostfixExprNode extends ExprBaseNode {

    public ExprBaseNode selfNode;

    public enum postfixOp {
        INCRE, DECRE;
    }

    public postfixOp opcode;

    public PostfixExprNode(Position pos, String opcode, ExprBaseNode selfNode) {
        super(pos);
        switch (opcode) {
            case "++":
                this.opcode = postfixOp.INCRE;
                break;
            case "--":
                this.opcode = postfixOp.DECRE;
                break;
        }
        this.selfNode = selfNode;
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