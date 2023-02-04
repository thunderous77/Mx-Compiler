package chaos.compiler.backend.asm.inst;

import chaos.compiler.backend.asm.hierarchy.AsmBlock;
import chaos.compiler.backend.asm.operand.AsmRegister;

public class AsmLaInst extends AsmBaseInst{

    private final String symbol;

    public AsmLaInst(AsmRegister rd, String symbol, AsmBlock parentBlock) {
        super(rd, null, null, null, parentBlock);
        this.symbol = symbol;
    }

    @Override
    public AsmBaseInst copy() {
        return new AsmLaInst(rd, symbol, null);
    }

    @Override
    public String format() {
        // la rd, symbol
        return String.format("%s\t%s,%s", "la", rd, symbol);
    }

}
