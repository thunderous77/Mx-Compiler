package chaos.compiler.backend.asm;

import chaos.utility.Pair;

public class AsmTranslator {

    public static String translateWidth(int width) {
        switch (width) {
            case 1 : return "b";
            case 2 : return "h";
            case 4 : case 8 : return "w";
        }
        return "";
    }

    public static String translateArithmOp(String irOp) {
        switch (irOp) {
            case "sdiv": return "div";
            case "srem": return "rem";
            case "shl": return "sll";
            case "ashr": return "sra";
            default: return irOp;
        }
    }

    // only have blt, bge
    // switch <= to >=, > to <
    public static Pair<String, Boolean> translateCmpOp(String irOp) {
        switch (irOp) {
            case "sgt": return new Pair<>("lt", true);
            case "sge": return new Pair<>("ge", false);
            case "slt": return new Pair<>("lt", false);
            case "sle": return new Pair<>("ge", true);
            default: return new Pair<>(irOp, false);
        }
    }

}
