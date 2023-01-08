package chaos.compiler.backend.asm;

public class AsmTranslator {

    public static String widthTranslate(int width) {
        switch (width) {
            case 1 : return "b";
            case 2 : return "h";
            case 4 : case 8 : return "w";
        }
        return "";
    }

}
