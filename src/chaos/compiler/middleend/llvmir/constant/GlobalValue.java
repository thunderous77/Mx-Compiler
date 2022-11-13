package chaos.compiler.middleend.llvmir.constant;

import chaos.compiler.middleend.llvmir.type.IRBaseType;

public class GlobalValue extends BaseConstant {

    public GlobalValue(String name, IRBaseType type) {
        super(name, type);
    }

    @Override
    public String identifier() {
        return "@" + this.name;
    }

}
