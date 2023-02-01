package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.constant.IRNullPointerConstant;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

public class IRStoreInst extends IRBaseInst {

    public IRStoreInst(IRValue storePtr, IRValue storeValue, IRBlock parentBlock) {
        super("store", storeValue.type, parentBlock);
        this.addOperand(storeValue);
        this.addOperand(storePtr);
    }

    public IRValue storeValue() {
        return this.getOperand(0);
    }

    public IRValue storePtr() {
        return this.getOperand(1);
    }

    public void replacePtr(IRValue value) {
        this.resetOperand(1, value);
    }

    @Override
    public boolean isValueSelf() {
        return false;
    }

    @Override
    public String print() {
        // store i32 %1, i32* %i, align 4
        // nullptr has the same type with its pointedType
        return "store " + ((IRPointerType) this.storePtr().type).pointedType + " " + this.storeValue().identifier() + ", " + this.storePtr().typedIdentifier() + ", align " + this.type.size();
    }

    @Override
    public IRBaseInst copy() {
        return new IRStoreInst(storePtr(), storeValue(), null);
    }

    @Override
    public boolean mayHaveSideEffects() {return true;}

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}
