package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.operand.AsmPhysicalReg;
import chaos.compiler.backend.asm.operand.AsmRegister;

import java.util.HashSet;

public class AsmRetInst extends AsmBaseInst{

    public AsmRetInst(AsmBlock parentBlock) {
        super(null, null, null, null, parentBlock);
    }

    @Override
    public HashSet<AsmRegister> uses() {
        HashSet<AsmRegister> ret = new HashSet<AsmRegister>();
        ret.add(AsmPhysicalReg.reg("ra"));
        return ret;
    }

    @Override
    public AsmBaseInst copy() {
        return new AsmRetInst(null);
    }

    @Override
    public String format() {
        return "ret";
    }

}
