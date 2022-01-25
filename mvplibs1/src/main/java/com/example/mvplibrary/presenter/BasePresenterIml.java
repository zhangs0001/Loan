package com.example.mvplibrary.presenter;

import com.example.mvplibrary.model.BaseModel;
import com.example.mvplibrary.view.BaseView;
import com.trello.rxlifecycle2.LifecycleProvider;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BasePresenterIml<V extends BaseView,M extends BaseModel> implements BasePresenter<V> {

    private WeakReference<V> vWeakReference;
    private WeakReference<M> mWeakReference;

//    视图
    private V mView;
//    Model对象
    private M mModel;

    @Override
    public void bindView(V v) {
        vWeakReference = new WeakReference<>(v);
        if (v != null && vWeakReference != null){
            mView = vWeakReference.get();
        }
        if (createModel() != null){
            mWeakReference = new WeakReference<>(createModel());
            mModel = mWeakReference.get();
        }
    }

//    根据泛型动态创建Model对象
    private M createModel() {
        M mModel = null;
        Class<? extends BasePresenterIml> aClass = this.getClass();
        ParameterizedType genericSuperclass = (ParameterizedType) aClass.getGenericSuperclass();
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        Class actualTypeArgument = (Class) actualTypeArguments[1];
        if (actualTypeArguments != null){
            try {
                mModel = (M)actualTypeArgument.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return mModel;
    }

    public LifecycleProvider getLifecycleProvider(){
        if(mView!=null){
            return (LifecycleProvider) mView;
        }
        return null;
    }

    @Override
    public void onUnBindView() {
        if (vWeakReference != null){
            vWeakReference.clear();
        }
        if (mWeakReference != null){
            mWeakReference.clear();
        }
        if (mView != null){
            mView = null;
        }
        if (mModel != null){
            mModel = null;
        }
    }

    public V getmView() {
        return mView;
    }

    public M getmModel() {
        return mModel;
    }
}
