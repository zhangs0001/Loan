package com.loan.vvver.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.utils.JsonUtils;
import com.example.mvplibrary.activity.BaseActivity;
import com.google.gson.JsonElement;
import com.loan.vvver.R;
import com.loan.vvver.config.ApiConstant;
import com.loan.vvver.config.AppConstant;
import com.loan.vvver.data.bean.OwnInFoBean;
import com.loan.vvver.data.bean.ServiceBean;
import com.loan.vvver.data.body.HomeBody;
import com.loan.vvver.http.LoanCallBack;
import com.loan.vvver.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerServiceActivity extends BaseActivity {


    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.tv_PhoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.btn_DialNumber)
    Button btnDialNumber;

    @Override
    protected int createLayoutId() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void initViewAndData() {
        title.setVisibility(View.VISIBLE);
        titleLeft.setVisibility(View.VISIBLE);
        title.setText(getString(R.string.a15));
        titleLeft.setBackgroundResource(R.drawable.ic_back);

        initServicePhoneNumber();
    }

    private void initServicePhoneNumber() {
        startLoading(CustomerServiceActivity.this);
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.CustomerService)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new HomeBody(String.valueOf(SPUtils.get(CustomerServiceActivity.this, AppConstant.islog, "")), parseStrToMd5L32(SPUtils.get(CustomerServiceActivity.this, AppConstant.islog, "") + AppConstant.endconfig))))
                .build().enqueue(new LoanCallBack<ServiceBean>() {
            @Override
            public void error(String error, int code) {
                closeLoding();
                Log.e(TAG, "error: " + error + code);
            }

            @Override
            public ServiceBean convert(JsonElement result) {
                return JsonUtils.jsonToClass(result, ServiceBean.class);
            }

            @Override
            public void onSucess(ServiceBean serviceBean) {
                closeLoding();
                Log.e(TAG, "onSucess: 客服 = " + serviceBean.toString());
                tvPhoneNumber.setText(serviceBean.getServicePhone());

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

        fastClickChecked(btnDialNumber, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = tvPhoneNumber.getText().toString();
                if (!tel.equals("")) {
                    try {
                        startCallTel(tel);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: " + e.getMessage());
                    }
                }
            }
        });
    }

    private void startCallTel(String tel) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}