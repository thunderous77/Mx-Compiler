package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.IRUser;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;

import chaos.compiler.middleend.llvmir.type.IRBaseType;
public abstract class IRBaseInst extends IRUser {

    public IRBlock parentBlock;
    public IRBaseInst(String name, IRBaseType type, IRBlock parentBlock) {
        super(name,type);
        setParentBlock(parentBlock);
    }

    public IRBaseInst(String name, IRBaseType type, IRBlock parentBlock, boolean insertAtFirst) {
        super(name,type);
        setParentBlockAtFirst(parentBlock);
    }

    public void setParentBlock(IRBlock parentBlock) {
        this.parentBlock = parentBlock;
        if (parentBlock != null) parentBlock.addInst(this);
    }

    public void setParentBlockAtFirst(IRBlock parentBlock) {
        if (parentBlock != null) parentBlock.addInstAtFirst(this);
    }

    public boolean mayHaveSideEffects() {return false;}

    public abstract String print();

    public boolean isValueSelf() {return true;}

    public abstract IRBaseInst copy();

    public abstract void accept(IRInstVisitor visitor);

}
