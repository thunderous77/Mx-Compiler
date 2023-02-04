package chaos.compiler.backend.asm;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.hierarchy.AsmModule;
import chaos.compiler.backend.asm.inst.*;
import chaos.compiler.backend.asm.operand.*;
import chaos.compiler.middleend.llvmir.IRUse;
import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.constant.IRBoolConstant;
import chaos.compiler.middleend.llvmir.constant.IRIntergerConstant;
import chaos.compiler.middleend.llvmir.constant.IRNullPointerConstant;
import chaos.compiler.middleend.llvmir.constant.IRStrConstant;
import chaos.compiler.middleend.llvmir.hierarchy.*;
import chaos.compiler.middleend.llvmir.instruction.*;
import chaos.compiler.middleend.llvmir.type.IRFunctionType;
import chaos.compiler.middleend.llvmir.type.IRPointerType;
import chaos.compiler.middleend.llvmir.type.IRStructType;
import chaos.utility.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.max;

public class AsmBuilder implements IRInstVisitor {

    public final AsmModule asmModule = new AsmModule();

    public AsmBlock curBlock;
    public AsmFunction curFunction;
    public Map<Integer, AsmRegister> liRecoder = new HashMap<>();


    public AsmBuilder(IRModule irModule) {
        this.runOnModule(irModule);
    }

    public void runOnModule(IRModule irModule) {
        for (IRGlobalVariable globalVar : irModule.globalVarList) {
            AsmGlobalReg globalReg = new AsmGlobalReg(globalVar.name);
            globalVar.asmOperand = globalReg;
            asmModule.globalVarList.add(globalReg);
        }
        for (IRStrConstant strConstant : irModule.strConstList) {
            AsmGlobalReg strReg = new AsmGlobalReg(strConstant.name, strConstant.constData);
            strConstant.asmOperand = strReg;
            asmModule.strConstList.add(strReg);
        }

        for (IRFunction builtinFunc : irModule.builtinFuncList) {
            AsmFunction function = new AsmFunction(builtinFunc.name);
            builtinFunc.asmOperand = function;
            for (int i = 0; i < ((IRFunctionType) builtinFunc.type).argTypes.size(); i++) {
                AsmVirtualReg reg = new AsmVirtualReg(builtinFunc.getArgType(i).size());
                function.argsList.add(reg);

                // spill
                if (i >= 8) {
                    reg.offsetStack = new AsmOffsetStack(function.calleeArgStackUse, AsmOffsetStack.StackType.calleeArgs);
                    function.calleeArgStackUse += 4;
                }
            }
        }

        for (IRFunction irFunction : irModule.funcList) {
            AsmFunction function = new AsmFunction(irFunction.name);
            irFunction.asmOperand = function;

            for (int i = 0; i < ((IRFunctionType) irFunction.type).argTypes.size(); i++) {
                IRValue arg = irFunction.operandList.get(i).value;
                AsmVirtualReg reg = new AsmVirtualReg(arg.type.size());
                arg.asmOperand = reg;
                function.argsList.add(reg);

                // spill
                if (i >= 8) {
                    reg.offsetStack = new AsmOffsetStack(function.calleeArgStackUse, AsmOffsetStack.StackType.calleeArgs);
                    function.calleeArgStackUse += 4;
                }
            }

            asmModule.functionList.add((AsmFunction) irFunction.asmOperand);

            for (IRBlock irBlock : irFunction.blockList) {
                AsmBlock block = new AsmBlock(irBlock.name);
                block.loopDepth = irBlock.loopDepth;
                irBlock.asmOperand = block;
                function.blockList.add(block);
            }

            for (IRBlock irBlock : irFunction.blockList) {
                irBlock.preBlockList.forEach(pre -> ((AsmBlock) irBlock.asmOperand).preBlockList.add((AsmBlock) pre.asmOperand));
                irBlock.nextBlockList.forEach(next -> ((AsmBlock) irBlock.asmOperand).nextBlockList.add((AsmBlock) next.asmOperand));
            }
            function.entryBlock = (AsmBlock) irFunction.entryBlock.asmOperand;
            function.exitBlock = (AsmBlock) irFunction.exitBlock.asmOperand;
        }

        irModule.funcList.forEach(this::runOnFunc);
    }

    public void runOnFunc(IRFunction function) {
        curFunction = (AsmFunction) function.asmOperand;

        // lower the stack pointer
        new AsmALUInst("add", AsmPhysicalReg.reg("sp"), AsmPhysicalReg.reg("sp"),
                new AsmOffsetStack(0, AsmOffsetStack.StackType.lowerSp), curFunction.entryBlock);

        // backup callee
        ArrayList<AsmRegister> calleeSaveTempList = new ArrayList<>();
        for (AsmPhysicalReg phyReg : AsmPhysicalReg.calleeSavedList) {
            AsmVirtualReg rd = new AsmVirtualReg();
            calleeSaveTempList.add(rd);
            new AsmMvInst(rd, phyReg, curFunction.entryBlock);
        }

        // new ra
        AsmVirtualReg raTemp = new AsmVirtualReg();
        new AsmMvInst(raTemp, AsmPhysicalReg.reg("ra"), curFunction.entryBlock);

        // move arguments 0~7 to reg
        for (int i = 0; i < Integer.min(curFunction.argsList.size(), 8); i++)
            new AsmMvInst(curFunction.argsList.get(i), AsmPhysicalReg.a(i), curFunction.entryBlock);

        // load arguments in mem to reg
        for (int i = 8; i < curFunction.argsList.size(); i++) {
            new AsmLoadInst(function.getOperand(i).type.size(), curFunction.argsList.get(i), AsmPhysicalReg.reg("sp"),
                    curFunction.argsList.get(i).offsetStack, curFunction.entryBlock);
        }

        function.blockList.forEach(this::runOnBlock);

        // callee temp back
        for (int i = 0; i < AsmPhysicalReg.calleeSavedList.size(); i++)
            new AsmMvInst(AsmPhysicalReg.calleeSavedList.get(i), calleeSaveTempList.get(i), curFunction.exitBlock);

        // ra temp back
        new AsmMvInst(AsmPhysicalReg.reg("ra"), raTemp, curFunction.exitBlock);

        // sp back
        new AsmALUInst("add", AsmPhysicalReg.reg("sp"), AsmPhysicalReg.reg("sp"),
                new AsmOffsetStack(0, AsmOffsetStack.StackType.raiseSp), curFunction.exitBlock);

        // return
        new AsmRetInst(curFunction.exitBlock);

        curFunction.blockList.forEach(block -> {
            block.instList.forEach(this::allocate);
        });
        AsmVirtualReg.resetRegNum();
    }

    public void runOnBlock(IRBlock block) {
        curBlock = (AsmBlock) block.asmOperand;
        liRecoder.clear();
        block.instList.forEach(inst -> inst.accept(this));
    }

    private void allocate(AsmBaseInst inst) {
        if (!(inst.imm instanceof AsmOffsetMemory)) return;
        inst.rs1 = ((AsmOffsetMemory) inst.imm).pointer;
        inst.imm = new AsmImmediate(inst.imm.value);
    }

    @Override
    public void visit(IRAllocaInst inst) {
        inst.asmOperand = new AsmOffsetStack(curFunction.allocaStackUse, AsmOffsetStack.StackType.alloca);
        curFunction.allocaStackUse += 4;
    }

    @Override
    public void visit(IRBinaryInst inst) {
        AsmRegister instReg = queryReg(inst);
        optALU(AsmTranslator.translateArithmOp(inst.op), instReg, inst.lValue(), inst.rValue());
    }

    @Override
    public void visit(IRBitcastInst inst) {
        // ignore type
        optMove(queryReg(inst), inst.getOperand(0));
    }

    @Override
    public void visit(IRBrInst inst) {
        inst.asmOperand = null;

        if (inst.isJump()) {
            new AsmJmpInst((AsmBlock) inst.destBlock().asmOperand, curBlock);
            return;
        }

        if (inst.condition() instanceof IRIcmpInst && ((IRIcmpInst) inst.condition()).forBr()) {
            // coalesce cmp and br
            Pair<String, Boolean> result = AsmTranslator.translateCmpOp(((IRIcmpInst) inst.condition()).op);
            if (result.second())
                new AsmBrInst(result.first(), queryReg(((IRIcmpInst) inst.condition()).rValue()), queryReg(((IRIcmpInst) inst.condition()).lValue()), (AsmBlock) inst.ifTrueBlock().asmOperand, curBlock);
            else
                new AsmBrInst(result.first(), queryReg(((IRIcmpInst) inst.condition()).lValue()), queryReg(((IRIcmpInst) inst.condition()).rValue()), (AsmBlock) inst.ifTrueBlock().asmOperand, curBlock);
        } else
            new AsmBrInst("ne", queryReg(inst.condition()), queryReg(new IRIntergerConstant(0)), (AsmBlock) inst.ifTrueBlock().asmOperand, curBlock);

        new AsmJmpInst((AsmBlock) inst.ifFalseBlock().asmOperand, curBlock);
    }

    @Override
    public void visit(IRCallInst inst) {
        AsmFunction callFunction = (AsmFunction) inst.callFunc().asmOperand;

        for (int i = 0; i < Integer.min(inst.callFunc().getArgNum(), 8); i++) {
            if (inst.getArg(i) instanceof IRGlobalValue) {
                if ((((IRGlobalValue) inst.getArg(i)).gpRegMark))
                    new AsmMvInst(AsmPhysicalReg.a(i), AsmPhysicalReg.reg("gp"), curBlock);
                else new AsmLaInst(AsmPhysicalReg.a(i), inst.getArg(i).asmOperand.identifier, curBlock);
            } else optMove(AsmPhysicalReg.a(i), inst.getArg(i));
        }

        if (callFunction.argsList != null) {
            for (int i = 8; i < callFunction.argsList.size(); i++)
                new AsmStoreInst(inst.getArg(i).type.size(), AsmPhysicalReg.reg("sp"), queryReg(inst.getArg(i)),
                        new AsmOffsetStack(callFunction.argsList.get(i).offsetStack.value, AsmOffsetStack.StackType.callerArgs), curBlock);
        }

        curFunction.callerArgStackUse = max(curFunction.callerArgStackUse, callFunction.calleeArgStackUse);

        new AsmCallInst(callFunction, curBlock);
        if (!inst.callFunc().isVoid())
            new AsmMvInst(queryReg(inst), AsmPhysicalReg.reg("a0"), curBlock);
    }

    @Override
    public void visit(IRGEPInst inst) {
        IRValue index = inst.isGetMember() ? inst.memberIndex() : inst.ptrMoveIndex();
        IRStructType classType = inst.isGetMember() ? (IRStructType) ((IRPointerType) inst.headPointer().type).pointedType : null;
        int elementSize = ((IRPointerType) inst.headPointer().type).pointedType.size();

        // local & const && only for load/store
        if (index instanceof IRIntergerConstant && !(inst.headPointer() instanceof IRGlobalValue) && specialGEPCheck(inst)) {
            int offset = 0;
            if (classType != null) offset = classType.memberOffset(((IRIntergerConstant) index).constData);
            else offset = ((IRIntergerConstant) index).constData * elementSize;
            inst.asmOperand = new AsmOffsetMemory(queryReg(inst.headPointer()), offset);
            return;
        }

        AsmRegister instReg = queryReg(inst);
        AsmRegister gepReg = optGEP(inst.headPointer(), index, elementSize, classType);
        new AsmMvInst(instReg, gepReg, curBlock);
    }

    @Override
    public void visit(IRIcmpInst inst) {
        // merge into next br inst
        if (inst.forBr()) return;

        AsmRegister instReg = queryReg(inst);
        switch (inst.op) {
            case "slt":
                optALU("slt", instReg, inst.lValue(), inst.rValue());
                break;
            case "sgt":
                optALU("slt", instReg, inst.rValue(), inst.lValue());
                break;
            case "sge": // a >= b -> !(a < b)
                optALU("slt", instReg, inst.lValue(), inst.rValue());
                new AsmALUInst("xor", instReg, instReg, queryImm(1), curBlock);
                break;
            case "sle":// a <= b -> !(b < a)
                optALU("slt", instReg, inst.rValue(), inst.lValue());
                new AsmALUInst("xor", instReg, instReg, queryImm(1), curBlock);
                break;
            case "eq": { // xor = a ^ b; seqz rd, xor(a == b -> xor == 0)
                AsmVirtualReg xorReg = new AsmVirtualReg();
                optALU("xor", xorReg, inst.lValue(), inst.rValue());
                new AsmALUInst("seqz", instReg, xorReg, curBlock);
                break;
            }
            case "ne": { // xor = a ^ b; seqz rd, xor(a != b -> xor != 0)
                AsmVirtualReg xorReg = new AsmVirtualReg();
                optALU("xor", xorReg, inst.lValue(), inst.rValue());
                new AsmALUInst("snez", instReg, xorReg, curBlock);
                break;
            }
        }
    }

    @Override
    public void visit(IRLoadInst inst) {
        AsmRegister instReg = queryReg(inst);
        if (inst.loadPtr() instanceof IRGlobalValue) {
            if (((IRGlobalValue) inst.loadPtr()).gpRegMark)
                new AsmMvInst(instReg, AsmPhysicalReg.reg("gp"), curBlock);
            else {
                AsmVirtualReg luiReg = new AsmVirtualReg();
                AsmGlobalReg globalReg = (AsmGlobalReg) queryReg(inst.loadPtr());
                new AsmLuiInst(luiReg, new AsmGlobalAddress(globalReg, AsmGlobalAddress.HiLo.hi), curBlock);
                new AsmLoadInst(inst.type.size(), instReg, luiReg, new AsmGlobalAddress(globalReg, AsmGlobalAddress.HiLo.lo), curBlock);
            }
        } else {
            if (inst.loadPtr().asmOperand instanceof AsmOffsetStack)
                new AsmLoadInst(inst.type.size(), instReg, AsmPhysicalReg.reg("sp"), queryImm(inst.loadPtr()), curBlock);
            else if (inst.loadPtr().asmOperand instanceof AsmOffsetMemory)
                new AsmLoadInst(inst.type.size(), instReg, null, (AsmImmediate) inst.loadPtr().asmOperand, curBlock);
            else new AsmLoadInst(inst.type.size(), instReg, queryReg(inst.loadPtr()), queryImm(0), curBlock);
        }
    }

    @Override
    public void visit(IRPhiInst inst) {
        return;
    }

    @Override
    public void visit(IRRetInst inst) {
        if (inst.isVoid()) return;
        optMove(AsmPhysicalReg.reg("a0"), inst.retValue());
    }

    @Override
    public void visit(IRStoreInst inst) {
        AsmRegister storeValueReg = queryReg(inst.storeValue());
        if (inst.storePtr() instanceof IRGlobalValue) {
            if (((IRGlobalValue) inst.storePtr()).gpRegMark)
                new AsmMvInst(AsmPhysicalReg.reg("gp"), storeValueReg, curBlock);
            else {
                AsmVirtualReg luiReg = new AsmVirtualReg();
                AsmGlobalReg globalReg = (AsmGlobalReg) queryReg(inst.storePtr());
                new AsmLuiInst(luiReg, new AsmGlobalAddress(globalReg, AsmGlobalAddress.HiLo.hi), curBlock);
                new AsmStoreInst(inst.storeValue().type.size(), luiReg, storeValueReg, new AsmGlobalAddress(globalReg, AsmGlobalAddress.HiLo.lo), curBlock);
            }
        } else {
            if (inst.storePtr().asmOperand instanceof AsmOffsetStack)
                new AsmStoreInst(inst.storeValue().type.size(), AsmPhysicalReg.reg("sp"), storeValueReg, queryImm(inst.storePtr()), curBlock);
            else if (inst.storePtr().asmOperand instanceof AsmOffsetMemory)
                new AsmStoreInst(inst.storeValue().type.size(), null, storeValueReg, (AsmImmediate) inst.storePtr().asmOperand, curBlock);
            else
                new AsmStoreInst(inst.storeValue().type.size(), queryReg(inst.storePtr()), storeValueReg, queryImm(0), curBlock);
        }
    }

    @Override
    public void visit(IRTruncInst inst) {
        optMove(queryReg(inst), inst.getOperand(0));
    }

    @Override
    public void visit(IRZextInst inst) {
        optMove(queryReg(inst), inst.getOperand(0));
    }

    @Override
    public void visit(IRMoveInst inst) {
        optMove(queryReg(inst.dest()), inst.source());
    }

    private static boolean isValidImm(IRValue value) {
        return (value instanceof IRIntergerConstant && ((IRIntergerConstant) value).constData >= -2048 && ((IRIntergerConstant) value).constData < 2048) || value instanceof IRBoolConstant;
    }

    private boolean specialGEPCheck(IRGEPInst inst) {
        if (inst.asmOperand != null) return false;
        for (IRUse use : inst.useList)
            if (!(use.user instanceof IRLoadInst || use.user instanceof IRStoreInst)) return false;
        return true;
    }

    private static boolean equalZero(IRValue value) {
        return value instanceof IRNullPointerConstant || (value instanceof IRIntergerConstant && ((IRIntergerConstant) value).constData == 0) || (value instanceof IRBoolConstant && !((IRBoolConstant) value).constData);
    }


    public void optMove(AsmRegister dest, IRValue source) {
        // immediate 有位数限制
        if (isValidImm(source)) new AsmLiInst(dest, queryImm(source), curBlock);
        else new AsmMvInst(dest, queryReg(source), curBlock);
    }

    public void optALU(String op, AsmRegister dest, IRValue lValue, IRValue rValue) {
        new AsmALUInst(op, dest, queryReg(lValue), queryReg(rValue), curBlock);
    }

    public AsmRegister optGEP(IRValue ptrPos, IRValue index, int elementSize, IRStructType classType) {
        AsmVirtualReg gepReg = new AsmVirtualReg();
        if (classType != null) {
            if (equalZero(index)) {
                AsmRegister ptrReg = queryReg(ptrPos);
                if (ptrPos instanceof IRGlobalValue) {
                    if (((IRGlobalValue) ptrPos).gpRegMark) new AsmMvInst(gepReg, AsmPhysicalReg.reg("gp"), curBlock);
                    else new AsmLaInst(gepReg, ptrReg.identifier, curBlock);
                } else new AsmMvInst(gepReg, ptrReg, curBlock);
            } else {
                int memberOffset = classType.memberOffset(((IRIntergerConstant) index).constData);
                optALU("add", gepReg, ptrPos, new IRIntergerConstant(memberOffset));
            }
        } else {
            if (index instanceof IRIntergerConstant) {
                if (equalZero(index)) {
                    AsmRegister ptrReg = queryReg(ptrPos);
                    if (ptrPos instanceof IRGlobalValue) {
                        if (((IRGlobalValue) ptrPos).gpRegMark)
                            new AsmMvInst(gepReg, AsmPhysicalReg.reg("gp"), curBlock);
                        else new AsmLaInst(gepReg, ptrReg.identifier, curBlock);
                    } else new AsmMvInst(gepReg, ptrReg, curBlock);
                } else {
                    int totalSize = ((IRIntergerConstant) index).constData * elementSize;
                    optALU("add", gepReg, ptrPos, new IRIntergerConstant(totalSize));
                }
            } else {
                AsmVirtualReg mulReg = new AsmVirtualReg();
                optALU("mul", mulReg, index, new IRIntergerConstant(elementSize));
                new AsmALUInst("add", gepReg, queryReg(ptrPos), mulReg, curBlock);
            }
        }
        return gepReg;
    }

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
                new AsmLiInst(retReg, new AsmImmediate(retInt), curBlock);
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
        if (value instanceof IRBoolConstant) return new AsmImmediate(((IRBoolConstant) value).constData ? 1 : 0);
        return null;
    }

}
