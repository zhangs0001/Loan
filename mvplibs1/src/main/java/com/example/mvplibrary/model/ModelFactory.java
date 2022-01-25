package com.example.mvplibrary.model;


import java.util.HashMap;
import java.util.Map;

//创建Model的工厂类
public class ModelFactory {
    private static Map<Class, BaseModel> models = new HashMap<>();
    public static <T extends BaseModel> T createModel(Class<T> mClass){
        T t = null;
        if (models != null){
            if (mClass != null){
                t = (T) models.get(mClass);
            }
        }else {
            try {
                t = mClass.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            models.put(mClass,t);
        }
        return t;
    }
}
