package com.loan.vvver.data.bean;

public class IsCompleteUserInFoBean {

    /**
     * code : -1
     * msg : error
     */

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "IsCompleteUserInFoBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
