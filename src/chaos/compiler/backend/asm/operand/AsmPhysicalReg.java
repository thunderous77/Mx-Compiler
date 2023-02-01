package chaos.compiler.backend.asm.operand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class AsmPhysicalReg extends AsmRegister{

    public static final ArrayList<String> RV32RegNameList = new ArrayList<String>(Arrays.asList(
            "zero", "ra", "sp", "gp", "tp", "t0", "t1", "t2", "s0", "s1",
            "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7",
            "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10", "s11",
            "t3", "t4", "t5", "t6"
    ));

    public static final HashMap<String, AsmPhysicalReg> phyRegMap = new HashMap<>() {
        {RV32RegNameList.forEach(regName -> put(regName, new AsmPhysicalReg(regName)));}
    };

    public static AsmPhysicalReg reg(String regName) {return phyRegMap.get(regName);}
    public static AsmPhysicalReg a(int index) {return phyRegMap.get("a" + index);}
    public static AsmPhysicalReg t(int index) {return phyRegMap.get("t" + index);}
    public static AsmPhysicalReg s(int index) {return phyRegMap.get("s" + index);}

    public static ArrayList<AsmPhysicalReg> callerSavedList = new ArrayList<>();
    public static ArrayList<AsmPhysicalReg> calleeSavedList = new ArrayList<>();
    public static ArrayList<AsmPhysicalReg> assignableList = new ArrayList<>();

    static {
        for (int i = 0; i <= 6; ++i) callerSavedList.add(t(i));
        for (int i = 0; i <= 7; ++i) callerSavedList.add(a(i));
        callerSavedList.add(reg("ra"));

        for (int i = 0; i <= 11; ++i) calleeSavedList.add(s(i));

        assignableList.addAll(callerSavedList);
        assignableList.addAll(calleeSavedList);
    }

    public AsmPhysicalReg(String identifier) {
        super(identifier);
    }

    }
