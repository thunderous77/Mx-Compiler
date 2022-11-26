package chaos.compiler.middleend.llvmir.hierarchy;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.type.IRBaseType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

public class IRGlobalVariable extends IRGlobalValue {

    // global variable is a pointer
    // for constant to replace
    public IRValue initValue;
    public int dimensize = 0;

    public IRGlobalVariable(String name, IRBaseType type) {
        super(name, new IRPointerType(type));
    }

    public IRBaseType pointedType() {
        return ((IRPointerType) this.type).pointedType;
    }

}
