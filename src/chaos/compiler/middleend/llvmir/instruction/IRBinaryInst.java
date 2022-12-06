package chaos.compiler.middleend.llvmir.instruction;


import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

public class IRBinaryInst extends IRBaseInst {

    public enum BinaryOpcode {
        ADD_NSW, SUB_NSW, MUL_NSW, SDIV, SREM, SHL, ASHR, AND, OR, XOR
    }

    public BinaryOpcode opcode;
    public String op;

    public IRBinaryInst(String op, IRBaseType retType, IRValue lValue, IRValue rValue, IRBlock parentBlock) {
        super(op, retType, parentBlock);
        this.op = op;
        this.addOperand(lValue);
        this.addOperand(rValue);
    }

    public IRValue leftValue() {
        return this.getOperand(0);
    }

    public IRValue rightValue() {
        return this.getOperand(1);
    }

    @Override
    public String print() {
        // %add = add i32 %A, %B
        return this.identifier() + " = " + this.op + " " + this.type + " " + this.leftValue().identifier() + ", " + this.rightValue().identifier();
    }

    @Override
    public IRBaseInst copy() {
        return new IRBinaryInst(op, type, leftValue(), rightValue(), null);
    }

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}
