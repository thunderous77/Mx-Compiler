package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.middleend.llvmir.type.IRBaseType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

public class AllocaInst extends BaseInst {

    public AllocaInst(IRBaseType allocaType) {
        super(new IRPointerType(allocaType));
    }

}

