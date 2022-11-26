package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

public class IRLoadInst extends IRBaseInst {

    public IRLoadInst(IRValue loadPtr, IRBlock parentBlock) {
        super(loadRename(loadPtr.name), ((IRPointerType) loadPtr.type).pointedType, parentBlock);
        this.addOperand(loadPtr);
    }

    public IRValue loadPtr() {
        return this.getOperand(0);
    }

    @Override
    public String format() {
        // %load = load <type>, <type*> %destPtr
        return this.identifier() + " = load " + this.type + ", " + this.loadPtr().typedIdentifier();
    }

    @Override
    public IRBaseInst copy() {
        return new IRLoadInst(loadPtr(), null);
    }

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}
