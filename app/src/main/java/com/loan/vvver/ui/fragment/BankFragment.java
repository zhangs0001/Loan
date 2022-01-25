package com.loan.vvver.ui.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.utils.JsonUtils;
import com.example.mvplibrary.fragment.BaseFragment;
import com.google.gson.JsonElement;
import com.loan.vvver.R;
import com.loan.vvver.config.ApiConstant;
import com.loan.vvver.config.AppConstant;
import com.loan.vvver.data.bean.NullBean;
import com.loan.vvver.data.body.UploadBankInFoBody;
import com.loan.vvver.data.body.UrgenPeopleBody;
import com.loan.vvver.http.LoanCallBack;
import com.loan.vvver.utils.LiveDataBus;
import com.loan.vvver.utils.SPUtils;
import com.loan.vvver.utils.ToastUtil;

import butterknife.BindView;

public class BankFragment extends BaseFragment {

    @BindView(R.id.Name)
    TextView Name;
    @BindView(R.id.bankcardnumber)
    EditText bankcardnumber;
    @BindView(R.id.bankcoding)
    EditText bankcoding;
    @BindView(R.id.bankname)
    EditText bankname;
    @BindView(R.id.bankbranch)
    EditText bankbranch;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    private String mingzi;

    @Override
    protected int createLayoutId() {
        return R.layout.fragment_bank;
    }

    @Override
    protected void initViewAndData() {
        LiveDataBus.get().with("name", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String name) {
                Name.setText(name);
                mingzi = name;
                Log.e(TAG, "onChanged: " + name);
            }
        });
//        Name.setText(String.valueOf(SPUtils.get(getContext(), "name", "")));

    }

    @Override
    protected void initClick() {
        fastClickChecked(btnCommit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mingzi;
                String Bankcardnumber = bankcardnumber.getText().toString();
                String Bankcoding = bankcoding.getText().toString();
                String Bankname = bankname.getText().toString();
                String Bankbranch = bankbranch.getText().toString();
                if (!"".equals(Bankcardnumber) &&
                        !"".equals(Bankcoding) &&
                        !"".equals(Bankname) &&
                        !"".equals(Bankbranch)) {
                    UploadBankInFo(name, Bankcardnumber, Bankcoding, Bankname, Bankbranch);
                } else {
                    ToastUtil.showToast(getActivity(), getString(R.string.a105));
                }
            }
        });
    }

    private void UploadBankInFo(String name, String bankcardnumber, String bankcoding, String bankname, String bankbranch) {
        startLoading(getActivity());
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.BankInformation)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new UploadBankInFoBody(name,
                        String.valueOf(SPUtils.get(getActivity(), AppConstant.islog, "")),
                        bankcardnumber, bankcoding, parseStrToMd5L32(SPUtils.get(getActivity(), AppConstant.islog, "") + bankcardnumber + AppConstant.endconfig), bankname, bankbranch)))
                .build().enqueue(new LoanCallBack<NullBean>() {
            @Override
            public void error(String error, int code) {
                closeLoding();
                Log.e(TAG, "error: " + error + code);
            }

            @Override
            public NullBean convert(JsonElement result) {
                return JsonUtils.jsonToClass(result, NullBean.class);
            }

            @Override
            public void onSucess(NullBean nullBean) {
                closeLoding();
                //通知父activity关闭页面
                LiveDataBus.get().with("closure").postValue(10);
            }
        });
    }
}