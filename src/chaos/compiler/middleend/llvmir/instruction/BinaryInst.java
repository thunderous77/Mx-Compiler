package chaos.compiler.middleend.llvmir.instruction;


import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

public class BinaryInst extends BaseInst {

    public enum BinaryOpcode {
        ADD_NSW, SUB_NSW, MUL_NSW, SDIV, SREM, SHL, ASHR, AND, OR, XOR
    }

    public BinaryOpcode opcode;
    public String op;

    public BinaryInst(String op, IRBaseType retType, Value lValue, Value rValue, IRBlock parentBlock) {
        super(op, retType, parentBlock);
        this.op = op;
        this.addOperand(lValue);
        this.addOperand(rValue);
    }

    public Value leftValue() {
        return this.getOperand(0);
    }

    public Value rightValue() {
        return this.getOperand(1);
    }

    @Override
    public String format() {
        // %add = add i32 %A, %B
        return this.identifier() + " = " + this.op + " " + this.type + " " + this.leftValue().identifier() + ", " + this.rightValue().identifier();
    }

    @Override
    public BaseInst copy() {
        return new BinaryInst(op, type, leftValue(), rightValue(), null);
    }

    @Override
    public void accept(InstVisitor visitor) {
        visitor.visit(this);
    }

}
