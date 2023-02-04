package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.operand.AsmImmediate;
import chaos.compiler.backend.asm.operand.AsmRegister;

import java.util.HashSet;


public abstract class AsmBaseInst {

    public AsmRegister rd, rs1, rs2;
    public AsmImmediate imm;

    public AsmBaseInst(AsmRegister rd, AsmRegister rs1, AsmRegister rs2, AsmImmediate imm, AsmBlock parentBlock) {
        this.rd = rd;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.imm = imm;
        if (parentBlock != null) parentBlock.addInst(this);
    }

    public void replaceUse(AsmRegister oldUse, AsmRegister newUse) {
        if (rs1 == oldUse) rs1 = newUse;
        if (rs2 == oldUse) rs2 = newUse;
    }

    public void replaceDef(AsmRegister oldDef, AsmRegister newDef) {
        if (rd == oldDef) {
            rd = newDef;
        }
    }

    public HashSet<AsmRegister> uses() {
        HashSet<AsmRegister> ret = new HashSet<>();
        if (rs1 != null) ret.add(rs1);
        if (rs2 != null) ret.add(rs2);
        return ret;
    }

    public HashSet<AsmRegister> defs() {
        HashSet<AsmRegister> ret = new HashSet<>();
        if (rd != null) ret.add(rd);
        return ret;
    }

    public abstract AsmBaseInst copy();

    public abstract String format();

}
