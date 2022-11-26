package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

public class IRZextInst extends IRBaseInst {

    public IRZextInst(IRValue fromValue, IRBaseType targetType, IRBlock parentBlock) {
        super("zext", targetType, parentBlock);
        this.addOperand(fromValue);
    }

    public IRValue fromValue() {
        return this.getOperand(0);
    }

    public String format() {
        // %zext = zext i8 %a to i32;
        return this.identifier() + " = zext " + this.fromValue().typedIdentifier() + " to " + this.type;
    }

    @Override
    public IRBaseInst copy() {
        return new IRZextInst(fromValue(), type, null);
    }

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}
