package chaos.compiler.middleend.llvmir.hierarchy;

import chaos.compiler.middleend.llvmir.constant.IRBaseConstant;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

public class IRGlobalValue extends IRBaseConstant {

    public boolean gpRegMark = false;

    public IRGlobalValue(String name, IRBaseType type) {
        super(name, type);
    }

    @Override
    public String identifier() {
        return "@" + this.name;
    }

}
