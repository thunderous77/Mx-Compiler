package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.operand.AsmPhysicalReg;
import chaos.compiler.backend.asm.operand.AsmRegister;

import java.util.HashSet;

public class AsmTailInst extends AsmBaseInst{

    private final AsmFunction callFunc;

    public AsmTailInst(AsmFunction callFunc, AsmBlock parentBlock) {
        super(null, null, null, null, parentBlock);
        this.callFunc = callFunc;
    }

    @Override
    public HashSet<AsmRegister> uses() {
        HashSet<AsmRegister> ret = new HashSet<>();
        for (int i = 0; i < Integer.min(8, callFunc.argsList.size()); i++)
            ret.add(AsmPhysicalReg.a(i));
        return ret;
    }

    @Override
    public HashSet<AsmRegister> defs() {
        return new HashSet<>(AsmPhysicalReg.callerSavedList);
    }

    @Override
    public AsmBaseInst copy() {
        return new AsmTailInst(callFunc, null);
    }

    @Override
    public String format() {
        // tail symbol
        return String.format("%s\t%s", "tail", callFunc.identifier);
    }

}
