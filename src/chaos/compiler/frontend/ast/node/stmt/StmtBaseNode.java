package chaos.compiler.frontend.ast.node.stmt;

import chaos.utility.Position;
import chaos.compiler.frontend.ast.node.BaseNode;

public abstract class StmtBaseNode extends BaseNode {

    public StmtBaseNode (Position pos) {
        super(pos);
    }

}