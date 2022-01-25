package com.example.mvplibrary.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.mvplibrary.model.BaseModel;
import com.example.mvplibrary.presenter.BasePresenterIml;
import com.example.mvplibrary.view.BaseView;

public abstract class BaseMvpActivity <V extends BaseView,M extends BaseModel,P extends BasePresenterIml<V,M>>
        extends BaseActivity implements BaseView{

//    P层对象
    public P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null){
            mPresenter.bindView((V) this);
        }
        initMvpData();
    }

    protected abstract void initMvpData();

    //    创建P层对象
    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
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
