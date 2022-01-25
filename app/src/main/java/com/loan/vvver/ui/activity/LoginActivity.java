package com.loan.vvver.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.loan.vvver.data.bean.GetCodeBean;
import com.loan.vvver.data.bean.LoginBean;
import com.loan.vvver.data.bean.NullBean;
import com.loan.vvver.data.body.GetCodeBody;
import com.loan.vvver.data.body.LoginBody;
import com.loan.vvver.data.body.UploadLocationBody;
import com.loan.vvver.http.LoanCallBack;
import com.loan.vvver.utils.CheckPermission;
import com.loan.vvver.utils.SPUtils;
import com.loan.vvver.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_cdoe)
    EditText etCdoe;
    @BindView(R.id.tv_RetrieveCode)
    TextView tvRetrieveCode;
    @BindView(R.id.bt_continue)
    Button btContinue;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.btn_toggle)
    Button btnToggle;

    private String phone;
    private String code;

    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected int createLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewAndData() {
        List<String> strings = new ArrayList<>();
        strings.add(Manifest.permission.READ_CONTACTS);
        strings.add(Manifest.permission.READ_CALL_LOG);
        strings.add(Manifest.permission.ACCESS_FINE_LOCATION);
        strings.add(Manifest.permission.READ_SMS);
        if (CheckPermission.requestPermissions(this, strings, 1)) {
            //已经拥有权限后执行的操作
//            showLocation();
        }
    }

    @Override
    protected void initClick() {

    }

    @OnClick({R.id.tv_RetrieveCode, R.id.bt_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_RetrieveCode:
                phone = etPhone.getText().toString();
                if (!TextUtils.isEmpty(phone) && phone != null) {
                    startTimer(tvRetrieveCode);
                    ObtainMessage(phone);
                }
                break;
            case R.id.bt_continue:
                phone = etPhone.getText().toString();
                code = etCdoe.getText().toString();
                if (!TextUtils.isEmpty(phone) && phone != null && !TextUtils.isEmpty(code) && code != null) {
//                    //判断是否有权限
//                    if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, 201);
//                        Log.e(TAG, "initViewAndData: " + 1);
//                    } else {
//                        //有权限没有通过，需要申请
//                        if (Build.VERSION.SDK_INT >= 23) {
//                            Log.e(TAG, "initViewAndData: " + 2);
//                            //1. 检测是否添加权限   PERMISSION_GRANTED  表示已经授权并可以使用
//                            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//                                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1005);
//                                Log.e(TAG, "initViewAndData: " + 3);
//                            } else {//手机为Android6.0的版本,权限已授权可以使用
//                                // 执行下一步
//                                if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
//                                        != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
//                                    //开启定位权限,200是标识码
//                                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
//                                } else {
//                                    //开始定位
                    if (isLocServiceEnable(LoginActivity.this)) {
                        Login(phone, code);
//                        showLocation();
                    } else {
                        ToastUtil.showToast(LoginActivity.this, "Please enable location permission");
                    }

//                                }
//                            }
//                        } else {//手机为Android6.0以前的版本，可以使用
//
//                        }
//                    }
                }
                break;
        }
    }

    private void Login(String phone, String code) {
        List<LoginBody.AppBean> appBeans = new ArrayList<>();
        try {
            PackageManager pm = getPackageManager();
//        获取到所有安装了的应用程序的信息，包括那些卸载了的，但没有清除数据的应用程序
            List<PackageInfo> list2 = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
            int j = 0;
            for (PackageInfo packageInfo : list2) {
                //得到手机上已经安装的应用的名字,即在AndriodMainfest.xml中的app_name。
                String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
                //得到手机上已经安装的应用的图标,即在AndriodMainfest.xml中的icon。
                Drawable drawable = packageInfo.applicationInfo.loadIcon(getPackageManager());
                //得到应用所在包的名字,即在AndriodMainfest.xml中的package的值。
                String packageName = packageInfo.packageName;
                //应用安装时间
                long firstInstallTime = packageInfo.firstInstallTime;
                //应用最后一次更新时间
                long lastUpdateTime = packageInfo.lastUpdateTime;
                //版本号
                String versionName = packageInfo.versionName;
                appBeans.add(new LoginBody.AppBean(appName, "" + versionName, packageName, "" + firstInstallTime, "" + lastUpdateTime));
                j++;
            }
            Log.e("========cccccc", "应用的总个数:" + j);
        } catch (Exception e) {
            Log.e(TAG, "Login: " + e.getMessage());
        }

        Log.e(TAG, "Login: " + jsonbody(new LoginBody(
                phone,
                AppConstant.cellphoneType,
                parseStrToMd5L32(phone + AppConstant.cellphoneType + AppConstant.endconfig),
                code,
                "V_0_0_02",
                "",
                "",
                appBeans,
                getString(R.string.app_name),
                getVersionName(),
                getPackageName())));
        Log.e(TAG, "Login: " + phone);
        Log.e(TAG, "Login: " + AppConstant.cellphoneType);
        Log.e(TAG, "Login: " + parseStrToMd5L32(phone + AppConstant.cellphoneType + AppConstant.endconfig));
        Log.e(TAG, "Login: " + code);
        Log.e(TAG, "Login: " + "V_0_0_02");
        Log.e(TAG, "Login: " + appBeans);
        Log.e(TAG, "Login: " + getString(R.string.app_name));
        Log.e(TAG, "Login: " + getVersionName());
        Log.e(TAG, "Login: " + getPackageName());

        startLoading(LoginActivity.this);
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.login)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new LoginBody(
                        phone,
                        AppConstant.cellphoneType,
                        parseStrToMd5L32(phone + AppConstant.cellphoneType + AppConstant.endconfig),
                        code,
                        "V_0_0_02",
                        "IMEI",
                        "IMSI", appBeans, getString(R.string.app_name), getVersionName(), getPackageName())))
                .build().enqueue(new LoanCallBack<LoginBean>() {
            @Override
            public void error(String error, int code) {
                Log.e(TAG, "error: " + error + code);
                closeLoding();
            }

            @Override
            public LoginBean convert(JsonElement result) {
                return JsonUtils.jsonToClass(result, LoginBean.class);
            }

            @Override
            public void onSucess(LoginBean loginBean) {
                closeLoding();
                SPUtils.put(LoginActivity.this, AppConstant.islog, loginBean.getUserId());
                Log.e(TAG, "onSucess: " + loginBean.toString());
                ToastUtil.showToast(LoginActivity.this, getString(R.string.a8));
                showLocation(loginBean.getUserId());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public void ObtainMessage(String phone) {
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.getverificationcode)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new GetCodeBody(phone, parseStrToMd5L32(phone + AppConstant.endconfig))))
                .build().enqueue(new LoanCallBack<GetCodeBean>() {
            @Override
            public void error(String error, int code) {
                if (code == -5) {
                    ToastUtil.showToast(LoginActivity.this, getString(R.string.a7));
                }
                Log.e(TAG, "error: " + error + code);
            }

            @Override
            public GetCodeBean convert(JsonElement result) {
                return JsonUtils.jsonToClass(result, GetCodeBean.class);
            }

            @Override
            public void onSucess(GetCodeBean getCodeBean) {
                Log.e(TAG, "onSucess: " + getCodeBean.toString());
//                etCdoe.setText(getCodeBean.getRandom());
            }
        });
    }

    private void showLocation(String userId) {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(TAG, location.toString() + "  纬度：" + location.getLatitude() + "  经度：" + location.getLongitude());
                new HttpClient.Buidler()
                        .setBaseUrl(ApiConstant.BASEURL)
                        .setApiUrl(ApiConstant.uploadlocation)
                        .post()
                        .setJson(true)
                        .setJsonbody(jsonbody(new UploadLocationBody(userId, parseStrToMd5L32(userId + AppConstant.endconfig), String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()))))
                        .build()
                        .enqueue(new LoanCallBack<NullBean>() {
                            @Override
                            public void error(String error, int code) {
                                Log.e(TAG, "error: " + error + code);
                            }

                            @Override
                            public NullBean convert(JsonElement result) {
                                return JsonUtils.jsonToClass(result, NullBean.class);
                            }

                            @Override
                            public void onSucess(NullBean nullBean) {
                                Log.e(TAG, "onSucess: " + "位置上传成功");
                            }
                        });
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, locationListener);
        }
    }

//    public String IMEI() {
//        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        String imei = mTelephonyMgr.getDeviceId();
//        Log.i("IMEI", imei);
//        return imei;
//    }
//
//    public String IMSI() {
//        TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        String imsi = mTelephonyMgr.getSubscriberId();
//        Log.i("IMSI", imsi);
//        return imsi;
//    }

//    public List APPList() {
//        PackageManager packageManager = getPackageManager();
//        List<ApplicationInfo> list = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
//        return list;
//    }

    public void getAppProcessName(Context context) {
        //当前应用pid
        final PackageManager packageManager = context.getPackageManager();
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        // get all apps
        final List<ResolveInfo> apps = packageManager.queryIntentActivities(mainIntent, 0);
        for (int i = 0; i < apps.size(); i++) {
            String name = apps.get(i).activityInfo.packageName;
            if (!name.contains("huawei") && !name.contains("android")) {
                Log.i("TAG", "getAppProcessName: " +
                        apps.get(i).activityInfo.applicationInfo.loadLabel(packageManager).toString() + "---" +
                        apps.get(i).activityInfo.packageName);
            }
        }
    }


    @OnClick(R.id.btn_toggle)
    public void onViewClicked() {

        getAppProcessName(LoginActivity.this);
        setLanguage();
    }

    private void setLanguage() {
        /**
         * 切换韩文
         */
        // 获得res资源对象
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        // 获得屏幕参数：主要是分辨率，像素等。
        DisplayMetrics dm = resources.getDisplayMetrics();
        // 韩语
        config.locale = Locale.ENGLISH;
//        isChange = true;
        resources.updateConfiguration(config, dm);
        //模拟重启页面
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.e("requestCode", "requestCode" + requestCode);
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户同意了权限请求后的操作
                } else {
                    CheckPermission.showMissingPermissionDialog(LoginActivity.this, "权限");
                }
            }
            break;
        }

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        }
    }


/**
 * @author:程龙 date; On 2018/8/13
 */


    /**
     * 手机是否开启位置服务，如果没有开启那么所有app将不能使用定位功能
     */
    public static boolean isLocServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }
}