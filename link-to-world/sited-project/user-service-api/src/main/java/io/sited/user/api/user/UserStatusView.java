package io.sited.user.api.user;

/**
 * @author chi
 */
public enum UserStatusView {
    ACTIVE("正常"), INACTIVE("冻结"), AUDITING("审核中"), DELETE("删除");

    public String display;

    UserStatusView(String display) {
        this.display = display;
    }
}
