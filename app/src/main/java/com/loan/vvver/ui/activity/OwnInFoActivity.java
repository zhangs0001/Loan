package com.loan.vvver.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.utils.JsonUtils;
import com.example.mvplibrary.activity.BaseActivity;
import com.google.gson.JsonElement;
import com.loan.vvver.R;
import com.loan.vvver.config.ApiConstant;
import com.loan.vvver.config.AppConstant;
import com.loan.vvver.data.bean.IsCompleteUserInFoBean;
import com.loan.vvver.data.bean.OwnInFoBean;
import com.loan.vvver.data.body.HomeBody;
import com.loan.vvver.http.LoanCallBack;
import com.loan.vvver.utils.LiveDataBus;
import com.loan.vvver.utils.SPUtils;

import butterknife.BindView;

public class OwnInFoActivity extends BaseActivity {

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.age)
    TextView age;
    @BindView(R.id.phonenumber)
    TextView phonenumber;
    @BindView(R.id.bankname)
    TextView bankname;
    @BindView(R.id.banknumber)
    TextView banknumber;

    @Override
    protected int createLayoutId() {
        return R.layout.activity_own_in_fo;
    }

    @Override
    protected void initViewAndData() {
        title.setVisibility(View.VISIBLE);
        titleLeft.setVisibility(View.VISIBLE);
        title.setText(getString(R.string.a19));
        titleLeft.setBackgroundResource(R.drawable.ic_back);

        initNetAsk();
    }

    private void initNetAsk() {
        startLoading(OwnInFoActivity.this);
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.OwnInFo)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new HomeBody(String.valueOf(SPUtils.get(OwnInFoActivity.this, AppConstant.islog, "")), parseStrToMd5L32(SPUtils.get(OwnInFoActivity.this, AppConstant.islog, "") + AppConstant.endconfig))))
                .build().enqueue(new LoanCallBack<OwnInFoBean>() {
            @Override
            public void error(String error, int code) {
                closeLoding();
                Log.e(TAG, "error: " + error + code);
            }

            @Override
            public OwnInFoBean convert(JsonElement result) {
                return JsonUtils.jsonToClass(result, OwnInFoBean.class);
            }

            @Override
            public void onSucess(OwnInFoBean ownInFoBean) {
                closeLoding();
                Log.e(TAG, "onSucess: 个人信息 = " + ownInFoBean.toString());

                name.setText(ownInFoBean.getRealname());
                sex.setText(ownInFoBean.getSex());
                age.setText(ownInFoBean.getAge());
                phonenumber.setText(ownInFoBean.getMobilePhone());
                bankname.setText(ownInFoBean.getBankname());
                banknumber.setText(ownInFoBean.getCardNo());
            }
        });
        Log.e(TAG, "initNetAsk: " + jsonbody(new HomeBody(String.valueOf(SPUtils.get(OwnInFoActivity.this, AppConstant.islog, "")), parseStrToMd5L32(SPUtils.get(OwnInFoActivity.this, AppConstant.islog, "") + AppConstant.endconfig))));
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