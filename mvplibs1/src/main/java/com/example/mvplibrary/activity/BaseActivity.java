package com.example.mvplibrary.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.example.mvplibrary.R;
import com.example.mvplibrary.utils.DbBean;
import com.google.gson.Gson;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.mvplibrary.fragment.BaseFragment.OSS_ENDPOINT;

public abstract class BaseActivity extends RxAppCompatActivity {
    public String TAG = getClass().getSimpleName();
    private Unbinder unbinder;
    private static Dialog loadingDialog;
    private Calendar calendars = Calendar.getInstance();
    public static final String OSS_ENDPOINT = "http://oss-ap-southeast-1.aliyuncs.com";
    private OSSClient oss;
    // ??????
    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // ???????????????
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    //?????????????????????uri
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(createLayoutId());
        unbinder = ButterKnife.bind(this);
        initViewAndData();
        initClick();
    }

    //    ????????????
    protected abstract int createLayoutId();

    //    ???????????????
    protected abstract void initViewAndData();

    //    ????????????
    protected abstract void initClick();

    //Loding??????
    @SuppressLint("SetTextI18n")
    public static Dialog startLoading(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);
        LinearLayout layout = v.findViewById(R.id.dialog_loading_view);
        TextView tipTextView = v.findViewById(R.id.tipTextView);
        tipTextView.setText("Loading...");
        loadingDialog = new Dialog(context, R.style.MyDialogStyle);
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(layout,
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.PopWindowAnimStyle);
        loadingDialog.show();
        return loadingDialog;
    }

    //Loding??????
    public static void closeLoding() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    /*
     * @Description: 32?????????MD5
     */
    public static String parseStrToMd5L32(String str) {
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(str.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes) {
                int bt = b & 0xff;
                if (bt < 16) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    /*
     * @param str
     * @Description: 32?????????MD5
     */
    public static String parseStrToMd5U32(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.toUpperCase();
        }
        return reStr;
    }

    /*
     * @param str
     * @Description: 16?????????MD5
     */
    public static String parseStrToMd5U16(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.toUpperCase().substring(8, 24);
        }
        return reStr;
    }

    /*
     * @param str
     * @Description: 16?????????MD5
     */
    public static String parseStrToMd5L16(String str) {
        String reStr = parseStrToMd5L32(str);
        if (reStr != null) {
            reStr = reStr.substring(8, 24);
        }
        return reStr;
    }

    /*
     * ????????????
     * */
    public static void fastClickChecked(final View v, final View.OnClickListener listener) {
        if (v == null) return;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                listener.onClick(v1);
                v1.setClickable(false);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setClickable(true);
                    }
                }, 1000);
            }
        });
    }


    /**
     * 60s?????????
     */
    public CountDownTimer startTimer(final TextView view) {
        /**
         * ?????????60????????????1???
         */
        CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                view.setClickable(false);
                view.setText(millisUntilFinished / 1000 + "second");
            }

            @Override
            public void onFinish() {
                view.setClickable(true);
                view.setText("Reacquire");
            }
        }.start();
        return timer;
    }

    /*
     * ???????????????JSON
     * */
    public String jsonbody(Object object) {
        return new Gson().toJson(object, Object.class);
    }

    /*
     * ??????Calendar??????????????????
     */

    //???
    public String getYEAR() {
        return String.valueOf(calendars.get(Calendar.YEAR));
    }

    //???
    public String getMONTH() {
        return String.valueOf(calendars.get(Calendar.MONTH) + 1);
    }

    //???
    public String getDAY() {
        return String.valueOf(calendars.get(Calendar.DAY_OF_MONTH));
    }

    //???
    public String getHOUR() {
        return String.valueOf(calendars.get(Calendar.HOUR_OF_DAY));
    }

    //???
    public String getMIN() {
        return String.valueOf(calendars.get(Calendar.MINUTE));
    }

    //???
    public String getSECOND() {
        return String.valueOf(calendars.get(Calendar.SECOND));
    }

    //???????????????
    public boolean getISAM() {
        return calendars.get(Calendar.AM_PM) == 1 ? true : false;
    }

    //???????????????????????????
    public boolean getIS24() {
        return DateFormat.is24HourFormat(getApplication()) ? true : false;
    }

    /**
     * ali oss????????????
     */
    public void UploadImage(String AccessId, String AccessKey, String bucketName, String objectName, Uri uploadFilePath) {
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(AccessId, AccessKey);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // ?????????????????????15???
        conf.setSocketTimeout(15 * 1000); // socket???????????????15???
        conf.setMaxConcurrentRequest(8); // ??????????????????????????????5???
        conf.setMaxErrorRetry(2); // ????????????????????????????????????2???
        // oss??????????????????OSS_ENDPOINT?????????OSS????????????
        oss = new OSSClient(this.getApplicationContext(), OSS_ENDPOINT, credentialProvider, conf);

        // ??????????????????
        PutObjectRequest put = new PutObjectRequest(bucketName, objectName, uploadFilePath);
        // ???????????????????????????????????????
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d(TAG, "PutObject " + "UploadSuccess");
                Log.d(TAG, "ETag " + result.getETag());
                Log.d(TAG, "RequestId " + result.getRequestId());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // ????????????
                if (clientExcepion != null) {
                    // ??????????????????????????????
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // ????????????
                    Log.e(TAG, "ErrorCode " + serviceException.getErrorCode());
                    Log.e(TAG, "RequestId " + serviceException.getRequestId());
                    Log.e(TAG, "HostId " + serviceException.getHostId());
                    Log.e(TAG, "RawMessage " + serviceException.getRawMessage());
                }
            }
        });
    }

    //?????????????????????
    public String getVersionName() {
        //??????packagemanager?????????
        PackageManager packageManager = getPackageManager();
        //getPackageName()???????????????????????????0???????????????????????????
        PackageInfo packageInfo = null;
        String versionname = "";
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionname = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionname;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
