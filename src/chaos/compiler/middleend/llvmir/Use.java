package chaos.compiler.middleend.llvmir;

public class Use {

    public User user;
    public Value value;

    private Use(User user, Value value) {
        this.user = user;
        this.value = value;
    }

    public static void getUseLink(User user, Value value) {
        Use newUse = new Use(user, value);
        user.operandList.add(newUse);
        value.useList.add(newUse);
    }

    public void removeUse() {
        value.useList.remove(this);
        user.operandList.remove(this);
    }

}
