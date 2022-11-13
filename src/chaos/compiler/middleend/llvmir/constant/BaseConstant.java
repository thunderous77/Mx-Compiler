package chaos.compiler.middleend.llvmir.constant;

import chaos.compiler.middleend.llvmir.User;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

abstract public class BaseConstant extends User {

    public BaseConstant(String name, IRBaseType type) {
        super(name, type);
    }

}
