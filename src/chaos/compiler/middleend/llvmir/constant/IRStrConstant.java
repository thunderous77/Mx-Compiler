package chaos.compiler.middleend.llvmir.constant;

import chaos.compiler.middleend.llvmir.hierarchy.IRGlobalValue;
import chaos.compiler.middleend.llvmir.type.IRArrayType;
import chaos.compiler.middleend.llvmir.type.IRIntergerType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;

import java.util.Objects;

public class IRStrConstant extends IRGlobalValue {

    public String constData;

    public IRStrConstant(String data) {
        // super must be the first statement, builtinType will be modified later according to the realData
        super(".strconst", new IRPointerType(new IRArrayType(IRIntergerType.INT8, data.length() + 1)));
        this.constData = data;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof IRStrConstant && Objects.equals(constData, ((IRStrConstant) o).constData);
    }

    public String constDataPrint() {
        String ret = "\"" + constData.replace("\\", "\\\\")
                .replace("\n", "\\0A")
                .replace("\r", "\\0D")
                .replace("\t", "\\09")
                .replace("\"", "\\22") + "\\00\"";
        return ret;
    }

}
