package chaos.compiler.frontend.ast.node.expr;

import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.utility.Position;

public class PrefixExprNode extends ExprBaseNode {

    public ExprBaseNode selfNode;

    public enum prefixOp {
        INCRE, DECRE;
    }

    public prefixOp opcode;

    public PrefixExprNode(Position pos, String opcode, ExprBaseNode selfNode) {
        super(pos);
        switch (opcode) {
            case "++":
                this.opcode = prefixOp.INCRE;
                break;
            case "--":
                this.opcode = prefixOp.DECRE;
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
        return true;
    }

}