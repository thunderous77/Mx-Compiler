package chaos.compiler.middleend.llvmir.hierarchy;

import chaos.compiler.middleend.llvmir.Struct;
import chaos.compiler.middleend.llvmir.constant.StrConstant;
import chaos.compiler.middleend.llvmir.type.*;
import chaos.utility.Error;

import java.util.ArrayList;
import java.util.Objects;


public class IRModule {

    public String filename;
    public ArrayList<IRFunction> funcList = new ArrayList<>();
    public ArrayList<IRFunction> builtinFuncList = new ArrayList<>();
    public ArrayList<Struct> classList = new ArrayList<>();
    public ArrayList<GlobalVariable> globalVarList = new ArrayList<>();
    public ArrayList<StrConstant> strConstList = new ArrayList<>();

    public void setBottomFunction() {
        // malloc : i32 -> i8_ptr
        builtinFuncList.add(new IRFunction("_bot_malloc", IRPointerType.NULL, IRIntergerType.INT32));
        // cat : str = str1 + str2 ->str
        builtinFuncList.add(new IRFunction("bot_str_cat", IRPointerType.StringType, IRPointerType.StringType, IRPointerType.StringType));
        // str1 =, !=, >, >=, <, <= str2 ->bool
        builtinFuncList.add(new IRFunction("bot_str_eq", new IRBoolType(), IRPointerType.StringType, IRPointerType.StringType));
        builtinFuncList.add(new IRFunction("bot_str_ne", new IRBoolType(), IRPointerType.StringType, IRPointerType.StringType));
        builtinFuncList.add(new IRFunction("bot_str_slt", new IRBoolType(), IRPointerType.StringType, IRPointerType.StringType));
        builtinFuncList.add(new IRFunction("bot_str_sle", new IRBoolType(), IRPointerType.StringType, IRPointerType.StringType));
        builtinFuncList.add(new IRFunction("bot_str_sgt", new IRBoolType(), IRPointerType.StringType, IRPointerType.StringType));
        builtinFuncList.add(new IRFunction("bot_str_sge", new IRBoolType(), IRPointerType.StringType, IRPointerType.StringType));
    }

    public IRFunction getMalloc() {
        return builtinFuncList.get(0);
    }

    public IRFunction getBuiltinFunction(String name) {
        for (IRFunction builtinFunc : builtinFuncList) {
            if (Objects.equals(builtinFunc.name, "_bot_str" + name))
                return builtinFunc;
            if (Objects.equals(builtinFunc.name, name))
                return builtinFunc;
        }
        throw new Error("<codegen error> cannot find builtin function \"" + name + "\"");
    }

    public StrConstant getStrConstant(String data) {
        for (StrConstant strConst : strConstList) {
            if (Objects.equals(strConst.constData, data))
                return strConst;
        }
        // generate a new string constant
        StrConstant newStrConst = new StrConstant(data);
        strConstList.add(newStrConst);
        return newStrConst;
    }

}
