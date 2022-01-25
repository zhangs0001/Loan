package com.example.httplibrary.callback;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import exception.ExceptionEngine;

/**
 * 项目名：2007
 * 包名：  com.http.httplibrary.callback
 * 文件名：BaseCallBack
 * 创建者：liangxq
 * 创建时间：2020/11/6  9:34
 * 描述：TODO
 */
public abstract  class BaseCallBack<T> extends BaseObserver {

    //解析成功的标志
    boolean callSuccess=true;

    @Override
    public void sucess(Object object) {
        String json = new Gson().toJson(object);
        T paser = paser(json);
        if(callSuccess&&isCodeSuccess()){
            onSucess(paser);
        }
    }
     T paser(String result){
         T data=null;
         try {
             data=onConvert(result);
             callSuccess=true;
         }catch (Exception e){
             callSuccess=false;
             error("解析错误", ExceptionEngine.ANALYTIC_SERVER_DATA_ERROR);
         }
         return data;
     }

    //将JsonElement转换为Response，并且通过子类的实现来获取data数据
    protected abstract T onConvert(String result);

    //数据返回状态成功
    public abstract boolean isCodeSuccess();

    //将我们需要的数据解析出来
     public abstract T convert(JsonElement result);

     //回调数据
     public abstract void onSucess(T t);
}
