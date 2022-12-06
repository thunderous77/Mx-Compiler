package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

public class IRTruncInst extends IRBaseInst {

    public IRTruncInst(IRValue fromValue, IRBaseType targetType, IRBlock parentBlock) {
        super("trunc", targetType, parentBlock);
        this.addOperand(fromValue);
    }

    public IRValue fromValue() {
        return this.getOperand(0);
    }

    public String print() {
        // %trunc = trunc i8 %a to i32;
        return this.identifier() + " = trunc " + this.fromValue().typedIdentifier() + " to " + this.type;
    }

    @Override
    public IRBaseInst copy() {
        return new IRTruncInst(fromValue(), type, null);
    }

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}
