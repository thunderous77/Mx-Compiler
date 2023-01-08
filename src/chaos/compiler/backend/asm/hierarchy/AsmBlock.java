package chaos.compiler.backend.asm.hierarchy;

import chaos.compiler.backend.asm.inst.AsmBaseInst;
import chaos.compiler.backend.asm.operand.AsmBaseOperand;

import java.util.ArrayList;
import java.util.LinkedList;

public class AsmBlock extends AsmBaseOperand{
    public String label = null;
    public ArrayList<AsmBaseInst> instList = new ArrayList<>();

    public AsmBlock() {
    }

    public void addInst(AsmBaseInst inst) {
        instList.add(inst);
    }

    public void addInstAtHead(AsmBaseInst inst) {
        instList.add(0, inst);
    }

    public void addInstAtEnd(AsmBaseInst inst) {
        instList.add(instList.size() - 1, inst);
    }

    @Override
    public String toString() {
        return label;
    }

}
