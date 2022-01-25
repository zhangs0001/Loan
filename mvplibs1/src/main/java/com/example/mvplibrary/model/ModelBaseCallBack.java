package com.example.mvplibrary.model;

public interface ModelBaseCallBack<T>{
//    成功
    void sucess(T data);
//    失败
    void onError(String msg,int code);
}
