package chaos.compiler.frontend.utility.registry;

import java.util.ArrayList;

import chaos.compiler.frontend.parser.MxParser;
import chaos.compiler.frontend.utility.type.BaseType;
import chaos.compiler.frontend.utility.type.VarType;

public class VarRegistry extends BaseRegistry {

    public VarType type;
    public ArrayList<VarRegistry> dimenLength;

    public VarRegistry(String name,BaseType.BuiltinType type) {
        super(name);
        this.type = new VarType(type);
        dimenLength = new ArrayList<>();
    }

    public VarRegistry(String name, MxParser.VarDefSingleContext ctx) {
        super(name, ctx);
        dimenLength = new ArrayList<>();
    }

    public VarRegistry(String name, MxParser.VarDefTypeContext ctx) {
        super(name, ctx);
        this.type = new VarType(ctx);
        dimenLength = new ArrayList<>();
    }

    public String toString() {
        StringBuilder ret = new StringBuilder("[VarRegistry] ");
        ret.append(type + "|" + name);
        return ret.toString();
    }

}