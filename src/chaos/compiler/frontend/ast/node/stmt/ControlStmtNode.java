package chaos.compiler.frontend.ast.node.stmt;

import chaos.utility.Position;
import chaos.compiler.frontend.ast.node.ASTVisitor;

public class ControlStmtNode extends StmtBaseNode {

    public enum controlOp {
        BREAK, CONTINUE
    }

    public controlOp opcode;

    public ControlStmtNode(Position pos, String opcode) {
        super(pos);
        switch (opcode) {
            case "break":
                this.opcode = controlOp.BREAK;
                break;
            case "continue":
                this.opcode = controlOp.CONTINUE;
                break;
        }
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}