package com.example.mvplibrary.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * 项目名：2007
 * 包名：  com.example.mvplibrary.base.adapter
 * 文件名：MultipleAdapter
 * 创建者：liangxq
 * 创建时间：2020/11/11  14:47
 * 描述：TODO
 */
public abstract class MultipleAdapter<DATA> extends BaseAdapter<DATA>{
    private CommonType<DATA> commonType;
    protected int type;
    public MultipleAdapter(List<DATA> datas, Context context, int layoutId, CommonType<DATA> commonType) {
        super(datas, context, layoutId);
        if(layoutId==0){
            this.commonType=commonType;
        }
    }

    @Override
    public int getItemViewType(int position) {
        type=commonType.getType(position, datas.get(position));
        return type;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layoutId = commonType.getTypeLayoutId(viewType);
        return new BaseViewHolder(inflater.inflate(layoutId,viewGroup,false),context);
    }
}
