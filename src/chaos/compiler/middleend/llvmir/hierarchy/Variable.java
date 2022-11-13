package chaos.compiler.middleend.llvmir.hierarchy;

import chaos.compiler.middleend.llvmir.IRVisitor;
import chaos.compiler.middleend.llvmir.Value;
import chaos.compiler.middleend.llvmir.constant.BaseConstant;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

public class Variable extends Value {

    public BaseConstant initValue = null;

    public Variable(IRBaseType type, String name, BaseConstant initValue) {
        super(type);
        this.name = name;
        this.initValue = initValue;
    }



}
