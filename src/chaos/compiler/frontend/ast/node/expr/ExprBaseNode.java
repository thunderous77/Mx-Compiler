package chaos.compiler.frontend.ast.node.expr;

import chaos.compiler.frontend.ast.node.BaseNode;
import chaos.utility.Position;
import chaos.compiler.frontend.utility.type.BaseType;

public abstract class ExprBaseNode extends BaseNode {

    public BaseType type;

    public ExprBaseNode(Position pos) {
        super(pos);
        this.type = null;
    }

    public abstract boolean isLeftValue();
}