package chaos.compiler.frontend.semantic;

import java.util.Objects;

import chaos.compiler.frontend.utility.type.VarType;
import chaos.utility.Error;
import chaos.compiler.frontend.ast.node.expr.*;
import chaos.compiler.frontend.ast.node.stmt.*;
import chaos.compiler.frontend.utility.type.BaseType;

public class TypeMatcher {

    // unary:
    // LogicNot(!) -> bool
    // other(+-~) -> int
    public static void match(UnaryExprNode node) {
        if (Objects.equals(node.opcode, UnaryExprNode.unaryOp.LOGIC_NOT)) {
            if (!node.selfNode.type.match(BaseType.BuiltinType.BOOL))
                throw new Error(node.pos, "builtinType mismatched between: \"bool\" and \"" + node.selfNode.type.toString() + "\"");
        } else if (!node.selfNode.type.match(BaseType.BuiltinType.INT))
            throw new Error(node.pos, "builtinType mismatched between: \"int\" and \"" + node.selfNode.type.toString() + "\"");
    }

    // prefix : --,++ -> int
    public static void match(PrefixExprNode node) {
        if (!node.selfNode.isLeftValue()) {
            throw new Error(node.pos, "expecting a left-value in prefix expression");
        }
        if (!node.selfNode.type.match(BaseType.BuiltinType.INT)) {
            throw new Error(node.pos, "builtinType mismatched between: \"int\" and \"" + node.selfNode.type.toString() + "\"");
        }
    }

    // postfix: a++, a-- -> int, leftValue
    public static void match(PostfixExprNode node) {
        if (!node.selfNode.isLeftValue()) {
            throw new Error(node.pos, "expecting a left-value in postfix expression");
        }
        if (!node.selfNode.type.match(BaseType.BuiltinType.INT)) {
            throw new Error(node.pos, "builtinType mismatched between: \"int\" and \"" + node.selfNode.type.toString() + "\"");
        }
    }

    // member: a.b, a -> class
    public static void match(MemberExprNode node) {
        if (!node.superNode.type.match(BaseType.BuiltinType.CLASS))
            throw new Error(node.pos, "typeError: " + node.superNode.type.toString() + " is supposed to be a class");
    }

    // index: a[b] , b -> int
    public static void match(IndexExprNode node) {
        if (!(node.arrayNode.type instanceof VarType))
            throw new Error(node.pos, "typeError: " + node.arrayNode.type.toString() + " is an undefined/wrong array builtinType");
        if (((VarType) node.arrayNode.type).dimensize == 0)
            throw new Error(node.pos, "typeError: " + node.arrayNode.type.toString() + " is an undefined/wrong array builtinType");
        assert node.indexNode != null;
        if (!node.indexNode.type.match(BaseType.BuiltinType.INT))
            throw new Error(node.pos, "builtinType mismatched between: \"int\" and \"" + node.indexNode.type.toString() + "\"");
    }

    // binary: a op b
    // string & string: == != >= <= > < +
    // &&, ||: bool && bool
    // +, -, *, /, %, <<, >>, ^, &, |: int + int
    // >, >=, <, <=: int > int
    // ==, !=: all
    public static void match(BinaryExprNode node) {
        if (!node.lNode.type.match(node.rNode.type))
            throw new Error(node.pos, "builtinType mismatched between: \"" + node.lNode.type.toString() + "\" and \"" + node.rNode.type.toString() + "\"");
        if (node.lNode.type.match(BaseType.BuiltinType.STRING)) {
            if (!Objects.equals(node.opcode, BinaryExprNode.binaryOp.EQUAL) &&
                    !Objects.equals(node.opcode, BinaryExprNode.binaryOp.NOT_EQUAL) &&
                    !Objects.equals(node.opcode, BinaryExprNode.binaryOp.GREATER_EQUAL) &&
                    !Objects.equals(node.opcode, BinaryExprNode.binaryOp.LESS_EQUAL) &&
                    !Objects.equals(node.opcode, BinaryExprNode.binaryOp.GREATER) &&
                    !Objects.equals(node.opcode, BinaryExprNode.binaryOp.LESS) &&
                    !Objects.equals(node.opcode, BinaryExprNode.binaryOp.ADD))
                throw new Error(node.pos, "invalid operator for builtinType \"string\"");
            return;
        }
        if (Objects.equals(node.opcode, BinaryExprNode.binaryOp.LOGIC_AND) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.LOGIC_OR)) {
            if (!node.lNode.type.match(BaseType.BuiltinType.BOOL))
                throw new Error(node.pos, "invalid operator for builtinType \"" + node.lNode.type + "\"");
        }
        if (Objects.equals(node.opcode, BinaryExprNode.binaryOp.ADD) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.SUB) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.MUL) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.DIV) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.MOD) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.SHIFT_LEFT) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.SHIFT_RIGHT) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.BIT_AND) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.BIT_OR) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.BIT_XOR)) {
            if (!node.lNode.type.match(BaseType.BuiltinType.INT))
                throw new Error(node.pos, "invalid operator for builtinType \"" + node.lNode.type + "\"");
        }
        if (Objects.equals(node.opcode, BinaryExprNode.binaryOp.GREATER) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.GREATER_EQUAL) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.LESS) ||
                Objects.equals(node.opcode, BinaryExprNode.binaryOp.LESS_EQUAL)) {
            if (!node.lNode.type.match(BaseType.BuiltinType.INT))
                throw new Error(node.pos, "invalid operator for builtinType \"" + node.lNode.type + "\"");
        }
    }

    //assignment: a = b -> a.builtinType == b.builtinType, a is leftvalue
    public static void match(AssignExprNode node) {
        if (!node.lNode.isLeftValue())
            throw new Error(node.pos, node.lNode + "should be a leftvalue");
        if (!node.lNode.type.match(node.rNode.type))
            throw new Error(node.pos, "builtinType mismatched between: \"" + node.lNode.type.toString() + "\" and \"" + node.rNode.type.toString() + "\"");
    }

    //variable declaration: int a = 1 -> a.builtinType == 1.builtinType
    public static void match(VarDefSingleNode node) {
        if (!node.varRegistry.type.match(node.initNode.type))
            throw new Error(node.pos, "builtinType mismatched between: \"" + node.varRegistry.type.toString() + "\" and \"" + node.initNode.type.toString() + "\"");
    }

    // for statement: condition -> bool
    public static void match(ForStmtNode node) {
        if (!node.conditionNode.type.match(BaseType.BuiltinType.BOOL))
            throw new Error(node.pos, "builtinType mismatched between: \"bool\" and \"" + node.conditionNode.type.toString() + "\"");
    }

    // while statement: condition -> bool
    public static void match(WhileStmtNode node) {
        if (!node.conditionNode.type.match(BaseType.BuiltinType.BOOL))
            throw new Error(node.pos, "builtinType mismatched between: \"bool\" and \"" + node.conditionNode.type.toString() + "\"");
    }

    // if statement: condition -> bool
    public static void match(IfStmtNode node) {
        if (!node.conditionNode.type.match(BaseType.BuiltinType.BOOL))
            throw new Error(node.pos, "builtinType mismatched between: \"bool\" and \"" + node.conditionNode.type.toString() + "\"");
    }

    // new: int[][] a = new int[][];
    // each dimension size -> int
    public static void match(NewExprNode node) {
        for (ExprBaseNode eachDimSizeNode : node.eachDimSizeNode) {
            if (!eachDimSizeNode.type.match(BaseType.BuiltinType.INT))
                throw new Error(node.pos, "builtinType mismatched between: \"int\" and \"" + eachDimSizeNode.type.toString() + "\"");
        }
    }

}
