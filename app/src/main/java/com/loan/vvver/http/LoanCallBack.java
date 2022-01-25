package com.loan.vvver.http;

import com.example.httplibrary.callback.BaseCallBack;
import com.example.httplibrary.utils.JsonUtils;
import com.google.gson.JsonElement;

public abstract class LoanCallBack<T> extends BaseCallBack<T> {
    LoanResponse loanResponse;

    @Override
    protected T onConvert(String result) {
        T t = null;
        loanResponse = JsonUtils.jsonToClass(result, LoanResponse.class);
        int code = loanResponse.getCode();
        String msg = loanResponse.getMsg();
        JsonElement data = loanResponse.getData();
        if (code == 0) {
            t = convert(data);
        } else {
            error(msg, code);
        }
        return t;
    }

    @Override
    public boolean isCodeSuccess() {
        return loanResponse.getCode() == 0;
    }
}
