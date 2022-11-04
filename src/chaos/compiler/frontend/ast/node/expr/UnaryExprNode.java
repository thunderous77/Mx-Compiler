package chaos.compiler.frontend.ast.node.expr;

import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.utility.Position;

public class UnaryExprNode extends ExprBaseNode {
    public ExprBaseNode selfNode;

    public enum unaryOp {
        LOGIC_NOT, BIT_NOT, ADD, SUB;
    }

    public unaryOp opcode;

    public UnaryExprNode(Position pos, String opcode, ExprBaseNode exprNode) {
        super(pos);
        this.selfNode = exprNode;
        switch (opcode) {
            case "!":
                this.opcode = unaryOp.LOGIC_NOT;
                break;
            case "~":
                this.opcode = unaryOp.BIT_NOT;
                break;
            case "+":
                this.opcode = unaryOp.ADD;
                break;
            case "-":
                this.opcode = unaryOp.SUB;
                break;
        }
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