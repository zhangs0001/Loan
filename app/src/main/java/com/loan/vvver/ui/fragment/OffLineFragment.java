package com.loan.vvver.ui.fragment;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;

import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.utils.JsonUtils;
import com.example.mvplibrary.fragment.BaseFragment;
import com.google.gson.JsonElement;
import com.loan.vvver.R;
import com.loan.vvver.config.ApiConstant;
import com.loan.vvver.config.AppConstant;
import com.loan.vvver.data.bean.NullBean;
import com.loan.vvver.data.bean.OssParameterBean;
import com.loan.vvver.data.bean.RepaymentOneBean;
import com.loan.vvver.data.body.CertificateBody;
import com.loan.vvver.data.body.HomeBody;
import com.loan.vvver.data.body.RePayMentOneBody;
import com.loan.vvver.data.body.UploadBankInFoBody;
import com.loan.vvver.http.LoanCallBack;
import com.loan.vvver.utils.LiveDataBus;
import com.loan.vvver.utils.SPUtils;
import com.loan.vvver.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

public class OffLineFragment extends BaseFragment {

    @BindView(R.id.iv_IDCardFront)
    ImageView ivIDCardFront;
    @BindView(R.id.view_cameraFront)
    View viewCameraFront;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.user_card)
    TextView userCard;
    @BindView(R.id.bank_card)
    TextView bankCard;
    @BindView(R.id.bank_name)
    TextView bankName;

    // 申请相机权限的requestCode
    private static final int PERMISSION_CAMERA_REQUEST_CODE = 0x00000012;

    //用于保存拍照图片的uri
    private Uri mCameraUri;
    // 用于保存图片的文件路径，Android 10以下使用图片路径访问图片
    private String mCameraImagePath;
    // 是否是Android 10以上手机
    private boolean isAndroidQ = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q;
    private String ACCESS_ID;
    private String ACCESS_KEY;
    private String bucketName;
    private String pOne;

    @Override
    protected int createLayoutId() {
        return R.layout.fragment_off_line;
    }

    @Override
    protected void initViewAndData() {
        initData();
        initOssParameter();
    }

    private void initData() {
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.RePayMent)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new RePayMentOneBody(String.valueOf(SPUtils.get(getActivity(), AppConstant.islog, "")),
                        1,
                        parseStrToMd5L32(1 + String.valueOf(SPUtils.get(getActivity(), AppConstant.islog, "")) + AppConstant.endconfig))))
                .build().enqueue(new LoanCallBack<RepaymentOneBean>() {
            @Override
            public void error(String error, int code) {
                Log.e(TAG, "error: " + error + code);
            }

            @Override
            public RepaymentOneBean convert(JsonElement result) {
                return JsonUtils.jsonToClass(result, RepaymentOneBean.class);
            }

            @Override
            public void onSucess(RepaymentOneBean repaymentOneBean) {
                bankName.setText(repaymentOneBean.getBankList().get(0).getBankname());
                userCard.setText(repaymentOneBean.getBankList().get(0).getAccount());
                bankCard.setText(repaymentOneBean.getBankList().get(0).getCardnumber());
            }
        });
    }

    @Override
    protected void initClick() {
        fastClickChecked(viewCameraFront, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndCamera();
            }
        });

        fastClickChecked(btnCommit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pOne != null) {
                    OffLineRePayMent();
                } else {
                    ToastUtil.showToast(getActivity(), getString(R.string.a106));
                }
            }
        });
    }

    private void OffLineRePayMent() {
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.certificate)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new CertificateBody(String.valueOf(SPUtils.get(getActivity(), AppConstant.islog, "")), pOne,
                        parseStrToMd5L32(pOne + SPUtils.get(getActivity(), AppConstant.islog, "") + AppConstant.endconfig))))
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
            public void onSucess(NullBean nullBean) {
                ToastUtil.showToast(getActivity(), getString(R.string.a106));
                LiveDataBus.get().with("gb").postValue(100);
            }
        });
    }

    /**
     * 检查权限并拍照。
     * 调用相机前先检查权限。
     */
    private void checkPermissionAndCamera() {
        int hasCameraPermission = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);
        if (hasCameraPermission == PackageManager.PERMISSION_GRANTED) {
            //有调起相机拍照。
            openCamera();
        } else {
            //没有权限，申请权限。
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                    PERMISSION_CAMERA_REQUEST_CODE);
        }
    }

    /**
     * 调起相机拍照
     */
    private void openCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断是否有相机
        if (captureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            Uri photoUri = null;

            if (isAndroidQ) {
                // 适配android 10
                photoUri = createImageUri();
            } else {
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (photoFile != null) {
                    mCameraImagePath = photoFile.getAbsolutePath();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                        photoUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".fileprovider", photoFile);
                    } else {
                        photoUri = Uri.fromFile(photoFile);
                    }
                }
            }

            mCameraUri = photoUri;
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(captureIntent, 200);
            }
        }
    }

    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    private Uri createImageUri() {
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        } else {
            return getActivity().getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        }
    }

    /**
     * 创建保存图片的文件
     */
    private File createImageFile() throws IOException {
        String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File tempFile = new File(storageDir, imageName);
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (resultCode == RESULT_OK) {
                if (isAndroidQ) {
                    long timeStampSec = System.currentTimeMillis() / 1000;
                    String timestamp = String.format("%010d", timeStampSec);
                    ivIDCardFront.setImageURI(mCameraUri);
                    Log.e(TAG, "onActivityResult: 0 " + mCameraUri);
                    pOne = SPUtils.get(getContext(),
                            AppConstant.islog, "") + "="
                            + timestamp + ".jpg";
                    viewCameraFront.setVisibility(View.GONE);

                    Log.e(TAG, "onActivityResult: 0 ========= " + SPUtils.get(getContext(),
                            AppConstant.islog, "") + "="
                            + timestamp + ".jpg");
                    UploadImage(ACCESS_KEY, ACCESS_ID, bucketName, SPUtils.get(getContext(),
                            AppConstant.islog, "") + "="
                                    + timestamp + ".jpg",
                            mCameraUri);
                }
            } else {
//                if (flag == 0) {
//                    // 使用图片路径加载
//                    ivIDCardFront.setImageBitmap(BitmapFactory.decodeFile(mCameraImagePath));
//                    Log.e(TAG, "onActivityResult: 0");
//                    pOne = SPUtils.get(getContext(),
//                            AppConstant.islog, "") + "="
//                            + getYEAR()
//                            + getMONTH()
//                            + getDAY()
//                            + getHOUR()
//                            + getMIN()
//                            + getSECOND() + ".jpg";
//                } else if (flag == 1) {
//                    // 使用图片路径加载
//                    ivIDCardOpposite.setImageBitmap(BitmapFactory.decodeFile(mCameraImagePath));
//                    Log.e(TAG, "onActivityResult: 1");
//                    pTwo = SPUtils.get(getContext(),
//                            AppConstant.islog, "") + "="
//                            + getYEAR()
//                            + getMONTH()
//                            + getDAY()
//                            + getHOUR()
//                            + getMIN()
//                            + getSECOND() + ".jpg";
//                } else if (flag == 2) {
//                    // 使用图片路径加载
//                    ivIDCardTake.setImageBitmap(BitmapFactory.decodeFile(mCameraImagePath));
//                    Log.e(TAG, "onActivityResult: 2");
//                    pThree = SPUtils.get(getContext(),
//                            AppConstant.islog, "") + "="
//                            + getYEAR()
//                            + getMONTH()
//                            + getDAY()
//                            + getHOUR()
//                            + getMIN()
//                            + getSECOND() + ".jpg";
//                }

//                UploadImage(ACCESS_KEY, ACCESS_ID, bucketName, SPUtils.get(getContext(),
//                        AppConstant.islog, "") + "="
//                                + getYEAR()
//                                + getMONTH()
//                                + getDAY()
//                                + getHOUR()
//                                + getMIN()
//                                + getSECOND() + ".jpg",
//                        mCameraImagePath);
            }
//            Log.e(TAG, "onActivityResult: " + mCameraUri);
//            Log.e(TAG, "onActivityResult: " + mCameraImagePath);
        } else {
            Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * 处理权限申请的回调。
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //允许权限，有调起相机拍照。
//                openCamera();
            } else {
                //拒绝权限，弹出提示框。
                Toast.makeText(getActivity(), getString(R.string.a108), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initOssParameter() {
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.OSSParameter)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new HomeBody(String.valueOf(SPUtils.get(getActivity(), AppConstant.islog, "")), parseStrToMd5L32(SPUtils.get(getActivity(), AppConstant.islog, "") + AppConstant.endconfig))))
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
}