package chaos.compiler.frontend.utility;

import chaos.compiler.frontend.utility.registry.FuncRegistry;
import chaos.compiler.frontend.utility.scope.ClassScope;
import chaos.compiler.frontend.utility.type.BaseType;

public class ArrayBuiltinFunc {

    public static ClassScope scope = new ClassScope();

    static {
        scope.register(new FuncRegistry("size", BaseType.BuiltinType.INT));
    }
}
