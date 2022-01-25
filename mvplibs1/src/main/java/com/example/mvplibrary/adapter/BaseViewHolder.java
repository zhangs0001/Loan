package com.example.mvplibrary.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 项目名：2007
 * 包名：  com.example.myapplication.adapter
 * 文件名：BaseViewHolder
 * 创建者：liangxq
 * 创建时间：2020/11/11  10:01
 * 描述：TODO
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private View rootView;
    //存储View的集合
    private SparseArray<View> mViews = new SparseArray<>();
    private Context context;
    public BaseViewHolder(View itemView, Context context) {
        super(itemView);
        rootView = itemView;
        this.context=context;
    }
    public View getRootView() {
        return rootView;
    }
    //找一个控件
    public <T extends View> T getView(int viewId) {
        T view = null;
        if (mViews.get(viewId) != null) {
            view = (T) mViews.get(viewId);
        } else {
            view = rootView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    public BaseViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }
    public BaseViewHolder setText(int viewId, final String text, final AdapterOnItemViewClickListener<String> adapterOnItemClickListener) {
        TextView textView = getView(viewId);
        textView.setText(text);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterOnItemClickListener.OnItemClick(text);
            }
        });
        return this;
    }

    public BaseViewHolder setImage(int viewId, String url){
        ImageView imageView=getView(viewId);
        ImageUtils.loadImage(context,url,imageView);
        return this;
    }


    public interface AdapterOnItemViewClickListener<T> {
        void OnItemClick(T t);
    }

}
