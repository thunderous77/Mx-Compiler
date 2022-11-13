package chaos.compiler.middleend.llvmir.type;

import java.util.ArrayList;

public class IRFunctionType extends IRBaseType {

    // record str / class private function
    IRBaseType methodfrom;
    public IRBaseType retType;
    public ArrayList<IRBaseType> argTypes;

    // init retType
    public IRFunctionType(IRBaseType retType, IRBaseType methodfrom) {
        this.retType = retType;
        this.methodfrom = methodfrom;
        argTypes = new ArrayList<>();
    }

    @Override
    public boolean match(IRBaseType other) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public String toString() {
        return retType.toString();
    }

}
