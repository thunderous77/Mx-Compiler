package chaos.compiler.frontend.ast.node;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.utility.Position;

public abstract class BaseNode{
    public Position pos;
    public IRValue value;

    public BaseNode(Position pos){
        this.pos = pos;
    }

    public abstract void accept(ASTVisitor visitor);
}