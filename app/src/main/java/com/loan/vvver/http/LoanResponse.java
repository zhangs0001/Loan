package com.loan.vvver.http;

import com.google.gson.JsonElement;

public class LoanResponse {
    private String msg;
    private int code;
    private JsonElement data;

    public LoanResponse() {
    }

    public LoanResponse(String msg, int code, JsonElement data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoanResponse{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
