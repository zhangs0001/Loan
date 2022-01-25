package com.loan.vvver.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.utils.JsonUtils;
import com.example.mvplibrary.activity.BaseActivity;
import com.google.gson.JsonElement;
import com.loan.vvver.R;
import com.loan.vvver.config.ApiConstant;
import com.loan.vvver.config.AppConstant;
import com.loan.vvver.data.bean.NullBean;
import com.loan.vvver.data.bean.OssParameterBean;
import com.loan.vvver.data.body.HomeBody;
import com.loan.vvver.data.body.ShootVideoBody;
import com.loan.vvver.http.LoanCallBack;
import com.loan.vvver.utils.CameraUtils;
import com.loan.vvver.utils.LiveDataBus;
import com.loan.vvver.utils.SPUtils;
import com.loan.vvver.utils.ToastUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadVideoActivity extends BaseActivity {


    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.btn_ShootVideo)
    Button btnShootVideo;
    @BindView(R.id.tv_say)
    TextView tvSay;

    private Uri fileUri;
    private String ACCESS_ID;
    private String ACCESS_KEY;
    private String bucketName;
    // 申请相机权限的requestCode
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 0x00000012;

    protected int createLayoutId() {
        return R.layout.activity_upload_video;
    }

    @Override
    protected void initViewAndData() {
        title.setVisibility(View.VISIBLE);
        titleLeft.setVisibility(View.VISIBLE);
        titleRight.setVisibility(View.VISIBLE);
        titleLeft.setBackgroundResource(R.drawable.ic_back);
        title.setText(getString(R.string.a102));
        Intent intent = getIntent();
        String str = intent.getStringExtra("say");
        tvSay.setText(str);
        initOssParameter();
    }

    @Override
    protected void initClick() {
        fastClickChecked(titleLeft, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fastClickChecked(btnShootVideo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 打开相机录像
                doOpenCameraForVideo();
            }
        });
    }

    private void doOpenCameraForVideo() {
        CameraUtils.openCameraForVideo(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CameraUtils.RequestCode.FLAG_REQUEST_CAMERA_VIDEO && resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult: " + data.getData());
            long timeStampSec = System.currentTimeMillis() / 1000;
            String timestamp = String.format("%010d", timeStampSec);
            String hkpz = SPUtils.get(UploadVideoActivity.this, AppConstant.islog, "") + "=" + timestamp + ".mp4";
            UploadImage(ACCESS_KEY, ACCESS_ID, bucketName, hkpz,
                    data.getData());
            UploadVideo(hkpz);
        }
    }

    private void UploadVideo(String hkpz) {
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.ShootVideo)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new ShootVideoBody(String.valueOf(SPUtils.get(UploadVideoActivity.this, AppConstant.islog, "")),
                        hkpz, parseStrToMd5L32(SPUtils.get(UploadVideoActivity.this, AppConstant.islog, "") + AppConstant.endconfig))))
                .build().enqueue(new LoanCallBack<NullBean>() {
            @Override
            public void error(String error, int code) {
                Log.e(TAG, "error: " + error + code);
            }

            @Override
            public NullBean convert(JsonElement result) {
                return JsonUtils.jsonToClass(result, NullBean.class);
            }

            @Override
            public void onSucess(NullBean ossParameterBean) {
                ToastUtil.showToast(UploadVideoActivity.this, getString(R.string.a103));
                finish();
            }
        });
    }

    private void initOssParameter() {
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.OSSParameter)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new HomeBody(String.valueOf(SPUtils.get(UploadVideoActivity.this, AppConstant.islog, "")), parseStrToMd5L32(SPUtils.get(UploadVideoActivity.this, AppConstant.islog, "") + AppConstant.endconfig))))
                .build().enqueue(new LoanCallBack<OssParameterBean>() {
            @Override
            public void error(String error, int code) {
                Log.e(TAG, "error: " + error + code);
            }

            @Override
            public OssParameterBean convert(JsonElement result) {
                return JsonUtils.jsonToClass(result, OssParameterBean.class);
            }

            @Override
            public void onSucess(OssParameterBean ossParameterBean) {
                Log.e(TAG, "onSucess: " + ossParameterBean.toString());
                ACCESS_ID = ossParameterBean.getOSSInfo().getPass();
                ACCESS_KEY = ossParameterBean.getOSSInfo().getAccount();
                bucketName = ossParameterBean.getOSSInfo().getName();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //刷新主页状态接口
        LiveDataBus.get().with("refreshhomepage").postValue(10);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

}