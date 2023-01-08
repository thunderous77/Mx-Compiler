package chaos.compiler.backend.asm.hierarchy;

import chaos.compiler.backend.asm.operand.AsmGlobalReg;
import chaos.compiler.backend.asm.operand.AsmPhysicalReg;

import java.util.ArrayList;
import java.util.HashMap;

public class AsmModule {

    public ArrayList<AsmFunction> functionList = new ArrayList<>();
    public ArrayList<AsmGlobalReg> globalVarList = new ArrayList<>();
    public ArrayList<AsmGlobalReg> strConstList = new ArrayList<>();

    private HashMap<String, AsmPhysicalReg> phyRegMap = new HashMap<>();
    private HashMap<Integer, AsmPhysicalReg> phyRegIdMap = new HashMap<>();
    private String[] regName = new String[]{
            "zero", "ra", "sp", "gp", "tp", "t0",
            "t1", "t2", "s0", "s1", "a0", "a1", "a2",
            "a3", "a4", "a5", "a6", "a7", "s2", "s3",
            "s4", "s5", "s6", "s7", "s8", "s9", "s10",
            "s11", "t3", "t4", "t5", "t6"
    };

    public AsmModule() {
        for (int i = 0; i < regName.length; ++i) {
            AsmPhysicalReg reg = new AsmPhysicalReg(regName[i]);
            phyRegMap.put(regName[i],reg);
            phyRegIdMap.put(i, reg);
        }
    }

    public AsmPhysicalReg getReg(String name) {
        return new AsmPhysicalReg(name);
    }

    public AsmPhysicalReg getReg(int id) {
        return new AsmPhysicalReg(regName[id]);
    }

    public AsmPhysicalReg a(int id) {
        return phyRegMap.get("a"+id);
    }

    public AsmPhysicalReg s(int id) {
        return phyRegMap.get("s" + id);
    }

    public AsmPhysicalReg t(int id) {
        return phyRegMap.get("t" + id);
    }

    public ArrayList<AsmPhysicalReg> getCall() {
        ArrayList<AsmPhysicalReg> ret = new ArrayList<>();
        ret.add(getReg("s0"));
        return ret;
    }

}
