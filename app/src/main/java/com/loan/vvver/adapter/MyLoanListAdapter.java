package com.loan.vvver.adapter;

import android.content.Context;

import com.example.mvplibrary.adapter.BaseAdapter;
import com.example.mvplibrary.adapter.BaseViewHolder;
import com.loan.vvver.R;
import com.loan.vvver.data.bean.MyLoanListBean;

import java.util.List;

public class MyLoanListAdapter extends BaseAdapter<MyLoanListBean> {
    public MyLoanListAdapter(List<MyLoanListBean> datas, Context context, int layoutId) {
        super(datas, context, layoutId);
    }

    @Override
    public void bindData(BaseViewHolder baseViewHolder, int position) {
        baseViewHolder.setText(R.id.money, "Loan amount：" + datas.get(position).getJk_money() + "INR");
        baseViewHolder.setText(R.id.date, "Repayment period：" + datas.get(position).getJk_date() + "day");
    }
}
