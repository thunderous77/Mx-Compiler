package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

public class LoadInst extends BaseInst {

    public LoadInst(Value loadPtr, IRBlock parentBlock) {
        super(loadRename(loadPtr.name), ((IRPointerType) loadPtr.type).pointedType, parentBlock);
        this.addOperand(loadPtr);
    }

    public Value loadPtr() {
        return this.getOperand(0);
    }

    @Override
    public String format() {
        // %load = load <type>, <type*> %destPtr
        return this.identifier() + " = load " + this.type + ", " + this.loadPtr().typedIdentifier();
    }

    @Override
    public BaseInst copy() {
        return new LoadInst(loadPtr(), null);
    }

    @Override
    public void accept(InstVisitor visitor) {
        visitor.visit(this);
    }

}
