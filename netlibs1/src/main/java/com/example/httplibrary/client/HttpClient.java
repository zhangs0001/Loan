package com.example.httplibrary.client;

import android.text.TextUtils;
import android.util.Log;
import com.example.httplibrary.callback.BaseCallBack;
import com.example.httplibrary.config.GlobalConfig;
import com.example.httplibrary.manager.RetrofitManager;
import com.example.httplibrary.service.HttpServer;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;

/**
 * 项目名：2007
 * 包名：  com.http.httplibrary.client
 * 文件名：HttpClient
 * 创建者：liangxq
 * 创建时间：2020/11/6  14:05
 * 描述：TODO
 */
public class HttpClient {
    HttpMethod method;
    Map<String, Object> paramser;
    Map<String, Object> headres;
    LifecycleProvider lifecycleProvider;
    ActivityEvent activityEvent;
    FragmentEvent fragmentEvent;
    String baseUrl;
    String apiUrl;
    boolean isJson;
    String jsonbody;
    long time;
    TimeUnit timeUnit;
    BaseCallBack baseCallBack;
    String tag;

    public HttpClient() {

    }

    public HttpClient(Buidler builder) {
        this.method = builder.method;
        this.activityEvent = builder.activityEvent;
        this.fragmentEvent = builder.fragmentEvent;
        this.paramser = builder.paramser;
        this.headres = builder.headres;
        this.lifecycleProvider = builder.lifecycleProvider;
        this.baseUrl = builder.baseUrl;
        this.apiUrl = builder.apiUrl;
        this.isJson = builder.isJson;
        this.jsonbody = builder.jsonbody;
        this.time = builder.time;
        this.timeUnit = builder.timeUnit;
        this.tag = builder.tag;
    }

    public void enqueue(BaseCallBack baseCallBack) {
        this.baseCallBack = baseCallBack;
        if (baseCallBack == null) {
            new RuntimeException("http callback is not null");
        }
        doRequest();
    }

    private void doRequest() {
        if (tag == null) {
            tag = System.currentTimeMillis() + "";
        }
        baseCallBack.setTag(tag);
        //添加公共请求头信息
        addHeaders();
        //添加公共请求参数
        addParams();
        Observable observable = createObservable();
        HttpObserable httpObserable = new HttpObserable.Buidler(observable)
                .setActivityEvent(activityEvent)
                .setFragmentEvent(fragmentEvent)
                .setLifecycleProvider(lifecycleProvider)
                .setBaseObserver(baseCallBack).build();
        httpObserable.observer();
    }
    //
    private Observable createObservable() {
        Observable observable = null;
        boolean hasBodyString = !TextUtils.isEmpty(jsonbody);
        RequestBody requestBody = null;
        if (hasBodyString) {
            Log.e("liangxq", "createObservable: " + hasBodyString);
            String mediaType = isJson ? "application/json; charset=utf-8" : "text/plain;charset=utf-8";
            requestBody = RequestBody.create(okhttp3.MediaType.parse(mediaType), jsonbody);
        }
        if (method == null) {
            method = HttpMethod.POST;
        }
        this.baseUrl = GlobalConfig.getInstance().getBaseUrl()==null?this.baseUrl:GlobalConfig.getInstance().getBaseUrl();

        HttpServer server = RetrofitManager.getInstance().createServer(HttpServer.class,baseUrl);

        if(apiUrl==null|| apiUrl.equals(baseUrl)){
            new RuntimeException("apiUrl is null or equals baseUrl");
        }
        switch (method) {
            case GET:
                observable=server.get(apiUrl,paramser,headres);
                break;
            case POST:
                if (isJson) {
                    observable= server.postjson(apiUrl, requestBody, headres);
                } else {
                    observable= server.post(apiUrl, paramser, headres);
                }
                break;
            case DELETE:
                observable = server.delete(apiUrl, paramser, headres);
                break;
            case PUT:
                observable = server.put(apiUrl, paramser, headres);
                break;
        }

        return observable;
    }

    private void addParams() {
        if (paramser == null) {
            paramser = new HashMap<>();
        }
        //添加公共请求参数
        if (GlobalConfig.getInstance().getBaseparams() != null) {
            paramser.putAll(GlobalConfig.getInstance().getBaseparams());
        }
    }

    private void addHeaders() {
        if (headres == null) {
            headres = new HashMap<>();
        }
        //添加公共请求头信息
        if (GlobalConfig.getInstance().getBaseHeades() != null ) {
            headres.putAll(GlobalConfig.getInstance().getBaseHeades());
        }

    }

    public static class Buidler {
        //请求方式
        HttpMethod method;
        //请求参数
        Map<String, Object> paramser;
        //请求头信息
        Map<String, Object> headres;
        //Rxjava绑定生命周期
        LifecycleProvider lifecycleProvider;
        //绑定Activity具体的生命周的
        ActivityEvent activityEvent;
        //绑定Fragment的具体的生命周期的
        FragmentEvent fragmentEvent;
        String baseUrl;
        //拼接的url
        String apiUrl;
        //是否是json上传表示
        boolean isJson;
        //json字符串
        String jsonbody;
        //超时时间
        long time;
        //时间单位
        TimeUnit timeUnit;
        //回调接口
        BaseCallBack baseCallBack;
        //订阅关系的标签
        String tag;
        public Buidler get() {
            this.method = HttpMethod.GET;
            return this;
        }

        public Buidler post() {
            this.method = HttpMethod.POST;
            return this;
        }

        public Buidler put() {
            this.method = HttpMethod.PUT;
            return this;
        }

        public Buidler delete() {
            this.method = HttpMethod.DELETE;
            return this;
        }

        public Buidler setParamser(Map<String, Object> paramser) {
            this.paramser = paramser;
            return this;
        }

        public Buidler setHeadres(Map<String, Object> headres) {
            this.headres = headres;
            return this;
        }

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

        public Buidler setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Buidler setApiUrl(String apiUrl) {
            this.apiUrl = apiUrl;
            return this;
        }

        public Buidler setJson(boolean json) {
            isJson = json;
            return this;
        }

        public Buidler setJsonbody(String jsonbody) {
            this.jsonbody = jsonbody;
            return this;
        }

        public Buidler setTime(long time) {
            this.time = time;
            return this;
        }

        public Buidler setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }


        public Buidler setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public HttpClient build() {
            return new HttpClient(this);
        }
    }
}
