package chaos.compiler.middleend.llvmir.constant;

import chaos.compiler.middleend.llvmir.hierarchy.IRGlobalValue;
import chaos.compiler.middleend.llvmir.type.IRArrayType;
import chaos.compiler.middleend.llvmir.type.IRIntergerType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

import java.util.Objects;

public class StrConstant extends IRGlobalValue {

    public String constData;

    public StrConstant(String data) {
        // super must be the first statement, builtinType will be modified later according to the realData
        super(".strconst", new IRPointerType(new IRArrayType(IRIntergerType.INT8, data.length() + 1)));
        this.constData = data;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof StrConstant && Objects.equals(constData, ((StrConstant) o).constData);
    }

    public String constDataFormat() {
        String ret = "\"" + constData.replace("\\", "\\\\")
                .replace("\n", "\\0A")
                .replace("\r", "\\0D")
                .replace("\t", "\\09")
                .replace("\"", "\\22") + "\\00\"";
        return ret;
    }

}
