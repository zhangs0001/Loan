package com.example.mvplibrary.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.mvplibrary.model.BaseModel;
import com.example.mvplibrary.presenter.BasePresenterIml;
import com.example.mvplibrary.view.BaseView;

public abstract class BaseMvpFragment<V extends BaseView,M extends BaseModel,P extends BasePresenterIml<V,M>>
extends BaseFragment implements BaseView{

    public P mPresenter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null){
            mPresenter.bindView((V) this);
        }
        initDataMvp();
    }
//    初始化MVP数据方法
    protected abstract void initDataMvp();

    protected abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.onUnBindView();
        }
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void onSucessView() {

    }

    @Override
    public void showErrorView() {

    }
}
