package chaos.compiler.frontend.ast.node.expr;

import chaos.utility.Position;
import chaos.compiler.frontend.ast.node.ASTVisitor;
import chaos.compiler.frontend.utility.type.BaseType;

public class MemberExprNode extends ExprBaseNode {

    public ExprBaseNode superNode;
    public String memberName;

    public MemberExprNode(Position pos, ExprBaseNode superNode, String memberName) {
        super(pos);
        this.superNode = superNode;
        this.memberName = memberName;
    }

    @Override
    public void accept(ASTVisitor vis) {
        vis.visit(this);
    }

    @Override
    public boolean isLeftValue() {
        return !this.type.match(BaseType.BuiltinType.FUNC);
    }

}