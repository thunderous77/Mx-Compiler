package chaos.compiler.middleend.llvmir;

import chaos.compiler.middleend.llvmir.constant.IRNullPointerConstant;
import chaos.compiler.middleend.llvmir.instruction.IRMoveInst;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

import java.util.*;

public class IRValue {

    // value rename
    public static Boolean rename = true;
    public static HashMap<String, Integer> renameMap = new HashMap<>();
    public Set<IRMoveInst> moveDefs = new HashSet<>();

    public static String rename(String rawName) {
        Integer renameCnt = renameMap.get(rawName);
        if (renameCnt == null) renameCnt = 0;
        renameMap.put(rawName, renameCnt + 1);
        if (renameCnt == 0) return rawName;
        else return rawName + "." + renameCnt;
    }

    public static String addrRename(String rawName) {
        return rawName + ".addr";
    }

    public static String getRawName(String name) {
        int lastIndex = name.lastIndexOf(".");
        if (lastIndex < 0) return name;
        else return name.substring(0, lastIndex);
    }

    public static String loadRename(String name) {
        int lastAddrIndex = name.lastIndexOf(".addr");
        if (lastAddrIndex < 0) return name + ".load";
        else return name.substring(0, lastAddrIndex) + ".load";
    }

    public IRBaseType type;
    public LinkedList<IRUse> useList = new LinkedList<>();
    public String name;
    public IRValue storePtr = null;

    public IRValue(IRBaseType type) {
        this.name = "";
        this.type = type;
    }

    public IRValue(String name, IRBaseType type) {
        this.name = rename(name);
        this.type = type;
    }

    public String identifier() {
        if (this instanceof IRNullPointerConstant)
            return "null";
        else return "%" + name;
    }

    public String typedIdentifier() {
        return type + " " + identifier();
    }

}
