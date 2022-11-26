package chaos.compiler.middleend.llvmir.constant;

import chaos.compiler.middleend.llvmir.IRUser;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

abstract public class IRBaseConstant extends IRUser {

    public IRBaseConstant(String name, IRBaseType type) {
        super(name, type);
    }

}
