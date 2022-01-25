package com.loan.vvver.data.bean;

public class LoginBean {

    /**
     * userId : 1
     * isRegister : 1
     */

    private String userId;
    private int isRegister;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(int isRegister) {
        this.isRegister = isRegister;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "userId='" + userId + '\'' +
                ", isRegister=" + isRegister +
                '}';
    }
}
