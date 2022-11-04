package chaos.compiler.frontend.ast.node.stmt;

import chaos.utility.Position;
import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.compiler.frontend.ast.node.expr.ExprBaseNode;
import chaos.compiler.frontend.utility.scope.IfScope;

public class IfStmtNode extends StmtBaseNode {

    public IfScope ifScope, elseScope;
    public ExprBaseNode conditionNode;
    public StmtBaseNode ifStmtNode, elseStmtNode;

    public IfStmtNode(Position pos, ExprBaseNode conditionNode, StmtBaseNode ifStmtNode) {
        super(pos);
        this.ifScope = new IfScope();
        this.elseScope = new IfScope();
        this.conditionNode = conditionNode;
        this.ifStmtNode = ifStmtNode;
        this.elseStmtNode = null;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}