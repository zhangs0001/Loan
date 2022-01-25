package com.loan.vvver.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;

import com.bigkoo.pickerview.TimePickerView;
import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.utils.JsonUtils;
import com.example.mvplibrary.fragment.BaseFragment;
import com.google.gson.JsonElement;
import com.loan.vvver.R;
import com.loan.vvver.config.ApiConstant;
import com.loan.vvver.config.AppConstant;
import com.loan.vvver.data.bean.NullBean;
import com.loan.vvver.data.bean.OssParameterBean;
import com.loan.vvver.data.body.HomeBody;
import com.loan.vvver.data.body.WorkBody;
import com.loan.vvver.http.LoanCallBack;
import com.loan.vvver.utils.LiveDataBus;
import com.loan.vvver.utils.SPUtils;
import com.loan.vvver.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

public class WorkFragment extends BaseFragment {

    @BindView(R.id.workName)
    EditText workName;
    @BindView(R.id.tel)
    EditText tel;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.position)
    EditText position;
    @BindView(R.id.pay)
    Spinner pay;
    @BindView(R.id.tv_time)
    EditText tv_time;
    @BindView(R.id.view_workcardFront)
    View viewWorkcardFront;
    @BindView(R.id.iv_workcardFront)
    ImageView ivWorkcardFront;
    @BindView(R.id.view_wordcardOpposite)
    View viewWordcardOpposite;
    @BindView(R.id.iv_workcardOpposite)
    ImageView ivWorkcardOpposite;
    @BindView(R.id.view_workcardTake)
    View viewWorkcardTake;
    @BindView(R.id.iv_workcardTake)
    ImageView ivWorkcardTake;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.sp_worktype)
    Spinner spWorktype;
    @BindView(R.id.tv_offline)
    TextView tvOffline;
    @BindView(R.id.tv_on_line)
    TextView tvOnLine;
    @BindView(R.id.btn_jump_over)
    Button btnJumpOver;
    @BindView(R.id.lin_info)
    LinearLayout linInfo;

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
    private int flag = 0;

    private String pOne = "nodata";
    private String pTwo = "nodata";
    private String pThree = "nodata";
    private String getContent;
    private String sp_worktype;
    private String name = "nodata";
    private String tel1 = "nodata";
    private String address1 = "nodata";
    private String position1 = "nodata";
    private String pay1 = "nodata";
    private String time = "nodata";
    private String industry = "nodata";

    @Override
    protected int createLayoutId() {
        return R.layout.fragment_work;
    }

    @Override
    protected void initViewAndData() {
        tv_time.setFocusable(false);
        tv_time.setFocusableInTouchMode(false);
        pay.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                getContent = getActivity().getResources().getStringArray(R.array.spingarr)[arg2];
                Log.e(TAG, "onItemSelected: " + getContent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spWorktype.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                sp_worktype = getActivity().getResources().getStringArray(R.array.employmentType)[arg2];
                Log.e(TAG, "onItemSelected: " + sp_worktype);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        initOssParameter();
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

    @Override
    protected void initClick() {
        fastClickChecked(viewWorkcardFront, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndCamera();
                flag = 0;
            }
        });
        fastClickChecked(viewWordcardOpposite, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewWorkcardFront.getVisibility() == View.GONE) {
                    checkPermissionAndCamera();
                    flag = 1;
                } else {
                    ToastUtil.showToast(getActivity(), getString(R.string.a110));
                }
            }
        });
        fastClickChecked(viewWorkcardTake, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewWorkcardFront.getVisibility() == View.GONE && viewWordcardOpposite.getVisibility() == View.GONE) {
                    checkPermissionAndCamera();
                    flag = 2;
                } else {
                    ToastUtil.showToast(getActivity(), getString(R.string.a110));
                }
            }
        });

        fastClickChecked(btnCommit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = workName.getText().toString();
                tel1 = tel.getText().toString();
                address1 = address.getText().toString();
                position1 = position.getText().toString();
                pay1 = getContent;
                time = tv_time.getText().toString();
                industry = sp_worktype;
                if (!"".equals(name)) {
                    name = workName.getText().toString();
                } else {
                    name = "nodata";
                }
                if (!"".equals(tel1)) {
                    tel1 = tel.getText().toString();
                } else {
                    tel1 = "nodata";
                }
                if (!"".equals(address1)) {
                    address1 = address.getText().toString();
                } else {
                    address1 = "nodata";
                }
                if (!"".equals(position1)) {
                    position1 = position.getText().toString();
                } else {
                    position1 = "nodata";
                }
                if (!"".equals(pay1)) {
                    pay1 = getContent;
                } else {
                    pay1 = "nodata";
                }
                if (!"".equals(time)) {
                    time = tv_time.getText().toString();
                } else {
                    time = "nodata";
                }
                if (!"".equals(industry)) {
                    industry = sp_worktype;
                } else {
                    industry = "nodata";
                }

                Log.e(TAG, "onClick: " + name + tel1 + address1 + position1 + pay1 + time + industry);
//                if (!"".equals(name) &&
//                        !"".equals(tel1) &&
//                        !"".equals(address1) &&
//                        !"".equals(position1) &&
//                        !"".equals(pay1) &&
//                        !"".equals(time) &&
//                        !"".equals(industry)) {
                commit(name, tel1, address1, position1, pay1, time, industry);
//                } else {
//                    ToastUtil.showToast(getActivity(), getString(R.string.a112));
//                }
            }
        });

        fastClickChecked(tv_time, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar selectedDate = Calendar.getInstance();//系统当前时间
                Calendar startDate = Calendar.getInstance();
                startDate.set(1800, 0, 1);
                Calendar endDate = Calendar.getInstance();
                endDate.set(2069, 2, 28);
                showDate(selectedDate, startDate, endDate);
            }
        });

        fastClickChecked(tvOffline, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linInfo.setVisibility(View.VISIBLE);
                btnJumpOver.setVisibility(View.GONE);
                tvOffline.setTextColor(Color.parseColor("#03DAC5"));
                tvOnLine.setTextColor(Color.parseColor("#8A8A8A"));
            }
        });
        fastClickChecked(tvOnLine, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linInfo.setVisibility(View.GONE);
                btnJumpOver.setVisibility(View.VISIBLE);
                tvOffline.setTextColor(Color.parseColor("#8A8A8A"));
                tvOnLine.setTextColor(Color.parseColor("#03DAC5"));
            }
        });

        fastClickChecked(btnJumpOver, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit(name, tel1, address1, position1, pay1, time, industry);
            }
        });

    }

    private void showDate(Calendar selectedDate, Calendar startDate, Calendar endDate) {
        TimePickerView pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSelect(Date date, View v) {
                String time = getTime(date);
                String[] split = time.split("-");
                Log.e(TAG, "onTimeSelect: " + split[0] + split[1] + split[2]);
                tv_time.setText(split[2] + "-" + split[1] + "-" + split[0]);
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("Cancel")//取消按钮文字
                .setSubmitText("confirm")//确认按钮文字
//                .setContentSize(18)//滚轮文字大小
//                .setTitleSize(20)//标题文字大小
//                //.setTitleText("Title")//标题文字
//                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
//                .isCyclic(true)//是否循环滚动
//                //.setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                //.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
//                //.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //.isDialog(true)//是否显示为对话框样式
                .build();

        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        //"YYYY-MM-DD HH:MM:SS"        "yyyy-MM-dd"
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private void commit(String name, String tel, String address, String position, String pay, String time, String industry) {
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.UploadWorkInFo)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new WorkBody(name, String.valueOf(SPUtils.get(getActivity(), AppConstant.islog, "")),
                        tel, parseStrToMd5L32(SPUtils.get(getActivity(), AppConstant.islog, "") + tel + AppConstant.endconfig),
                        address, position, pay, time, industry, pOne, pTwo, pThree)))
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
                //通知父activity刷新tab状态
                LiveDataBus.get().with("tabstate").postValue(40);
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
                    if (flag == 0) {
                        long timeStampSec = System.currentTimeMillis() / 1000;
                        String timestamp = String.format("%010d", timeStampSec);
                        ivWorkcardFront.setImageURI(mCameraUri);
                        Log.e(TAG, "onActivityResult: 0" + mCameraUri);
                        pOne = SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                + timestamp + ".jpg";
                        viewWorkcardFront.setVisibility(View.GONE);
                        Log.e(TAG, "onActivityResult: 0 ========= " + SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                + timestamp + ".jpg");
                        UploadImage(ACCESS_KEY, ACCESS_ID, bucketName, SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                        + timestamp + ".jpg",
                                mCameraUri);
                    } else if (flag == 1) {
                        // Android 10 使用图片uri加载
                        long timeStampSec = System.currentTimeMillis() / 1000;
                        String timestamp = String.format("%010d", timeStampSec);
                        ivWorkcardOpposite.setImageURI(mCameraUri);
                        Log.e(TAG, "onActivityResult: 1" + mCameraUri);
                        pTwo = SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                + timestamp + ".jpg";
                        viewWordcardOpposite.setVisibility(View.GONE);
                        Log.e(TAG, "onActivityResult: 0 ========= " + SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                + timestamp + ".jpg");
                        UploadImage(ACCESS_KEY, ACCESS_ID, bucketName, SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                        + timestamp + ".jpg",
                                mCameraUri);
                    } else if (flag == 2) {
                        long timeStampSec = System.currentTimeMillis() / 1000;
                        String timestamp = String.format("%010d", timeStampSec);
                        // Android 10 使用图片uri加载
                        ivWorkcardTake.setImageURI(mCameraUri);
                        Log.e(TAG, "onActivityResult: 2" + mCameraUri);
                        pThree = SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                + timestamp + ".jpg";
                        viewWorkcardTake.setVisibility(View.GONE);
                        Log.e(TAG, "onActivityResult: 0 ========= " + SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                + timestamp + ".jpg");
                        UploadImage(ACCESS_KEY, ACCESS_ID, bucketName, SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                        + timestamp + ".jpg",
                                mCameraUri);
                    }
                }
            } else {
//                if (flag == 0) {
//                    // 使用图片路径加载
//                    ivWorkcardFront.setImageBitmap(BitmapFactory.decodeFile(mCameraImagePath));
//                    Log.e(TAG, "onActivityResult: 0");
//                    pOne = SPUtils.get(getContext(),
//                            AppConstant.islog, "") + "="
//                            + getYEAR()
//                            + getMONTH()
//                            + getDAY()
//                            + getHOUR()
//                            + getMIN()
//                            + getSECOND() + ".jpg";
//                    viewWorkcardFront.setVisibility(View.GONE);
//                } else if (flag == 1) {
//                    // 使用图片路径加载
//                    ivWorkcardOpposite.setImageBitmap(BitmapFactory.decodeFile(mCameraImagePath));
//                    Log.e(TAG, "onActivityResult: 1");
//                    pTwo = SPUtils.get(getContext(),
//                            AppConstant.islog, "") + "="
//                            + getYEAR()
//                            + getMONTH()
//                            + getDAY()
//                            + getHOUR()
//                            + getMIN()
//                            + getSECOND() + ".jpg";
//                    viewWordcardOpposite.setVisibility(View.GONE);
//                } else if (flag == 2) {
//                    // 使用图片路径加载
//                    ivWorkcardTake.setImageBitmap(BitmapFactory.decodeFile(mCameraImagePath));
//                    Log.e(TAG, "onActivityResult: 2");
//                    pThree = SPUtils.get(getContext(),
//                            AppConstant.islog, "") + "="
//                            + getYEAR()
//                            + getMONTH()
//                            + getDAY()
//                            + getHOUR()
//                            + getMIN()
//                            + getSECOND() + ".jpg";
//                    viewWorkcardTake.setVisibility(View.GONE);
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
            Log.e(TAG, "onActivityResult: " + mCameraUri);
            Log.e(TAG, "onActivityResult: " + mCameraImagePath);
        } else {
//            Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
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
}