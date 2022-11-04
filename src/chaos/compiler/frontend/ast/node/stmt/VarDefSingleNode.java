package chaos.compiler.frontend.ast.node.stmt;

import chaos.utility.Position;
import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.compiler.frontend.ast.node.BaseNode;
import chaos.compiler.frontend.ast.node.expr.ExprBaseNode;
import chaos.compiler.frontend.utility.registry.VarRegistry;

public class VarDefSingleNode extends BaseNode {

    public VarRegistry varRegistry;
    public ExprBaseNode initNode;

    public VarDefSingleNode(Position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor vis) {
        vis.visit(this);
    }

}