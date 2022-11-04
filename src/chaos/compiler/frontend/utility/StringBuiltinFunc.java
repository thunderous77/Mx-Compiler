package chaos.compiler.frontend.utility;


import chaos.compiler.frontend.utility.registry.FuncRegistry;
import chaos.compiler.frontend.utility.registry.VarRegistry;
import chaos.compiler.frontend.utility.scope.ClassScope;
import chaos.compiler.frontend.utility.type.BaseType;

public class StringBuiltinFunc {
    public static ClassScope scope = new ClassScope();

    static {
        scope.register(new FuncRegistry("length", BaseType.BuiltinType.INT));
        scope.register(new FuncRegistry("substring", BaseType.BuiltinType.STRING,
                new VarRegistry("left", BaseType.BuiltinType.INT), new VarRegistry("right", BaseType.BuiltinType.INT)));
        scope.register(new FuncRegistry("parseInt", BaseType.BuiltinType.INT));
        scope.register(new FuncRegistry("ord", BaseType.BuiltinType.INT, new VarRegistry("pos", BaseType.BuiltinType.INT)));
    }
}
