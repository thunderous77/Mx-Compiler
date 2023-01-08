package chaos.compiler.backend.asm.hierarchy;

import chaos.compiler.backend.asm.operand.AsmBaseOperand;
import chaos.compiler.backend.asm.operand.AsmImmediate;
import chaos.compiler.backend.asm.operand.AsmRegister;
import chaos.compiler.backend.asm.operand.AsmVirtualReg;

import java.util.ArrayList;

public class AsmFunction extends AsmBaseOperand{

    public String name = null;
    public int spOffset = 0;

    public final ArrayList<AsmBlock> blockList = new ArrayList<>();
    public final ArrayList<AsmRegister> argsList = new ArrayList<>();
    public AsmBlock entryBlock, exitBlock;

    public AsmVirtualReg ra = null;
    public ArrayList<AsmVirtualReg> virRegList = new ArrayList<>();
    public ArrayList<AsmImmediate> offsetImmList = new ArrayList<>();

    public AsmFunction(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
