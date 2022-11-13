package chaos.compiler.middleend.llvmir;

public class Use {

    public User user;
    public Value value;

    private Use(User user, Value value) {
        this.user = user;
        this.value = value;
    }

    public static Use getUseLink(User user, Value value) {
        Use ret = new Use(user, value);
        user.operandList.add(ret);
        value.useList.add(ret);
        return ret;
    }

}
