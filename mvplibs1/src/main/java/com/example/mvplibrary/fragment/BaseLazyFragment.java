package com.example.mvplibrary.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mvplibrary.model.BaseModel;
import com.example.mvplibrary.presenter.BasePresenterIml;
import com.example.mvplibrary.view.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseLazyFragment<V extends BaseView,
        M extends BaseModel,
        P extends BasePresenterIml<V,M>>
extends BaseFragment implements BaseView{
    boolean mIsPrepare = false;        //初始化视图
    boolean mIsVisible = false;        //不可见
    boolean mIsFirstLoad = true;    //第一次加载
    private View rootView;
    private Unbinder unbinder;
    public P mPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(creeateLayoutId(),container,false);
            unbinder = ButterKnife.bind(this,rootView);
            mIsPrepare = true;
        }
        return rootView;
    }

    protected abstract int creeateLayoutId();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
        if (mPresenter != null){
            mPresenter.bindView((V) this);
        }
        initLazyMvpData();
    }

    protected abstract P createPresenter();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            mIsVisible = true;
        }else {
            mIsVisible = false;
        }
        initLazyMvpData();
    }

    public void initLazyMvpData(){
        if (mIsVisible && mIsPrepare && mIsFirstLoad){
            lazydata();
        }
        mIsFirstLoad = false;
    }

    protected abstract void lazydata();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null){
            mPresenter.onUnBindView();
        }
        if (unbinder != null){
            unbinder.unbind();
        }
    }
}
