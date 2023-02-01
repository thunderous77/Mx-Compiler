package chaos.compiler.backend.asm.operand;

import java.rmi.registry.Registry;

public class AsmOffsetMemory extends AsmImmediate{

    public AsmRegister pointer;

    public AsmOffsetMemory(AsmRegister pointer, int offset) {
        super(offset);
        this.pointer = pointer;
    }

}
