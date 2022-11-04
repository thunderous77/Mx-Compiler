package chaos.utility;

public class Error extends RuntimeException {
    Position pos;
    String msg;

    public Error(Position poss, String msgg) {
        this.pos = poss;
        this.msg = msgg;
    }

    public void show_error() {
        System.out.println("在行" + pos.get_row() + ",列" + pos.get_col() + ": " + msg);
    }
}
