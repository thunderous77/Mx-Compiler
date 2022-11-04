package chaos.compiler.frontend.ast.node.stmt;

import java.util.ArrayList;

import chaos.compiler.frontend.utility.scope.IfScope;
import chaos.utility.Position;
import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.compiler.frontend.utility.scope.NormalScope;

public class SuiteStmtNode extends StmtBaseNode {

    public NormalScope scope;
    public ArrayList<StmtBaseNode> stmtNodes;

    public SuiteStmtNode(Position pos) {
        super(pos);
        this.scope = new NormalScope();
        this.stmtNodes = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}