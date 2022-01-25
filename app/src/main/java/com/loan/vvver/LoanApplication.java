package com.loan.vvver;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;

public class LoanApplication extends Application {
    public static LoanApplication mContext;
    private static final String AF_DEV_KEY = "UJWRHUC59reKzKdoZkC2Ee";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        AppsFlyerLib.getInstance().init(AF_DEV_KEY, null, this);
        AppsFlyerLib.getInstance().start(this);
    }

    public static Context getContext() {
        return mContext;
    }

}