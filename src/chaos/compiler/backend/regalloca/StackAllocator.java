package chaos.compiler.backend.regalloca;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.hierarchy.AsmModule;
import chaos.compiler.backend.asm.inst.AsmBaseInst;
import chaos.compiler.backend.asm.operand.AsmImmediate;
import chaos.compiler.backend.asm.operand.AsmOffsetStack;

public class StackAllocator {

    private final int SpUnit = 16;

    public void runOnModule(AsmModule module) {
        module.functionList.forEach(this::runOnFunction);
    }

    public void runOnFunction(AsmFunction function) {
        function.totalStackUse += function.callerArgStackUse + function.allocaStackUse + function.spillStackUse;
        if (function.totalStackUse % SpUnit != 0) function.totalStackUse = (function.totalStackUse / SpUnit + 1) * SpUnit;

        // stack allocate
        for (AsmBlock block : function.blockList) {
            for (AsmBaseInst inst : block.instList) {
                if (inst.imm instanceof AsmOffsetStack) {
                    switch (((AsmOffsetStack) inst.imm).stackType) {
                        case callerArgs: {
                            inst.imm = new AsmImmediate(inst.imm.value);
                            break;
                        }
                        case alloca: {
                            inst.imm = new AsmImmediate(inst.imm.value + function.callerArgStackUse);
                            break;
                        }
                        case spill: {
                            inst.imm = new AsmImmediate(inst.imm.value + function.callerArgStackUse + function.allocaStackUse);
                            break;
                        }
                        case calleeArgs: {
                            inst.imm = new AsmImmediate(inst.imm.value + function.totalStackUse);
                            break;
                        }
                        case lowerSp: {
                            inst.imm = new AsmImmediate(-function.totalStackUse);
                            break;
                        }
                        case raiseSp: {
                            inst.imm = new AsmImmediate(function.totalStackUse);
                            break;
                        }
                    }
                }
            }
        }
    }

}
