package chaos.compiler.middleend.llvmir.instruction;

import chaos.compiler.frontend.ast.node.expr.BinaryExprNode;
import chaos.compiler.middleend.llvmir.Value;

public class BinaryInst extends BaseInst {

    public enum BinaryOpcode {
        ADD_NSW, SUB_NSW, MUL_NSW, SDIV, SREM, SHL, ASHR, AND, OR, XOR
    }

    public BinaryOpcode opcode;

    public BinaryInst(BinaryOpcode opcode, Value op1, Value op2) {
        super(op1.type);
        this.opcode = opcode;
        addUse(op1);
        addUse(op2);
    }

}
