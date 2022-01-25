package com.example.mvplibrary.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 项目名：2007
 * 包名：  com.example.myapplication.adapter
 * 文件名：BaseAdapter
 * 创建者：liangxq
 * 创建时间：2020/11/11  9:38
 * 描述：TODO
 */
public abstract class BaseAdapter<DATA> extends RecyclerView.Adapter<BaseViewHolder>{
    protected List<DATA> datas;
    protected Context context;
    protected LayoutInflater inflater;

    private int layoutId;
    public BaseAdapter(List<DATA> datas, Context context , int layoutId
    ) {
        this.datas = datas;
        this.context = context;
        inflater= LayoutInflater.from(context);
        this.layoutId=layoutId;
    }
    //追加数据
    public void setData(List<DATA> datas){
        this.datas=datas;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new BaseViewHolder(inflater.inflate(layoutId,viewGroup,false),context);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, final int position) {
        bindData(viewHolder,position);
        viewHolder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("liangxq", "onClick: " );
                if (adapterOnItemClickListener != null){
                    adapterOnItemClickListener.OnItemClick(position);
                }

            }
        });
    }

    public abstract void bindData(BaseViewHolder baseViewHolder,int position);
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public interface AdapterOnItemClickListener {
        void  OnItemClick(int postion);
    }
    private AdapterOnItemClickListener adapterOnItemClickListener;

    public void setAdapterOnItemClickListener(AdapterOnItemClickListener adapterOnItemClickListener) {
        this.adapterOnItemClickListener = adapterOnItemClickListener;
    }
}
