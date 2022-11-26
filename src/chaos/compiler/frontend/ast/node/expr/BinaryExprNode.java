package chaos.compiler.frontend.ast.node.expr;

import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.utility.Position;

public class BinaryExprNode extends ExprBaseNode {

    public ExprBaseNode lNode, rNode;

    public enum binaryOp {
        ADD, SUB, MUL, DIV, MOD, SHIFT_LEFT, SHIFT_RIGHT, LESS, LESS_EQUAL, GREATER, GREATER_EQUAL, EQUAL, NOT_EQUAL,
        BIT_AND, BIT_OR, BIT_XOR, LOGIC_AND, LOGIC_OR
    }

    public binaryOp opcode;

    public BinaryExprNode(Position pos, ExprBaseNode lNode, ExprBaseNode rNode) {
        super(pos);
        this.lNode = lNode;
        this.rNode = rNode;
    }

    public void setType(String opcode) {
        switch (opcode) {
            case "*":
                this.opcode = binaryOp.MUL;
                break;
            case "/":
                this.opcode = binaryOp.DIV;
                break;
            case "%":
                this.opcode = binaryOp.MOD;
                break;
            case "+":
                this.opcode = binaryOp.ADD;
                break;
            case "-":
                this.opcode = binaryOp.SUB;
                break;
            case "<<":
                this.opcode = binaryOp.SHIFT_LEFT;
                break;
            case ">>":
                this.opcode = binaryOp.SHIFT_RIGHT;
                break;
            case ">":
                this.opcode = binaryOp.GREATER;
                break;
            case ">=":
                this.opcode = binaryOp.GREATER_EQUAL;
                break;
            case "<":
                this.opcode = binaryOp.LESS;
                break;
            case "<=":
                this.opcode = binaryOp.LESS_EQUAL;
                break;
            case "==":
                this.opcode = binaryOp.EQUAL;
                break;
            case "!=":
                this.opcode = binaryOp.NOT_EQUAL;
                break;
            case "&":
                this.opcode = binaryOp.BIT_AND;
                break;
            case "^":
                this.opcode = binaryOp.BIT_XOR;
                break;
            case "|":
                this.opcode = binaryOp.BIT_OR;
                break;
            case "&&":
                this.opcode = binaryOp.LOGIC_AND;
                break;
            case "||":
                this.opcode = binaryOp.LOGIC_OR;
                break;
        }
    }

    public boolean isCompare() {
        return opcode == binaryOp.LESS || opcode == binaryOp.LESS_EQUAL || opcode == binaryOp.GREATER || opcode == binaryOp.GREATER_EQUAL || opcode == binaryOp.EQUAL || opcode == binaryOp.NOT_EQUAL;
    }

    public boolean isArith() {
        return opcode == binaryOp.ADD || opcode == binaryOp.SUB || opcode == binaryOp.MUL || opcode == binaryOp.DIV || opcode == binaryOp.MOD || opcode == binaryOp.SHIFT_LEFT || opcode == binaryOp.SHIFT_RIGHT || opcode == binaryOp.BIT_OR || opcode == binaryOp.BIT_XOR || opcode == binaryOp.BIT_AND;
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