package com.example.mvplibrary.presenter;

public interface BasePresenter<V> {
//    绑定View
    void bindView(V v);
//    解绑View
    void onUnBindView();
}
