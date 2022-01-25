package com.loan.vvver.ui.fragment;

import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.utils.JsonUtils;
import com.example.mvplibrary.fragment.BaseFragment;
import com.google.gson.JsonElement;
import com.loan.vvver.R;
import com.loan.vvver.config.ApiConstant;
import com.loan.vvver.config.AppConstant;
import com.loan.vvver.data.bean.RepaymentTwoBean;
import com.loan.vvver.data.body.RePayMentTwoBody;
import com.loan.vvver.http.LoanCallBack;
import com.loan.vvver.utils.SPUtils;
import com.loan.vvver.utils.ToastUtil;

import butterknife.BindView;

public class OnLineFragment extends BaseFragment {


    @BindView(R.id.RePaymoney)
    EditText RePaymoney;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.lin)
    LinearLayout lin;

    @Override
    protected int createLayoutId() {
        return R.layout.fragment_on_line;
    }

    @Override
    protected void initViewAndData() {

    }

    @Override
    protected void initClick() {
        fastClickChecked(btnCommit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String money = RePaymoney.getText().toString();
                if (!money.equals("")) {
                    initRePayMoney(money);
                } else {
                    ToastUtil.showToast(getActivity(), getString(R.string.a109));
                }
            }
        });
    }

    private void initRePayMoney(String money) {
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.RePayMent)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new RePayMentTwoBody(String.valueOf(SPUtils.get(getActivity(), AppConstant.islog, "")),
                        2,
                        parseStrToMd5L32(2 + String.valueOf(SPUtils.get(getActivity(), AppConstant.islog, "")) + AppConstant.endconfig), money)))
                .build().enqueue(new LoanCallBack<RepaymentTwoBean>() {
            @Override
            public void error(String error, int code) {
                Log.e(TAG, "error: " + error + code);
            }

            @Override
            public RepaymentTwoBean convert(JsonElement result) {
                return JsonUtils.jsonToClass(result, RepaymentTwoBean.class);
            }

            @Override
            public void onSucess(RepaymentTwoBean repaymentTwoBean) {
                lin.setVisibility(View.GONE);
                btnCommit.setVisibility(View.GONE);
                web.setVisibility(View.VISIBLE);
                web.loadUrl(repaymentTwoBean.getUrl());
                web.setVisibility(View.VISIBLE);
                web.loadUrl(repaymentTwoBean.getUrl());
                //扩大比例的缩放
                web.getSettings().setUseWideViewPort(true);
                web.setVerticalScrollbarOverlay(true);
                //自适应屏幕
                web.getSettings().setLoadWithOverviewMode(true);
                //支持javascript
                web.getSettings().setJavaScriptEnabled(true);
                // 设置可以支持缩放
                web.getSettings().setSupportZoom(true);
                //自适应屏幕
                web.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            }
        });
    }
}