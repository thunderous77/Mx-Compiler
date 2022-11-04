package chaos.compiler.frontend.ast.node.stmt;

import java.util.ArrayList;

import chaos.utility.Position;
import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.compiler.frontend.ast.node.expr.ExprBaseNode;
import chaos.compiler.frontend.utility.scope.LoopScope;

public class ForStmtNode extends StmtBaseNode {

    public LoopScope scope;
    public ExprBaseNode initNode, conditionNode, incrNode;
    public ArrayList<VarDefSingleNode> initVarDefSingleNode;
    public StmtBaseNode bodyStmtNode;

    public ForStmtNode(Position pos) {
        super(pos);
        this.scope = new LoopScope();
        initVarDefSingleNode = new ArrayList<VarDefSingleNode>();
        this.initNode = this.conditionNode = this.incrNode = null;
        this.bodyStmtNode = null;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}