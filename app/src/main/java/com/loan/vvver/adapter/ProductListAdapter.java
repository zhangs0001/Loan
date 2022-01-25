package com.loan.vvver.adapter;

import android.content.Context;

import com.example.mvplibrary.adapter.BaseAdapter;
import com.example.mvplibrary.adapter.BaseViewHolder;
import com.loan.vvver.R;
import com.loan.vvver.data.bean.HomeBean;

import java.util.List;

public class ProductListAdapter extends BaseAdapter<HomeBean.ListBean> {
    public ProductListAdapter(List<HomeBean.ListBean> datas, Context context, int layoutId) {
        super(datas, context, layoutId);
    }

    @Override
    public void bindData(BaseViewHolder baseViewHolder, int position) {
        baseViewHolder.setText(R.id.tv_money, "Loan amount：" + datas.get(position).getMoney() + " INR");
        baseViewHolder.setText(R.id.tv_date, "Repayment period：" + datas.get(position).getDay() + " day");
    }
}
