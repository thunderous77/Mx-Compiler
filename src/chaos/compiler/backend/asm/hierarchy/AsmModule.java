package chaos.compiler.backend.asm.hierarchy;

import chaos.compiler.backend.asm.operand.AsmGlobalReg;
import chaos.compiler.backend.asm.operand.AsmPhysicalReg;

import java.util.ArrayList;
import java.util.HashMap;

public class AsmModule {

    public ArrayList<AsmFunction> functionList = new ArrayList<>();
    public ArrayList<AsmGlobalReg> globalVarList = new ArrayList<>();
    public ArrayList<AsmGlobalReg> strConstList = new ArrayList<>();

}
