package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRVisitor;
import chaos.compiler.middleend.llvmir.User;
import chaos.compiler.middleend.llvmir.hierarchy.Block;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

abstract public class BaseInst extends User {

    public BaseInst(IRBaseType type) {
        super(type);
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

}
