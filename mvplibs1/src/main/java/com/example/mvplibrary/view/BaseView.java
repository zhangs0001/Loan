package com.example.mvplibrary.view;

public interface BaseView {
//    网络请求中
    void showLoadingView();
//    网络请求成功
    void onSucessView();
//    网络请求失败
    void showErrorView();
}
