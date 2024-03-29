package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.operand.AsmPhysicalReg;
import chaos.compiler.backend.asm.operand.AsmRegister;

import java.util.HashSet;

public class AsmCallInst extends AsmBaseInst{

    public final AsmFunction callFunc;

    public boolean isTailCall;

    public AsmCallInst(AsmFunction callFunc, AsmBlock parentBlock, boolean isTailCall) {
        super(null, null, null, null, parentBlock);
        this.callFunc = callFunc;
        this.isTailCall = isTailCall;
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
        return new AsmCallInst(callFunc, null, isTailCall);
    }

    @Override
    public String format() {
        return String.format("%s\t%s", "call", callFunc.toString());
    }

}
