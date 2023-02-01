package chaos.compiler.middleend.analyzer;

import chaos.compiler.middleend.llvmir.IRValue;
import chaos.compiler.middleend.llvmir.constant.IRIntergerConstant;
import chaos.compiler.middleend.llvmir.hierarchy.IRBlock;
import chaos.compiler.middleend.llvmir.hierarchy.IRFunction;
import chaos.compiler.middleend.llvmir.instruction.IRBaseInst;
import chaos.compiler.middleend.llvmir.instruction.IRBitcastInst;
import chaos.compiler.middleend.llvmir.instruction.IRCallInst;
import chaos.compiler.middleend.llvmir.instruction.IRGEPInst;
import chaos.compiler.middleend.llvmir.type.IRPointerType;
import chaos.utility.UnionSet;
import com.sun.jdi.Value;

import java.util.HashSet;

public class AliasAnalyzer {

    private HashSet<IRValue> certain = new HashSet<>();
    private UnionSet<IRValue> bitcastUnion = new UnionSet<>();

    public void runOnFunc(IRFunction function) {
        certain.clear();
        bitcastUnion.clear();
        certain.addAll(function.parentModule.globalVarList);
        certain.addAll(function.parentModule.strConstList);

        for (IRBlock block : function.blockList)
            for (IRBaseInst inst : block.instList) {
                if (inst instanceof IRCallInst && ((IRCallInst) inst).callFunc() == function.parentModule.getMallocFunc())
                    certain.add(inst);
                if (inst instanceof IRBitcastInst && ((IRBitcastInst) inst).fromValue().type instanceof IRPointerType) {
                    bitcastUnion.setAlias(inst, ((IRBitcastInst) inst).fromValue());
                }
            }
    }

    public boolean mayAlias(IRValue addr1, IRValue addr2) {
        // Log.info("alias", addr1.identifier(), addr2.identifier());

        addr1 = bitcastUnion.getAlias(addr1);
        addr2 = bitcastUnion.getAlias(addr2);

        // Log.info("bitcast", addr1.identifier(), addr2.identifier());

        if (certain.contains(addr1) || certain.contains(addr2)) {
            return addr1.equals(addr2);
        }

        if (addr1 instanceof IRGEPInst) {
            if (addr2 instanceof IRGEPInst) {
                boolean ret = mayAlias(((IRGEPInst) addr1).headPointer(), ((IRGEPInst) addr2).headPointer());
                if (((IRGEPInst) addr1).indexNum() == 1 && ((IRGEPInst) addr2).indexNum() == 1) {
                    IRValue index1 = ((IRGEPInst) addr1).ptrMoveIndex();
                    IRValue index2 = ((IRGEPInst) addr2).ptrMoveIndex();

                    if (index1 instanceof IRIntergerConstant && index2 instanceof IRIntergerConstant && ((IRIntergerConstant) index1).constData != ((IRIntergerConstant) index2).constData) {
                        return false;
                    }
                }
                return ret;
            }
            else return mayAlias(((IRGEPInst) addr1).headPointer(), addr2);
        }

        if (addr2 instanceof IRGEPInst)
            return mayAlias(addr2, addr1);

        return addr1.type.match(addr2.type);
    }

}
