package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRBoolType;

public class IcmpInst extends BaseInst {

    public String op;

    public IcmpInst(String op, Value lValue, Value rValue, IRBlock parentBlock) {
        super("icmp", new IRBoolType(), parentBlock);
        this.op = op;
        this.addOperand(lValue);
        this.addOperand(rValue);
    }

    public Value lValue() {
        return this.getOperand(0);
    }

    public Value rValue() {
        return this.getOperand(1);
    }

    @Override
    public String format() {
        // %icmp = icmp slt i32 %i_value, 4
        return this.identifier() + " = icmp " + this.op + " " + this.lValue().typedIdentifier() + ", " + this.rValue().identifier();
    }

    @Override
    public BaseInst copy() {
        return new IcmpInst(op, lValue(), rValue(), null);
    }

    @Override
    public void accept(InstVisitor visitor) {
        visitor.visit(this);
    }

}
