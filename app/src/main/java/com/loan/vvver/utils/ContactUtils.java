package com.loan.vvver.utils;

/*
TODO com.numberone.cashone.utils
TODO Time: 2021/11/24 16:52
TODO Name: zhang
*/

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.loan.vvver.data.bean.DbBean;
import com.loan.vvver.data.body.ContactsBody;

import java.util.ArrayList;
import java.util.List;

public class ContactUtils {
    // 号码
    public final static String NUM = ContactsContract.CommonDataKinds.Phone.NUMBER;
    // 联系人姓名
    public final static String NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
    //联系人提供者的uri
    private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

    //获取所有联系人
    public List<DbBean> getPhone(Context context) {
        List<DbBean> uploadContactsBeans = new ArrayList<>();
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(phoneUri, new String[]{NUM, NAME}, null, null, null);
        while (cursor.moveToNext()) {
            DbBean zhangsBean = new DbBean(cursor.getString(cursor.getColumnIndex(NAME)), cursor.getString(cursor.getColumnIndex(NUM)));
            uploadContactsBeans.add(zhangsBean);
        }
        return uploadContactsBeans;
    }
}
