package chaos.compiler.middleend.llvmir.type;

import chaos.compiler.middleend.llvmir.IRStruct;

import java.util.ArrayList;
import java.util.Objects;

public class IRStructType extends IRBaseType {
    public final String structName;
    public final IRStruct struct;
    public ArrayList<IRBaseType> varTypeList = new ArrayList<>();

    public IRStructType(String structName) {
        this.structName = structName;
        struct = new IRStruct("class." + structName, this);
    }

    @Override
    public boolean match(IRBaseType other) {
        return other instanceof IRStructType && Objects.equals(structName, ((IRStructType) other).structName);
    }

    @Override
    public int size() {
        int ret = 0;
        for (IRBaseType memberVarType : varTypeList) ret += memberVarType.size();
        return ret;
    }

    // e.g. struct {i32 a, i8 b, i32 c}
    // index 0 1 2
    // offset 0 4 5
    public int memberOffset(int index) {
        int ret = 0;
        for (int i = 0; i < index; ++i) ret += varTypeList.get(i).size();
        return ret;
    }

    @Override
    public String toString() {
        return struct.identifier();
    }
}
