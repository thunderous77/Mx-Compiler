package chaos.compiler.backend.asm;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.inst.AsmLiInst;
import chaos.compiler.backend.asm.operand.*;
import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.constant.IRBoolConstant;
import chaos.compiler.middleend.llvmir.constant.IRIntergerConstant;
import chaos.compiler.middleend.llvmir.constant.IRNullPointerConstant;

import java.util.HashMap;
import java.util.Map;

public class AsmCurrent {

    public AsmBlock block;
    public AsmFunction function;

    public Map<Integer, AsmRegister> liRecoder = new HashMap<>();

    public AsmRegister queryReg(IRValue value) {
        if (value.asmOperand != null) return (AsmRegister) value.asmOperand;

        Integer retInt = null;
        if (value instanceof IRIntergerConstant) retInt = ((IRIntergerConstant) value).constData;
        else if (value instanceof IRBoolConstant) retInt = ((IRBoolConstant) value).constData ? 1 : 0;
        else if (value instanceof IRNullPointerConstant) retInt = 0;

        if (retInt != null && retInt == 0) {
            value.asmOperand = AsmPhysicalReg.reg("zero");
            return AsmPhysicalReg.reg("zero");
        }

        AsmRegister retReg;
        if (liRecoder.containsKey(retInt)) retReg = liRecoder.get(retInt);
        else {
            retReg = new AsmVirtualReg(value.type.size());
            if (retInt != null) {
                new AsmLiInst(retReg, new AsmImmediate(retInt) ,this.block);
                liRecoder.put(retInt, retReg);
            }
        }

        if (!(value instanceof IRIntergerConstant || value instanceof IRBoolConstant)) value.asmOperand = retReg;
        return retReg;
    }

    public AsmImmediate queryImm(int value) {
        return new AsmImmediate(value);
    }

    public AsmImmediate queryImm(IRValue value) {
        if (value.asmOperand instanceof AsmOffsetStack) return (AsmImmediate) value.asmOperand;
        if (value instanceof IRIntergerConstant) return new AsmImmediate(((IRIntergerConstant) value).constData);
        if (value instanceof IRBoolConstant) return new AsmImmediate (((IRBoolConstant) value).constData ? 1 : 0);
        return null;
    }

}
