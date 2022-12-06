package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRBaseType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

public class IRAllocaInst extends IRBaseInst {

    public String allocaName;
    public IRBaseType pointedType;

    public IRAllocaInst(String allocaName, IRBaseType pointedType, IRBlock parentBlock) {
        super(addrRename(allocaName), new IRPointerType(pointedType), parentBlock, true);
        this.allocaName = allocaName;
        this.pointedType = pointedType;
    }

    @Override
    public String print() {
        // %alloca = alloca <type>, align <size>
        return this.identifier() + " = alloca " + this.pointedType + ", align " + this.pointedType.size();
    }

    @Override
    public IRBaseInst copy() {
        return new IRAllocaInst(allocaName, pointedType, null);
    }

    @Override
    public void accept(IRInstVisitor visitor) {
        visitor.visit(this);
    }

}

