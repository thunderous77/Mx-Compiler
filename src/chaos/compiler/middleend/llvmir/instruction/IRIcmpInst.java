package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRBoolType;

public class IRIcmpInst extends IRBaseInst {

    public String op;

    public IRIcmpInst(String op, IRValue lValue, IRValue rValue, IRBlock parentBlock) {
        super("icmp", new IRBoolType(), parentBlock);
        this.op = op;
        this.addOperand(lValue);
        this.addOperand(rValue);
    }

    public IRValue lValue() {
        return this.getOperand(0);
    }

    public IRValue rValue() {
        return this.getOperand(1);
    }

    @Override
    public String format() {
        // %icmp = icmp slt i32 %i_value, 4
        return this.identifier() + " = icmp " + this.op + " " + this.lValue().typedIdentifier() + ", " + this.rValue().identifier();
    }

    @Override
    public IRBaseInst copy() {
        return new IRIcmpInst(op, lValue(), rValue(), null);
    }

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}
