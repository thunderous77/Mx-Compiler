package chaos.compiler.backend.asm.hierarchy;

import chaos.compiler.backend.asm.inst.AsmBaseInst;
import chaos.compiler.backend.asm.operand.AsmBaseOperand;

import java.util.LinkedList;

public class AsmBlock extends AsmBaseOperand {

    public LinkedList<AsmBaseInst> instList = new LinkedList<>();

    public AsmBlock(String label) {
        super(label);
    }

    public void addInst(AsmBaseInst inst) {
        instList.add(inst);
    }

}
