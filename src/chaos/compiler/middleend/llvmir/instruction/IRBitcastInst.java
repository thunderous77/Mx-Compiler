package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

public class IRBitcastInst extends IRBaseInst {

    public IRBitcastInst(IRValue fromValue, IRBaseType targetType, IRBlock parentBlock) {
        super("bitcast", targetType, parentBlock);
        this.addOperand(fromValue);
    }

    public IRValue fromValue() {
        return this.getOperand(0);
    }

    public String print() {
        // %bitcast = bitcast i8** %a to i8*;
        return this.identifier() + " = bitcast " + this.fromValue().typedIdentifier() + " to " + this.type;
    }

    @Override
    public IRBaseInst copy() {
        return new IRBitcastInst(fromValue(), type, null);
    }

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}
