package com.loan.vvver.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.utils.JsonUtils;
import com.example.mvplibrary.activity.BaseActivity;
import com.google.gson.JsonElement;
import com.loan.vvver.R;
import com.loan.vvver.adapter.MyLoanListAdapter;
import com.loan.vvver.config.ApiConstant;
import com.loan.vvver.config.AppConstant;
import com.loan.vvver.data.bean.MyLoanListBean;
import com.loan.vvver.data.body.HomeBody;
import com.loan.vvver.http.LoanCallBack;
import com.loan.vvver.utils.LiveDataBus;
import com.loan.vvver.utils.SPUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyLoanActivity extends BaseActivity {

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.recy_MyLoon)
    RecyclerView recyMyLoon;

    @Override
    protected int createLayoutId() {
        return R.layout.activity_my_loan;
    }

    @Override
    protected void initViewAndData() {
        title.setVisibility(View.VISIBLE);
        titleLeft.setVisibility(View.VISIBLE);
        title.setText(getString(R.string.a20));
        titleLeft.setBackgroundResource(R.drawable.ic_back);

        initMyLoan();
    }

    private void initMyLoan() {
        startLoading(MyLoanActivity.this);
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.MyLoan)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new HomeBody(String.valueOf(SPUtils.get(MyLoanActivity.this, AppConstant.islog, "")),
                        parseStrToMd5L32(SPUtils.get(MyLoanActivity.this, AppConstant.islog, "") + AppConstant.endconfig))))
                .build().enqueue(new LoanCallBack<List<MyLoanListBean>>() {
            @Override
            public void error(String error, int code) {
                closeLoding();
                Log.e(TAG, "error: " + error + code);
            }

            @Override
            public List<MyLoanListBean> convert(JsonElement result) {
                return JsonUtils.jsonToClassList(result, MyLoanListBean.class);
            }

            @Override
            public void onSucess(List<MyLoanListBean> productListBean) {
                closeLoding();
                Log.e(TAG, "onSucess: 贷款记录= " + productListBean.toString());
                recyMyLoon.setLayoutManager(new LinearLayoutManager(MyLoanActivity.this));
                MyLoanListAdapter myLoanListAdapter = new MyLoanListAdapter(productListBean, MyLoanActivity.this, R.layout.myloan_item);
                recyMyLoon.setAdapter(myLoanListAdapter);
            }
        });
    }

    @Override
    protected void initClick() {
        fastClickChecked(titleLeft, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //刷新主页状态接口
        LiveDataBus.get().with("refreshhomepage").postValue(10);
    }
}