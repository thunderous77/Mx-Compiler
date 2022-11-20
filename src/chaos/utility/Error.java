package chaos.utility;

public class Error extends RuntimeException {
    String msg;

    // position is unable to get
    public Error(String msgg) {
        this.msg = msgg;
    }

    public Error(Position poss, String msgg) {
        this.msg = "在行" + poss.get_row() + ",列" + poss.get_col() + ": " + msgg;
    }

    public void show_error() {
        System.err.println(msg);
    }
}
