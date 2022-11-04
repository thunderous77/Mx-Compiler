package chaos.compiler.frontend.ast.node;

import chaos.utility.Position;

public abstract class BaseNode{
    public Position pos;

    public BaseNode(Position pos){
        this.pos = pos;
    }

    public abstract void accept(ASTVisitor visitor);
}