package chaos.compiler.backend.asm.hierarchy;

import chaos.compiler.backend.asm.inst.AsmBaseInst;
import chaos.compiler.backend.asm.operand.AsmBaseOperand;
import chaos.compiler.backend.asm.operand.AsmRegister;
import chaos.compiler.middleend.llvmir.hierarchy.Loop;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class AsmBlock extends AsmBaseOperand{

    public LinkedList<AsmBaseInst> instList = new LinkedList<>();
    public ArrayList<AsmBlock> preBlockList = new ArrayList<>(), nextBlockList = new ArrayList<>();
    public HashSet<AsmRegister> in = new HashSet<>(), out = new HashSet<>();
    public int loopDepth = 0;

    public AsmBlock(String label) {
        super(label);
    }

    public void addInst(AsmBaseInst inst) {
        instList.add(inst);
    }

}
