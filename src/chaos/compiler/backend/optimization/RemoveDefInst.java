package chaos.compiler.backend.optimization;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.inst.AsmBaseInst;
import chaos.compiler.backend.asm.inst.AsmBrInst;
import chaos.compiler.backend.asm.inst.AsmCallInst;
import chaos.compiler.backend.asm.operand.AsmPhysicalReg;

import java.util.HashMap;
import java.util.HashSet;

// remove unnecessary definition instrustion

public class RemoveDefInst {

    public void runOnFunc(AsmFunction function) {
        for (AsmBlock block : function.blockList) {
            HashMap<AsmPhysicalReg, AsmBaseInst> defButNotUsed = new HashMap<>();
            HashSet<AsmBaseInst> toRemoveSet = new HashSet<>();

            for (AsmBaseInst inst : block.instList) {
                if (inst.rs1 != null && inst.rs1.color != null)
                    defButNotUsed.remove(inst.rs1.color);
                if (inst.rs2 != null && inst.rs2.color != null)
                    defButNotUsed.remove(inst.rs2.color);

                if (inst.rd != null && inst.rd.color != null) {
                    if (defButNotUsed.containsKey(inst.rd.color))
                        toRemoveSet.add(defButNotUsed.get(inst.rd.color));

                    defButNotUsed.put(inst.rd.color, inst);
                }

                if (inst instanceof AsmCallInst || inst instanceof AsmBrInst)
                    defButNotUsed.clear();
            }

            block.instList.removeAll(toRemoveSet);
        }
    }

}
