package chaos.compiler.frontend.ast.node;

import java.util.ArrayList;

import chaos.utility.Position;
import chaos.compiler.frontend.utility.scope.GlobalScope;

public class RootNode extends BaseNode {

    public ArrayList<BaseNode> sonNodes;
    public GlobalScope scope;

    public RootNode(Position pos) {
        super(pos);
        sonNodes = new ArrayList<>();
        this.scope = new GlobalScope();
    }

    @Override
    public void accept(ASTVisitor vis) {
        vis.visit(this);
    }
}