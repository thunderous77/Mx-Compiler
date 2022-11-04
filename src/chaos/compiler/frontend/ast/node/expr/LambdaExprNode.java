package chaos.compiler.frontend.ast.node.expr;

import java.util.ArrayList;

import chaos.utility.Position;
import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.compiler.frontend.ast.node.stmt.SuiteStmtNode;
import chaos.compiler.frontend.utility.registry.FuncRegistry;

public class LambdaExprNode extends ExprBaseNode {

    public FuncRegistry funcRegistry;
    public SuiteStmtNode suiteNode;
    public ArrayList<ExprBaseNode> ArgsNode;

    public LambdaExprNode(Position pos, SuiteStmtNode suiteNode) {
        super(pos);
        this.suiteNode = suiteNode;
        ArgsNode = new ArrayList<>();
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