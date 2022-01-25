package com.loan.vvver.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Spinner;
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
import com.loan.vvver.data.body.UpLoadMyInFoBody;
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

public class OwnInFoFragment extends BaseFragment {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_IDNumper)
    EditText etIDNumper;
    @BindView(R.id.et_Address)
    EditText etAddress;
    @BindView(R.id.et_BirthDay)
    EditText etBirthDay;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.view_cameraFront)
    View viewCameraFront;
    @BindView(R.id.iv_IDCardFront)
    ImageView ivIDCardFront;
    @BindView(R.id.view_cameraOpposite)
    View viewCameraOpposite;
    @BindView(R.id.iv_IDCardOpposite)
    ImageView ivIDCardOpposite;
    @BindView(R.id.view_cameraTake)
    View viewCameraTake;
    @BindView(R.id.iv_IDCardTake)
    ImageView ivIDCardTake;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.et_Facebook)
    EditText etFacebook;
    @BindView(R.id.sex)
    Spinner sex;
    @BindView(R.id.sp_marriageStatus)
    Spinner spMarriageStatus;
    @BindView(R.id.sp_education)
    Spinner spEducation;
    @BindView(R.id.et_pannumber)
    EditText etPannumber;
    @BindView(R.id.et_panname)
    EditText etPanname;
    @BindView(R.id.et_panFatherName)
    EditText etPanFatherName;

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

    private String pOne;
    private String pTwo;
    private String pThree;

    private String getsex;
    private String getmarriageStatus;
    private String geteducation;


    @Override
    protected int createLayoutId() {
        return R.layout.fragment_own_in_fo;
    }

    @Override
    protected void initViewAndData() {
        initOssParameter();

        etBirthDay.setFocusable(false);
        etBirthDay.setFocusableInTouchMode(false);
        sex.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                getsex = getActivity().getResources().getStringArray(R.array.nannv)[arg2];
                Log.e(TAG, "onItemSelected: " + getsex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spMarriageStatus.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                getmarriageStatus = getActivity().getResources().getStringArray(R.array.marriageStatus)[arg2];
                Log.e(TAG, "onItemSelected: " + getmarriageStatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spEducation.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                geteducation = getActivity().getResources().getStringArray(R.array.education)[arg2];
                Log.e(TAG, "onItemSelected: " + geteducation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                etBirthDay.setText(split[2] + "-" + split[1] + "-" + split[0]);
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
        fastClickChecked(viewCameraFront, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndCamera();
                flag = 0;
            }
        });
        fastClickChecked(viewCameraOpposite, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewCameraFront.getVisibility() == View.GONE) {
                    checkPermissionAndCamera();
                    flag = 1;
                } else {
                    ToastUtil.showToast(getActivity(), getString(R.string.a110));
                }
            }
        });
        fastClickChecked(viewCameraTake, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewCameraFront.getVisibility() == View.GONE && viewCameraOpposite.getVisibility() == View.GONE) {
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
                String name = etName.getText().toString();
                String IDNumper = etIDNumper.getText().toString();
                String Address = etAddress.getText().toString();
                String BirthDay = etBirthDay.getText().toString();
                String Email = etEmail.getText().toString();
                String facebook = etFacebook.getText().toString();
                String pannumber = etPannumber.getText().toString();
                String panname = etPanname.getText().toString();
                String panfathername = etPanFatherName.getText().toString();
                LiveDataBus.get().with("name").postValue(name);
                Log.e(TAG, "onClick: " + name + "\n"
                        + "\n" + IDNumper + "\n" + Address + "\n" + BirthDay + "\n" + Email + "\n" + pOne + "\n" + pTwo + "\n" + pThree);
                if (!"".equals(name) && !"zhangsan".equals(name) &&
                        !"".equals(IDNumper) &&
                        !"".equals(Address) &&
                        !"".equals(BirthDay) &&
                        !"".equals(Email) &&
                        !"".equals(facebook) &&
                        !"".equals(getsex) &&
                        !"".equals(getmarriageStatus) &&
                        !"".equals(geteducation) &&
                        !"".equals(pannumber) &&
                        !"".equals(panname) &&
                        !"".equals(panfathername) &&
                        pOne != null &&
                        pTwo != null &&
                        pThree != null) {
                    commit(name, IDNumper, Address, BirthDay, Email, facebook, getsex, getmarriageStatus, geteducation, pannumber, panname, panfathername, pOne, pTwo, pThree);
                } else {
                    ToastUtil.showToast(getActivity(), getString(R.string.a111));
                }
            }
        });

        fastClickChecked(etBirthDay, new View.OnClickListener() {
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
    }

    private void commit(String name, String IDNumper, String Address, String BirthDay, String Email, String facebook, String getsex, String getmarriageStatus,
                        String geteducation, String pannumber, String panname, String panfathername, String pOne, String pTwo, String pThree) {
        Log.e(TAG, "commit: " + jsonbody(new UpLoadMyInFoBody(String.valueOf(SPUtils.get(getActivity(), AppConstant.islog, "")),
                IDNumper, Address, name, BirthDay, parseStrToMd5L32(SPUtils.get(getActivity(), AppConstant.islog, "") + AppConstant.endconfig),
                Email, pOne, pTwo, pThree, getsex, getmarriageStatus, geteducation, pannumber, panname, panfathername, facebook)));

        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.UploadIDphoto)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new UpLoadMyInFoBody(String.valueOf(SPUtils.get(getActivity(), AppConstant.islog, "")),
                        IDNumper, Address, name, BirthDay,
                        parseStrToMd5L32(SPUtils.get(getActivity(), AppConstant.islog, "") + AppConstant.endconfig),
                        Email, pOne, pTwo, pThree, getsex, getmarriageStatus, geteducation, pannumber, panname, panfathername, facebook)))
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
                LiveDataBus.get().with("tabstate").postValue(20);
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
                        ivIDCardFront.setImageURI(mCameraUri);
                        Log.e(TAG, "onActivityResult: 0 " + mCameraUri);
                        pOne = SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                + timestamp + ".jpg";
                        viewCameraFront.setVisibility(View.GONE);

                        Log.e(TAG, "onActivityResult: 0 ========= " + SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                + timestamp + ".jpg");
                        if (mCameraUri != null) {
                            UploadImage(ACCESS_KEY, ACCESS_ID, bucketName, SPUtils.get(getContext(),
                                    AppConstant.islog, "") + "="
                                            + timestamp + ".jpg",
                                    mCameraUri);
                        }

                    } else if (flag == 1) {
                        long timeStampSec = System.currentTimeMillis() / 1000;
                        String timestamp = String.format("%010d", timeStampSec);
                        // Android 10 使用图片uri加载
                        ivIDCardOpposite.setImageURI(mCameraUri);
                        Log.e(TAG, "onActivityResult: 1" + mCameraUri);
                        pTwo = SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                + timestamp + ".jpg";
                        viewCameraOpposite.setVisibility(View.GONE);

                        Log.e(TAG, "onActivityResult: 1 ========= " + SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                + timestamp + ".jpg");
                        if (mCameraUri != null) {
                            UploadImage(ACCESS_KEY, ACCESS_ID, bucketName, SPUtils.get(getContext(),
                                    AppConstant.islog, "") + "="
                                            + timestamp + ".jpg",
                                    mCameraUri);
                        }
                    } else if (flag == 2) {
                        long timeStampSec = System.currentTimeMillis() / 1000;
                        String timestamp = String.format("%010d", timeStampSec);
                        // Android 10 使用图片uri加载
                        ivIDCardTake.setImageURI(mCameraUri);
                        Log.e(TAG, "onActivityResult: 2" + mCameraUri);
                        pThree = SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                + timestamp + ".jpg";
                        viewCameraTake.setVisibility(View.GONE);

                        Log.e(TAG, "onActivityResult: 2 ========= " + SPUtils.get(getContext(),
                                AppConstant.islog, "") + "="
                                + timestamp + ".jpg");
                        if (mCameraUri != null) {
                            UploadImage(ACCESS_KEY, ACCESS_ID, bucketName, SPUtils.get(getContext(),
                                    AppConstant.islog, "") + "="
                                            + timestamp + ".jpg",
                                    mCameraUri);
                        }
                    }

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