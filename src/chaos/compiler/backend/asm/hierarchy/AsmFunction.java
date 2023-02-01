package chaos.compiler.backend.asm.hierarchy;

import chaos.compiler.backend.asm.operand.AsmBaseOperand;
import chaos.compiler.backend.asm.operand.AsmImmediate;
import chaos.compiler.backend.asm.operand.AsmRegister;
import chaos.compiler.backend.asm.operand.AsmVirtualReg;

import java.util.ArrayList;

public class AsmFunction extends AsmBaseOperand{

    public final ArrayList<AsmBlock> blockList = new ArrayList<>();
    public final ArrayList<AsmRegister> argsList = new ArrayList<>();
    public AsmBlock entryBlock, exitBlock;

    public int callerArgStackUse = 0, allocaStackUse = 0, spillStackUse = 0, calleeArgStackUse = 0, totalStackUse = 0;

    public AsmFunction(String identifier) {
        super(identifier);
    }

}
