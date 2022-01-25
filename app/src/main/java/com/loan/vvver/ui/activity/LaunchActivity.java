package com.loan.vvver.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.mvplibrary.activity.BaseActivity;
import com.loan.vvver.R;
import com.loan.vvver.config.AppConstant;
import com.loan.vvver.utils.SPUtils;

public class LaunchActivity extends BaseActivity {
    private int count = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                if (count > 0) {
                    count--;
                    handler.sendEmptyMessageDelayed(100, 1000);
                } else {
                    if (!String.valueOf(SPUtils.get(LaunchActivity.this, AppConstant.islog, "")).equals("")) {
                        startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(LaunchActivity.this, LoginActivity.class));
                    }
                    finish();
                }
            }
        }
    };

    @Override
    protected int createLayoutId() {
        return R.layout.activity_launch;
    }

    @Override
    protected void initViewAndData() {
        handler.sendEmptyMessageDelayed(100, 1000);
    }

    @Override
    protected void initClick() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        handler.removeCallbacksAndMessages(0);
    }
}