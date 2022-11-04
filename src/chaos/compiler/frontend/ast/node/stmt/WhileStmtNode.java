package chaos.compiler.frontend.ast.node.stmt;

import chaos.utility.Position;
import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.compiler.frontend.ast.node.expr.ExprBaseNode;
import chaos.compiler.frontend.utility.scope.LoopScope;

public class WhileStmtNode extends StmtBaseNode {

    public LoopScope scope;
    public ExprBaseNode conditionNode;
    public StmtBaseNode bodyStmtNode;

    public WhileStmtNode(Position pos, ExprBaseNode conditionNode, StmtBaseNode bodyStmtNode) {
        super(pos);
        this.scope = new LoopScope();
        this.conditionNode = conditionNode;
        this.bodyStmtNode = bodyStmtNode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}