package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRVoidType;

public class IRBrInst extends IRBaseInst {

    public IRBrInst(IRBlock destBlock, IRBlock parentBlock) {
        super("br", new IRVoidType(), parentBlock);
        this.addOperand(destBlock);
    }

    public IRBrInst(IRValue condition, IRBlock ifTrueBlock, IRBlock ifFalseBlock, IRBlock parentBlock) {
        super("br", new IRVoidType(), parentBlock);
        this.addOperand(condition);
        this.addOperand(ifTrueBlock);
        this.addOperand(ifFalseBlock);
    }

    public IRBlock destBlock() {
        assert this.getOperandSize() == 1;
        return (IRBlock) this.getOperand(0);
    }

    public IRValue condition() {
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
    public boolean isValueSelf() {
        return false;
    }

    @Override
    public String print() {
        // br label %A
        // br i1 %comparison_result, label %A, label %B
        if (this.getOperandSize() == 1)
            return "br " + this.destBlock().typedIdentifier();
        else
            return "br " + this.condition().type + " " + this.condition().identifier() + ", " + this.ifTrueBlock().typedIdentifier() + ", " + this.ifFalseBlock().typedIdentifier();
    }

    @Override
    public IRBaseInst copy() {
        if (this.getOperandSize() == 1) return new IRBrInst(destBlock(), null);
        return new IRBrInst(condition(), ifTrueBlock(), ifFalseBlock(), null);
    }

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}
