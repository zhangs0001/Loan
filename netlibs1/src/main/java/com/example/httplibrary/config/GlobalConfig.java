package com.example.httplibrary.config;

import android.content.Context;

import com.example.httplibrary.constans.Constans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import okhttp3.Interceptor;

public class GlobalConfig {
    //baseUrl
    private String baseUrl;
    //请求超时时间
    private long timeout;
    //时间单位
    private TimeUnit timeUnit;
    //公共请求参数
    private Map<String, Object> baseparams;
    //公共的请求头信息
    private Map<String, Object> baseHeades;
    //公共的拦截器
    private List<Interceptor> interceptors;
    //日志开关
    private boolean isShowLog;

    //上下问对象
    private Context context;

    private Handler handler;

    //存储各种appkey的集合
    private Map<String,String>appkeys;

    private GlobalConfig(){

    }

    static class GlobalConfigViewGolder{
        private static GlobalConfig instance=new GlobalConfig();
    }

    public static GlobalConfig getInstance(){
        return GlobalConfigViewGolder.instance;
    }

    public GlobalConfig setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public GlobalConfig setTimeout(long timeout) {
        this.timeout = timeout;
        return this;
    }

    public GlobalConfig setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }

    public GlobalConfig setBaseparams(Map<String, Object> baseparams) {
        this.baseparams = baseparams;
        return this;
    }

    public GlobalConfig setBaseHeades(Map<String, Object> baseHeades) {
        this.baseHeades = baseHeades;
        return this;
    }

    public GlobalConfig setInterceptors(Interceptor interceptor) {
        if(interceptors==null){
            interceptors=new ArrayList<>();
        }
        interceptors.add(interceptor);
        return this;
    }

    public GlobalConfig setShowLog(boolean showLog) {
        isShowLog = showLog;
        return this;
    }

    public GlobalConfig setContext(Context context) {
        this.context = context;
        return this;
    }

    public GlobalConfig setHandler(Handler handler) {
        this.handler = handler;
        return this;
    }

    public GlobalConfig setAppkeys(Map<String, String> appkeys) {
        this.appkeys = appkeys;
        return this;
    }


    public long getTimeout() {
        return timeout==0? Constans.TIME_OUT_DEFAULT:timeout;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit==null?Constans.TIME_UNIT_DEFAULT:timeUnit;
    }

    public Map<String, Object> getBaseparams() {
        if(baseparams==null){
            baseparams=new HashMap<>();
        }
        return baseparams;
    }

    public Map<String, Object> getBaseHeades() {
        if(baseHeades==null){
            baseHeades=new HashMap<>();
        }
        return baseHeades;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public boolean isShowLog() {
        return isShowLog;
    }

    public Context getContext() {
        return context;
    }

    public Handler getHandler() {
        return handler;
    }

    public Map<String, String> getAppkeys() {
        return appkeys;
    }

    public <T> T getValue(String key){
        if(appkeys!=null&& key!=null &&!key.equals("")){
            return (T) appkeys.get(key);
        }else{
            new RuntimeException("appkeys is null or key is null");
        }
        return null;
    }
}
