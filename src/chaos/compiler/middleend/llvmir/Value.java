package chaos.compiler.middleend.llvmir;

import chaos.compiler.middleend.llvmir.instruction.BaseInst;
import chaos.compiler.middleend.llvmir.type.IRBaseType;

import java.util.*;

abstract public class Value {

    // value rename
    public static Boolean rename = false;
    public static HashMap<String, Integer> renameMap = new HashMap<>();

    public static String rename(String rawName) {
        if (!rename) return rawName;
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
    public LinkedList<Use> useList = new LinkedList<>();
    public String name;

    public Value(IRBaseType type) {
        this.name = "AnonymousType";
        this.type = type;
    }

    public Value(String name, IRBaseType type) {
        this.name = rename(name);
        this.type = type;
    }

    public String identifier() {
        return "%" + name;
    }

    public String typeIdentifier() {
        return type + " " + identifier();
    }

}
