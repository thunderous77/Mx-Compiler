package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRVisitor;
import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

public class BitcastInst extends BaseInst {

    public BitcastInst(Value fromValue, IRBaseType targetType, IRBlock parentBlock) {
        super("bitcast", targetType, parentBlock);
        this.addOperand(fromValue);
    }

    public Value fromValue() {
        return this.getOperand(0);
    }

    public String format() {
        // %bitcast = bitcast i8** %a to i8*;
        return this.identifier() + " = bitcast " + this.fromValue().typedIdentifier() + " to " + this.type;
    }

    @Override
    public BaseInst copy() {
        return new BitcastInst(fromValue(), type, null);
    }

    @Override
    public void accept(InstVisitor visitor) {
        visitor.visit(this);
    }

}
