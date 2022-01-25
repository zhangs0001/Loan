package com.example.mvplibrary.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 项目名：2007
 * 包名：  com.example.myapplication.adapter
 * 文件名：ImageUtils
 * 创建者：liangxq
 * 创建时间：2020/11/11  10:25
 * 描述：TODO
 */
public class ImageUtils {
    public static void loadImage(Context context, String url, ImageView imageView){
        if (context != null&&url!=null&&imageView!=null){
            Glide.with(context).load(url).into(imageView);
        }

    }
}
