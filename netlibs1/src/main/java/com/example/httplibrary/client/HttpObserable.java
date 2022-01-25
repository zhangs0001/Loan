package com.example.httplibrary.client;


import com.example.httplibrary.callback.BaseObserver;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import exception.ExceptionEngine;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 项目名：2007
 * 包名：  com.http.httplibrary.client
 * 文件名：HttpObserable
 * 创建者：liangxq
 * 创建时间：2020/11/6  15:16
 * 描述：TODO
 */
public class HttpObserable {
    LifecycleProvider lifecycleProvider;
    //绑定Activity具体的生命周的
    ActivityEvent activityEvent;
    //绑定Fragment的具体的生命周期的
    FragmentEvent fragmentEvent;
    Observable observable;
    BaseObserver baseObserver;

    public HttpObserable(Buidler buidler) {
        this.lifecycleProvider=buidler.lifecycleProvider;
        this.activityEvent=buidler.activityEvent;
        this.fragmentEvent=buidler.fragmentEvent;
        this.observable=buidler.observable;
        this.baseObserver=buidler.baseObserver;
    }

    //错误信息的分类回调
    private Observable onErrorResumeNext() {
        return bindlifecycle().onErrorResumeNext(new Function<Throwable, ObservableSource>() {
            @Override
            public ObservableSource apply(Throwable throwable) throws Exception {
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        });
    }

    //设置线程切换
    public void observer(){
        onErrorResumeNext().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(baseObserver);
    }



    private Observable bindlifecycle() {
        if (lifecycleProvider != null) {
            if (activityEvent != null || fragmentEvent != null) {
                //两个同时存在,以 activity 为准
                if (activityEvent != null && fragmentEvent != null) {
                    return observable.compose(lifecycleProvider.bindUntilEvent(activityEvent));
                }
                if (activityEvent != null) {
                    return observable.compose(lifecycleProvider.bindUntilEvent(activityEvent));
                }
                if (fragmentEvent != null) {
                    return observable.compose(lifecycleProvider.bindUntilEvent(fragmentEvent));
                }
            } else {
                return observable.compose(lifecycleProvider.bindToLifecycle());
            }
        }
        return observable;

    }


    public static final class Buidler{
        LifecycleProvider lifecycleProvider;
        //绑定Activity具体的生命周的
        ActivityEvent activityEvent;
        //绑定Fragment的具体的生命周期的
        FragmentEvent fragmentEvent;
        Observable observable;
        BaseObserver baseObserver;

        public Buidler setLifecycleProvider(LifecycleProvider lifecycleProvider) {
            this.lifecycleProvider = lifecycleProvider;
            return this;
        }

        public Buidler setActivityEvent(ActivityEvent activityEvent) {
            this.activityEvent = activityEvent;
            return this;
        }

        public Buidler setFragmentEvent(FragmentEvent fragmentEvent) {
            this.fragmentEvent = fragmentEvent;
            return this;
        }

        public Buidler setBaseObserver(BaseObserver baseObserver) {
            this.baseObserver = baseObserver;
            return this;
        }

        public Buidler(Observable observable) {
            this.observable = observable;
        }

        public HttpObserable build(){
            return new HttpObserable(this);
        }
    }
}
