package chaos.compiler.frontend.ast.node.expr;

import java.util.ArrayList;

import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.compiler.frontend.utility.type.VarType;
import chaos.utility.Position;

public class NewExprNode extends ExprBaseNode {

    public ArrayList<ExprBaseNode> eachDimSizeNode;

    public NewExprNode(Position pos, VarType type) {
        super(pos);
        this.type = type;
        eachDimSizeNode = new ArrayList<>();
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