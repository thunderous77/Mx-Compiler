package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.User;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;

import chaos.compiler.middleend.llvmir.type.IRBaseType;
public abstract class BaseInst extends User {

    public IRBlock parentBlock;
    public String instName;

    public BaseInst(String name, IRBaseType type,IRBlock parentBlock) {
        super(name,type);
        setParentBlock(parentBlock);
    }

    public BaseInst(String name, IRBaseType type,IRBlock parentBlock,boolean insertAtHead) {
        super(name,type);
        setParentBlockAtHead(parentBlock);
    }

    public void setParentBlock(IRBlock parentBlock) {
        this.parentBlock = parentBlock;
        if (parentBlock != null) parentBlock.addInst(this);
    }

    public void setParentBlockAtHead(IRBlock parentBlock) {
        this.parentBlock = parentBlock;
        if (parentBlock != null) parentBlock.addInstAtHead(this);
    }

    public abstract String format();

    public boolean isTerminator() {return false;}

    public boolean isValueSelf() {return true;}

    public abstract BaseInst copy();

    public abstract void accept(InstVisitor visitor);

}
