package chaos.compiler.frontend.utility.registry;

import chaos.compiler.middleend.llvmir.Value;
import chaos.utility.Position;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class BaseRegistry {

    public final String name;
    public final Position position;
    public Value value;

    BaseRegistry(String name) {
        this.name = name;
        this.position = null;
    }

    BaseRegistry(String name, ParserRuleContext context) {
        this.name = name;
        this.position = new Position(context);
    }
}