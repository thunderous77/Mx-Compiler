package chaos.compiler.frontend.utility.scope;

import chaos.compiler.frontend.utility.registry.*;
import chaos.compiler.frontend.utility.type.BaseType;
import chaos.compiler.frontend.utility.type.FuncType;
import chaos.utility.Error;

public class NormalScope extends BaseScope {

    @Override
    public ClassRegistry queryClass(String name) {
        return null;
    }

    @Override
    public FuncRegistry queryFunc(String name) {
        return null;
    }

    @Override
    public VarRegistry queryVar(String name) {
        return varMap.get(name);
    }

    @Override
    public void register(BaseRegistry registry) {
        if (varMap.containsKey(registry.name))
            throw new Error(registry.position, registry.name + " is redefined");
        varMap.put(registry.name, (VarRegistry) registry);
    }

}