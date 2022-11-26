package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRVoidType;

public class IRRetInst extends IRBaseInst {

    public IRRetInst(IRBlock parentBlock) {
        super("ret", new IRVoidType(), parentBlock);
    }

    public IRRetInst(IRValue retValue, IRBlock parentBlock) {
        super("ret", retValue.type, parentBlock);
        this.addOperand(retValue);
    }

    public IRValue retValue() {
        return this.getOperand(0);
    }

    public boolean isVoid() {
        return this.getOperandSize() == 0;
    }

    @Override
    public boolean isValueSelf() {
        return false;
    }

    @Override
    public boolean isTerminator() {
        return true;
    }

    @Override
    public String format() {
        String ret = "ret " + this.type;
        if (!isVoid()) ret += " " + this.retValue().identifier();
        return ret;
    }

    @Override
    public IRBaseInst copy() {
        if (isVoid()) return new IRRetInst(null);
        else return new IRRetInst(retValue(), null);
    }

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}
