package chaos.compiler.middleend.llvmir;

public class IRUse {

    public IRUser user;
    public IRValue value;

    public IRUse(IRUser user, IRValue value) {
        this.user = user;
        this.value = value;
    }

    public static void getUseLink(IRUser user, IRValue value) {
        IRUse newUse = new IRUse(user, value);
        user.operandList.add(newUse);
        value.useList.add(newUse);
    }

    public void removeUse() {
        value.useList.remove(this);
        user.operandList.remove(this);
    }

}
