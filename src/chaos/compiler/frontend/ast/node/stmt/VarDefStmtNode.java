package chaos.compiler.frontend.ast.node.stmt;

import java.util.ArrayList;

import chaos.utility.Position;
import chaos.compiler.frontend.ast.node.ASTVisitor;

public class VarDefStmtNode extends StmtBaseNode {

    public ArrayList<VarDefSingleNode> varDefSingleNodes;

    public VarDefStmtNode(Position pos) {
        super(pos);
        varDefSingleNodes = new ArrayList<>();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}