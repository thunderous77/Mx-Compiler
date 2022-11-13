package chaos.compiler.middleend.llvmir.constant;

import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.type.IRBaseType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

public class GlobalVariable extends GlobalValue {

    // global variable is a pointer
    // for constant to replace
    public Value initValue;
    public int dimensize = 0;

    public GlobalVariable(String name, IRBaseType type) {
        super(name, type);
    }

    public IRBaseType pointedType() {
        return ((IRPointerType) this.type).pointedType;
    }

}
