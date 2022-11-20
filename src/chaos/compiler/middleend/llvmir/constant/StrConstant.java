package chaos.compiler.middleend.llvmir.constant;

import chaos.compiler.middleend.llvmir.hierarchy.GlobalValue;
import chaos.compiler.middleend.llvmir.type.IRArrayType;
import chaos.compiler.middleend.llvmir.type.IRIntergerType;

import java.util.Objects;

public class StrConstant extends GlobalValue {

    public String constData;

    public StrConstant(String data) {
        // super must be the first statement, builtinType will be modified later according to the realData
        super("strconst", new IRArrayType(IRIntergerType.INT8, data.length() + 1));
//        String realData = data.replace("\\\\", "\\")
//                .replace("\\n", "\n")
//                .replace("\\r", "\r")
//                .replace("\\t", "\t")
//                .replace("\\\"", "\"");
        this.constData = data;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof StrConstant && Objects.equals(constData, ((StrConstant) o).constData);
    }

    public String constDataFormat() {
        return constData.replace("\\", "\\\\")
                .replace("\n", "\\0A")
                .replace("\r", "\\0D")
                .replace("\t", "\\09")
                .replace("\"", "\\22") + "\\00";
    }

}
