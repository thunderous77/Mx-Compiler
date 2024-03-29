package chaos.compiler.backend.asm;

import chaos.compiler.backend.asm.hierarchy.AsmFunction;
import chaos.compiler.backend.asm.inst.AsmBaseInst;
import chaos.compiler.backend.asm.operand.AsmGlobalReg;

import java.util.ArrayList;

public class AsmFormatter {

    public final static String TAB = "\t";

    public static ArrayList<String> stringConstFormat(AsmGlobalReg globalReg) {
        /*
        	.type	.Lanon.strconst,@object # @anon.strconst
            .section	.rodata.str1.1,"aMS",@progbits,1
        .Lanon.strconst:
            .asciz	"Hello World!"
            .size	.Lanon.strconst, 13
         */
        ArrayList<String> ret = new ArrayList<>();
        ret.add(TAB + ".type" + TAB + globalReg + ",@object");
        ret.add(TAB + ".section" + TAB + ".rodata");
        ret.add(globalReg.identifier + ":");
        ret.add(TAB + ".asciz" + TAB + "\"" + globalReg.stringFormat() + "\"");
        ret.add(TAB + ".size" + TAB + globalReg.identifier + ", " + globalReg.stringConst.length());
        return ret;
    }

    public static ArrayList<String> globalVariableFormat(AsmGlobalReg globalReg) {
        /*
        	.type	glb,@object             # @glb
            .section	.sbss,"aw",@nobits
            .globl	glb
            .p2align	2
        glb:
            .word	0                       # 0x0
            .size	glb, 4

         */
        ArrayList<String> ret = new ArrayList<>();
        ret.add(TAB + ".type" + TAB + globalReg + ",@object");
        ret.add(TAB + ".section" + TAB + ".bss");
        ret.add(TAB + ".globl" + TAB + globalReg.identifier);
        ret.add(globalReg.identifier + ":");
        ret.add(TAB + ".word" + TAB + 0);
        ret.add(TAB + ".size" + TAB + globalReg.identifier + ", 4");
        return ret;
    }

    public static ArrayList<String> functionHeaderFormat(AsmFunction function) {
        ArrayList<String> ret = new ArrayList<>();
        ret.add(TAB + ".globl" + TAB + function);
        ret.add(TAB + ".p2align" + TAB + "1");
        ret.add(TAB + ".type" + TAB + function + "," + "@function");
        return ret;
    }

    public static String instFormat(AsmBaseInst inst) {
        return TAB + inst.format();
    }

}
