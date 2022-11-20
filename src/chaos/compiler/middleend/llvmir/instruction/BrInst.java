package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.type.IRFunctionType;
import chaos.compiler.middleend.llvmir.type.IRVoidType;

import java.util.ArrayList;

public class BrInst extends BaseInst {

    public BrInst(IRBlock destBlock, IRBlock parentBlock) {
        super("br", new IRVoidType(), parentBlock);
        this.addOperand(destBlock);
    }

    public BrInst(Value condition, IRBlock ifTrueBlock, IRBlock ifFalseBlock, IRBlock parentBlock) {
        super("br", new IRVoidType(), parentBlock);
        this.addOperand(condition);
        this.addOperand(ifTrueBlock);
        this.addOperand(ifFalseBlock);
    }

    public IRBlock destBlock() {
        assert this.getOperandSize() == 1;
        return (IRBlock) this.getOperand(0);
    }

    public Value condition() {
        assert this.getOperandSize() != 1;
        return this.getOperand(0);
    }

    public IRBlock ifTrueBlock() {
        return (IRBlock) this.getOperand(1);
    }

    public IRBlock ifFalseBlock() {
        return (IRBlock) this.getOperand(2);
    }

    public void resetDestBlock(IRBlock newBlock) {
        this.resetOperand(0, newBlock);
    }

    public void resetIfTrueBlock(IRBlock newBlock) {
        this.resetOperand(1, newBlock);
    }

    public void resetIfFalseBlock(IRBlock newBlock) {
        this.resetOperand(2, newBlock);
    }

    @Override
    public boolean isTerminator() {
        return true;
    }

    @Override
    public boolean isValueSelf() {
        return false;
    }

    @Override
    public String format() {
        // br label %A
        // br i1 %comparison_result, label %A, label %B
        if (this.getOperandSize() == 1)
            return "br " + this.destBlock().typedIdentifier();
        else
            return "br " + this.condition().type + " " + this.condition().identifier() + ", " + this.ifTrueBlock().typedIdentifier() + ", " + this.ifFalseBlock().typedIdentifier();
    }

    @Override
    public BaseInst copy() {
        if (this.getOperandSize() == 1) return new BrInst(destBlock(), null);
        return new BrInst(condition(), ifTrueBlock(), ifFalseBlock(), null);
    }

    @Override
    public void accept(InstVisitor visitor) {
        visitor.visit(this);
    }

}
