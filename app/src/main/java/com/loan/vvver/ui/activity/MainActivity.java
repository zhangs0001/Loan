package com.loan.vvver.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.utils.JsonUtils;
import com.example.mvplibrary.activity.BaseActivity;
import com.example.mvplibrary.adapter.BaseAdapter;
import com.google.gson.JsonElement;
import com.kelin.banner.transformer.GalleryTransformer;
import com.kelin.banner.view.BannerView;
import com.loan.vvver.R;
import com.loan.vvver.adapter.ProductListAdapter;
import com.loan.vvver.config.ApiConstant;
import com.loan.vvver.config.AppConstant;
import com.loan.vvver.data.bean.HomeBannerBean;
import com.loan.vvver.data.bean.HomeBean;
import com.loan.vvver.data.bean.ImageBannerEntry;
import com.loan.vvver.data.bean.IsCompleteUserInFoBean;
import com.loan.vvver.data.bean.NullBean;
import com.loan.vvver.data.body.ContactsBody;
import com.loan.vvver.data.body.HomeBody;
import com.loan.vvver.data.body.LoanBody;
import com.loan.vvver.data.body.UploadBankInFoBody;
import com.loan.vvver.http.LoanCallBack;
import com.loan.vvver.utils.LiveDataBus;
import com.loan.vvver.utils.LocationUtils;
import com.loan.vvver.utils.MakeSureDialog;
import com.loan.vvver.utils.SPUtils;
import com.loan.vvver.utils.ToastUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.lin)
    LinearLayout lin;
    @BindView(R.id.tv_)
    TextView tv;
    @BindView(R.id.recy)
    RecyclerView recy;
    @BindView(R.id.vp_view_pager)
    BannerView bannerView;
    @BindView(R.id.tv_Customcombination)
    TextView tvCustomcombination;

    private int isComplete1;
    private int isComplete2;
    private int isComplete3;
    private int isComplete4;
    private int isLoComeToAccount;
    private int iscode;
    private String say;
    private String pic1;
    private String pic2;
    private String pic3;

    private static final String AF_DEV_KEY = "UJWRHUC59reKzKdoZkC2Ee";

//    // ??????
//    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
//    // ???????????????
//    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
//    //?????????????????????uri
//    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;


    @Override
    protected int createLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewAndData() {
        AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
                Log.d("LOG_TAG", "error getting conversion data: " + errorMessage);
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> conversionData) {
                for (String attrName : conversionData.keySet()) {
                    Log.d("LOG_TAG", "attribute: " + attrName + " = " + conversionData.get(attrName));
                }
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
                Log.d("LOG_TAG", "error onAttributionFailure : " + errorMessage);
            }
        };
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, conversionListener, getApplicationContext());
        AppsFlyerLib.getInstance().start(this);

        title.setVisibility(View.VISIBLE);
        titleRight.setVisibility(View.VISIBLE);
        title.setText(getString(R.string.app_name));
        titleRight.setBackgroundResource(R.drawable.ic_more);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bannerView.setShowLeftAndRightPage(30, true, new GalleryTransformer());
        } else {
            bannerView.setShowLeftAndRightPage(20);
        }
        bannerView.setEntries(getImageBannerEntries());


        LiveDataBus.get().with("refreshhomepage", Integer.class).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer refreshhomepage) {
                if (refreshhomepage == 10) {
                    initHomePage();
                    Log.e(TAG, "onChanged: " + "??????");
                }
            }
        });
        initHomePage();
        Log.e(TAG, "onChanged: " + "?????????");
    }

    private void initHomePage() {
        Log.e(TAG, "initHomePage: " + jsonbody(new HomeBody(String.valueOf(SPUtils.get(MainActivity.this, AppConstant.islog, "")), parseStrToMd5L32(SPUtils.get(MainActivity.this, AppConstant.islog, "") + AppConstant.endconfig))));
        startLoading(MainActivity.this);
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.HomePage)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new HomeBody(String.valueOf(SPUtils.get(MainActivity.this, AppConstant.islog, "")), parseStrToMd5L32(SPUtils.get(MainActivity.this, AppConstant.islog, "") + AppConstant.endconfig))))
                .build().enqueue(new LoanCallBack<HomeBean>() {
            @Override
            public void error(String error, int code) {
                Log.e(TAG, "error: " + error + code);
                closeLoding();
            }

            @Override
            public HomeBean convert(JsonElement result) {
                return JsonUtils.jsonToClass(result, HomeBean.class);
            }

            @Override
            public void onSucess(HomeBean homeBean) {
                closeLoding();
                Log.e(TAG, "onSucess: " + homeBean.toString());
                isLoComeToAccount = homeBean.getIsLoComeToAccount();
                iscode = homeBean.getIscode();
                say = homeBean.getSay();
                Log.e(TAG, "isLoComeToAccount: " + isLoComeToAccount + "    " + iscode + say);
                isComplete1 = homeBean.getStep2().getStep2();
                isComplete2 = homeBean.getStep3().getStep3();
                isComplete3 = homeBean.getStep4().getStep4();
                isComplete4 = homeBean.getStep1().getStep1();
                Log.e(TAG, "??????????????????: " + isComplete1 + isComplete2 + isComplete3 + isComplete4);
                recy.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                ProductListAdapter productListAdapter = new ProductListAdapter(homeBean.getList(), MainActivity.this, R.layout.product_listitem);
                recy.setAdapter(productListAdapter);
                if (homeBean.getFlag() == 1) {
                    lin.setVisibility(View.VISIBLE);
                } else {
                    lin.setVisibility(View.GONE);
                }

                fastClickChecked(lin, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String money = homeBean.getNewOrderMoney();
                        String replace1 = money.replace(",", "");
//                        String replace = replace1.replace(".", "");
                        //??????
                        Double Interest = Double.parseDouble(replace1) * Double.parseDouble(homeBean.getNewOrderDay()) * Double.parseDouble(homeBean.getPoints2());
                        //????????????
                        Double should = Interest + Double.parseDouble(replace1) + Double.parseDouble(replace1) * Double.parseDouble(homeBean.getPoints3());
                        //?????????
                        Double servicecharge = Double.parseDouble(replace1) * Double.parseDouble(homeBean.getPoints3());

                        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
                        decimalFormat.format(Double.parseDouble(replace1.replace(",", "")));
                        showLoanSuccessDialog(homeBean.getNewOrderMoney(), homeBean.getNewOrderDay(), Interest, servicecharge, should, 0);
                    }
                });
                productListAdapter.setAdapterOnItemClickListener(new BaseAdapter.AdapterOnItemClickListener() {
                    @Override
                    public void OnItemClick(int postion) {
                        if (isComplete1 == 1 && isComplete2 == 1 && isComplete3 == 1 && isComplete4 == 1) {
                            MakeSureDialog dialog = new MakeSureDialog();
                            String money = homeBean.getList().get(postion).getMoney();
                            String replace = money.replace(",", "");
                            //??????
                            Double Interest = Double.parseDouble(replace) * Double.parseDouble(homeBean.getList().get(postion).getDay()) * Double.parseDouble(homeBean.getPoints2());
                            //????????????
                            Double should = Interest + Double.parseDouble(replace) + Double.parseDouble(replace) * Double.parseDouble(homeBean.getPoints3());
                            //?????????
                            Double servicecharge = Double.parseDouble(replace) * Double.parseDouble(homeBean.getPoints3());
                            DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
                            decimalFormat.format(Double.parseDouble(money.replace(",", "")));
                            dialog.setContent(getString(R.string.a38) + homeBean.getList().get(postion).getMoney() + " INR" + "\n" +
                                    getString(R.string.a40) + Interest + " INR" + "\n" +
                                    getString(R.string.a39) + homeBean.getList().get(postion).getDay() + " day" + "\n\n" +
                                    getString(R.string.a96)
                            );
                            dialog.setDialogClickListener(new MakeSureDialog.onDialogClickListener() {
                                @Override
                                public void onSureClick() {
                                    initLoan(homeBean.getList().get(postion).getMoney(), homeBean.getList().get(postion).getDay(), Interest, servicecharge, should);
                                }

                                @Override
                                public void onCancelClick() {
                                    //?????????????????????
                                    dialog.dismiss();
                                    initHomePage();
                                }
                            });
                            dialog.show(getSupportFragmentManager(), "OK!");
                        } else {
                            Intent intent = new Intent(MainActivity.this, VerifyMessageActivity.class);
                            //????????????????????????  1?????? 0?????????
                            intent.putExtra("isOwnInFo", isComplete1);
                            //?????????????????????????????????  1?????? 0?????????
                            intent.putExtra("isUrgen", isComplete2);
                            //????????????????????????  1?????? 0?????????
                            intent.putExtra("isWork", isComplete3);
                            //????????????????????????  1?????? 0?????????
                            intent.putExtra("isBank", isComplete4);
                            startActivity(intent);
                        }
                    }
                });


            }
        });
    }

    private void initLoan(String money, String day, Double interest, Double servicecharge, Double should) {
        startLoading(MainActivity.this);
        Log.e(TAG, "initLoan: " + jsonbody(new LoanBody(String.valueOf(SPUtils.get(MainActivity.this, AppConstant.islog, "")),
                parseStrToMd5L32(SPUtils.get(MainActivity.this, AppConstant.islog, "") + AppConstant.endconfig), money, day)));
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.SubmitLoanInformation)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new LoanBody(String.valueOf(SPUtils.get(MainActivity.this, AppConstant.islog, "")),
                        parseStrToMd5L32(SPUtils.get(MainActivity.this, AppConstant.islog, "") + AppConstant.endconfig), money, day)))
                .build().enqueue(new LoanCallBack<NullBean>() {
            @Override
            public void error(String error, int code) {
                if (code == -2) {
                    ToastUtil.showToast(MainActivity.this, getString(R.string.a97));
                }
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
                ToastUtil.showToast(MainActivity.this, getString(R.string.a98));
                showLoanSuccessDialog(money, day, interest, servicecharge, should, 1);
                initHomePage();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showLoanSuccessDialog(String money, String day, Double interest, Double servicecharge, Double should, int flag) {
        Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_loansuccess, null);
        TextView tv_titleLeft = inflate.findViewById(R.id.title_left);
        ImageView iv_dingdan = inflate.findViewById(R.id.iv_dingdan);
        TextView tv_tixing = inflate.findViewById(R.id.tv_tixing);
        TextView tv_ApplyMoney = inflate.findViewById(R.id.ApplyMoney);
        TextView tv_theterm = inflate.findViewById(R.id.theterm);
        TextView tv_Interest = inflate.findViewById(R.id.Interest);
        TextView tv_servicecharge = inflate.findViewById(R.id.servicecharge);
        TextView tv_should = inflate.findViewById(R.id.should);
        LinearLayout lin_repayment = inflate.findViewById(R.id.repayment);
        Button btn_Home = inflate.findViewById(R.id.btn_home);
        Button btn_Details = inflate.findViewById(R.id.btn_details);

        if (flag == 0) {
            iv_dingdan.setBackgroundResource(R.drawable.ic_repayment);
            tv_tixing.setText(getString(R.string.a99));
        } else {

            iv_dingdan.setBackgroundResource(R.drawable.ic_audit);
            tv_tixing.setText(getString(R.string.a100));
        }
//        tv_titleLeft.setVisibility(View.VISIBLE);
//        tv_titleLeft.setBackgroundResource(R.drawable.ic_back);
        tv_ApplyMoney.setText(money + "INR");
        tv_theterm.setText(day + "day");
        tv_Interest.setText(interest + "INR");
        tv_servicecharge.setText(servicecharge + "INR");
        tv_should.setText(should + "INR");

        tv_titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        fastClickChecked(lin_repayment, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (iscode == 3) {
                    Intent intent = new Intent(MainActivity.this, UploadVideoActivity.class);
                    intent.putExtra("say", say);
                    startActivity(intent);
                } else {
                    if (isLoComeToAccount == 1) {
                        startActivity(new Intent(MainActivity.this, RepaymentActivity.class));
                    } else {
                        ToastUtil.showToast(MainActivity.this, getString(R.string.a7));
                    }
                }
            }
        });

        fastClickChecked(btn_Home, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                initHomePage();
            }
        });

        fastClickChecked(btn_Details, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyLoanActivity.class));
            }
        });

        //??????????????????Dialog
        dialog.setContentView(inflate);
        //????????????Activity???????????????
        Window dialogWindow = dialog.getWindow();
        //??????Dialog?????????????????????
        dialogWindow.setGravity(Gravity.BOTTOM);
        //??????Dialog??????
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //?????????????????????
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//??????Dialog?????????????????????
        //????????????????????????
        dialogWindow.setAttributes(lp);
        dialog.show();//???????????????
    }

    @Override
    protected void initClick() {
        fastClickChecked(titleRight, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
        fastClickChecked(lin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + "????????????");
            }
        });
    }

    public void show() {
        Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //????????????????????????
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        //???????????????
        TextView id = inflate.findViewById(R.id.tv_IDInfo);
        TextView myloan = inflate.findViewById(R.id.tv_MyLoan);
        TextView urget = inflate.findViewById(R.id.tv_Emergency_Contact);
        TextView edit = inflate.findViewById(R.id.tv_Edit);
        fastClickChecked(id, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OwnInFoActivity.class));
                dialog.dismiss();
            }
        });
        fastClickChecked(myloan, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MyLoanActivity.class));
                dialog.dismiss();
            }
        });
        fastClickChecked(urget, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomerServiceActivity.class));
                dialog.dismiss();
            }
        });
        fastClickChecked(edit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initHomePage();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                dialog.dismiss();
                SPUtils.clear(MainActivity.this);
                ToastUtil.showToast(MainActivity.this, getString(R.string.a14));
            }
        });

        //??????????????????Dialog
        dialog.setContentView(inflate);
        //????????????Activity???????????????
        Window dialogWindow = dialog.getWindow();
        //??????Dialog?????????????????????
        dialogWindow.setGravity(Gravity.BOTTOM);
        //??????Dialog??????
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //?????????????????????
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//??????Dialog?????????????????????
        //????????????????????????
        dialogWindow.setAttributes(lp);
        dialog.show();//???????????????
    }

    @NonNull
    private List<ImageBannerEntry> getImageBannerEntries() {
//        new HttpClient.Buidler()
//                .setBaseUrl(ApiConstant.BASEURL)
//                .setApiUrl(ApiConstant.HomeBanner)
//                .get()
//                .build().enqueue(new LoanCallBack<HomeBannerBean>() {
//            @Override
//            public void error(String error, int code) {
//                Log.e(TAG, "error: " + error + code);
//            }
//
//            @Override
//            public HomeBannerBean convert(JsonElement result) {
//                return JsonUtils.jsonToClass(result, HomeBannerBean.class);
//            }
//
//            @Override
//            public void onSucess(HomeBannerBean homeBannerBean) {
//                pic1 = homeBannerBean.getPic1();
//                pic2 = homeBannerBean.getPic2();
//                pic3 = homeBannerBean.getPic3();
//                Log.e(TAG, "onSucess: " + pic1 + "\n" + pic2 + "\n" + pic3);
//            }
//        });
        List<ImageBannerEntry> items = new ArrayList<>();
        items.add(new ImageBannerEntry("", "", "https://indv-cash.oss-ap-southeast-1.aliyuncs.com/app2banner/ban1.png"));
        items.add(new ImageBannerEntry("", "", "https://indv-cash.oss-ap-southeast-1.aliyuncs.com/app2banner/ban2.png"));
        items.add(new ImageBannerEntry("", "", "https://indv-cash.oss-ap-southeast-1.aliyuncs.com/app2banner/ban3.png"));
        return items;
    }


//    private void initUploadData() {
////        ?????????
//        List<ContactsBody.DbBean> dbBeans = new ArrayList<>();
//        ContentResolver cr = getContentResolver();
//        Cursor cursor = cr.query(phoneUri, new String[]{NUM, NAME}, null, null, null);
//        while (cursor.moveToNext()) {
//            ContactsBody.DbBean dbBean = new ContactsBody.DbBean(cursor.getString(cursor.getColumnIndex(NAME)), cursor.getString(cursor.getColumnIndex(NUM)));
//            dbBeans.add(dbBean);
//        }
//
//        /**
//         * String number = cur.getString(cur.getColumnIndex("??????????????????")); //????????????
//         *
//         *  sms???????????????
//         *   _id?????????????????????100
//         *   thread_id????????????????????????100???????????????????????????????????????????????????????????????
//         *   address???????????????????????????????????????+8613811810000
//         *   person??????????????????????????????????????????????????????????????????????????????null
//         *   date????????????long?????????1256539465022??????????????????????????????????????????
//         *   protocol?????????0SMS_RPOTO?????????1MMS_PROTO??????
//         *   read???????????????0?????????1??????
//         *   status???????????????-1?????????0complete,64pending,128failed
//         *   type???????????????1??????????????????2????????????
//         *   body?????????????????????
//         *   service_center???????????????????????????????????????+8613800755500
//         *
//         *
//         *  private String type;//????????????
//         *  private String phone;//????????????
//         *  private String person;//??????
//         *  private String content;//????????????
//         *  private String sms_time;//????????????
//         */
//
//        //??????????????????
//        Uri uri = Uri.parse("content://sms/");   //??????content://sms/??????URI??????SMS?????????
//        ContentResolver resolver = getContentResolver();
//        Cursor cursor1 = resolver.query(uri, new String[]{"type", "address", "person", "body", "date"}, null, null, null);
//        List<ContactsBody.SmsBean> smsBeans = new ArrayList<>();
//        if (cursor1 != null && cursor1.getCount() > 0) {
//            int type;
//            String phone;
//            String person;
//            String content;
//            int sms_time;
//            while (cursor1.moveToNext()) {
//                type = cursor1.getInt(0);
//                phone = cursor1.getString(1);
//                person = cursor1.getString(2);
//                content = cursor1.getString(3);
//                sms_time = cursor1.getInt(4);
//                ContactsBody.SmsBean smsBean = new ContactsBody.SmsBean(String.valueOf(type), phone, person, content, String.valueOf(sms_time));
//                smsBeans.add(smsBean);
//                Log.e("zhangs", "type=" + type + " phone=" + phone + "\n person=" + person + "\n content=" + content + " sms_time=" + sms_time);
//            }
//        }
//
//        //        ????????????
//
//        /**
//         * private String dttype;//dttype   DIAL-?????????DIALED-??????
//         *         private String name;//??????
//         *         private String number;//?????????
//         *         private String callDuration;//????????????
//         *         private String callDateStr;//????????????
//         * */
//        List<ContactsBody.Contacts1Bean> contacts1Beans = new ArrayList<>();
//        // 1.?????????????????????ContentResolver
//        ContentResolver resolver1 = getContentResolver();
//        String[] columns = {CallLog.Calls.CACHED_NAME// ????????????????????????
//                , CallLog.Calls.NUMBER// ???????????????????????????
//                , CallLog.Calls.DATE// ?????????????????????
//                , CallLog.Calls.DURATION// ????????????
//                , CallLog.Calls.TYPE};// ????????????}
//        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//        }
//        // 2.??????ContentResolver???query?????????????????????????????????
//        /**
//         * @param uri ???????????????URI????????????URI???ContentProvider????????????
//         * @param projection ?????????????????????
//         * @param selection sql??????where???????????????
//         * @param selectionArgs ?????????????????????????
//         * @param sortOrder ????????????
//         */
//        Cursor cursor2 = resolver1.query(CallLog.Calls.CONTENT_URI, // ?????????????????????URI
//                columns, null, null, CallLog.Calls.DEFAULT_SORT_ORDER// ???????????????????????????????????????????????????
//        );
//        while (cursor2.moveToNext()) {
//            String name = cursor2.getString(cursor2.getColumnIndex(CallLog.Calls.CACHED_NAME));
//            String number = cursor2.getString(cursor2.getColumnIndex(CallLog.Calls.NUMBER));
//
//            long dateLong = cursor2.getLong(cursor2.getColumnIndex(CallLog.Calls.DATE));
//            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateLong));
//            String time = new SimpleDateFormat("HH:mm").format(new Date(dateLong));
//
//            int duration = cursor2.getInt(cursor2.getColumnIndex(CallLog.Calls.DURATION));
//
//            int type = cursor2.getInt(cursor2.getColumnIndex(CallLog.Calls.TYPE));
//
//            String dayCurrent = new SimpleDateFormat("dd").format(new Date());
//            String dayRecord = new SimpleDateFormat("dd").format(new Date(dateLong));
//            String typeString = "";
//
//            ContactsBody.Contacts1Bean contacts1Bean = new ContactsBody.Contacts1Bean(String.valueOf(type), name, number, String.valueOf(duration), date);
//            contacts1Beans.add(contacts1Bean);
//            Log.e(TAG, "initUploadData: " + contacts1Beans.toString());
//        }
//
//        Log.e(TAG, "initUploadData: " + jsonbody(new ContactsBody(String.valueOf(SPUtils.get(MainActivity.this, AppConstant.islog, "")),
//                parseStrToMd5L32(SPUtils.get(MainActivity.this, AppConstant.islog, "") + AppConstant.endconfig), dbBeans, contacts1Beans, smsBeans)));
//        new HttpClient.Buidler()
//                .setBaseUrl(ApiConstant.BASEURL)
//                .setApiUrl(ApiConstant.UploadAddressBookInformation)
//                .post()
//                .setJson(true)
//                .setJsonbody(jsonbody(new ContactsBody(String.valueOf(SPUtils.get(MainActivity.this, AppConstant.islog, "")),
//                        parseStrToMd5L32(SPUtils.get(MainActivity.this, AppConstant.islog, "") + AppConstant.endconfig), dbBeans, contacts1Beans, smsBeans)))
//                .build().enqueue(new LoanCallBack<NullBean>() {
//            @Override
//            public void error(String error, int code) {
//                Log.e(TAG, "error: " + error + code);
//            }
//
//            @Override
//            public NullBean convert(JsonElement result) {
//                return JsonUtils.jsonToClass(result, NullBean.class);
//            }
//
//            @Override
//            public void onSucess(NullBean nullBean) {
//                Log.e(TAG, "onSucess: ok");
//            }
//        });
//    }


}