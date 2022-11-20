package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.type.IRBaseType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

public class AllocaInst extends BaseInst {

    public String allocaName;
    public IRBaseType pointedType;

    public AllocaInst(String allocaName, IRBaseType pointedType, IRBlock parentBlock) {
        super(addrRename(allocaName), new IRPointerType(pointedType), parentBlock, true);
        this.allocaName = allocaName;
        this.pointedType = pointedType;
    }

    @Override
    public String format() {
        // %alloca = alloca <type>, align <size>
        return this.identifier() + " = alloca " + this.pointedType + ", align " + this.pointedType.size();
    }

    @Override
    public BaseInst copy() {
        return new AllocaInst(allocaName, pointedType, null);
    }

    @Override
    public void accept(InstVisitor visitor) {
        visitor.visit(this);
    }

}

