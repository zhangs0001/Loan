package com.loan.vvver.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.mvplibrary.activity.BaseActivity;
import com.loan.vvver.R;
import com.loan.vvver.ui.fragment.OffLineFragment;
import com.loan.vvver.ui.fragment.OnLineFragment;
import com.loan.vvver.utils.LiveDataBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepaymentActivity extends BaseActivity {

    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.tv_offline)
    TextView tvOffline;
    @BindView(R.id.tv_on_line)
    TextView tvOnLine;
    @BindView(R.id.flo)
    FrameLayout flo;
    private FragmentManager manager;
    private FragmentTransaction beginTransaction;
    private OffLineFragment offLineFragment;
    private OnLineFragment onLineFragment;

    @Override
    protected int createLayoutId() {
        return R.layout.activity_repayment;
    }

    @Override
    protected void initViewAndData() {
        title.setVisibility(View.VISIBLE);
        titleLeft.setVisibility(View.VISIBLE);
        titleRight.setVisibility(View.VISIBLE);
        titleLeft.setBackgroundResource(R.drawable.ic_back);
        title.setText(getString(R.string.a101));
        manager = getSupportFragmentManager();
        offLineFragment = new OffLineFragment();
        onLineFragment = new OnLineFragment();
        beginTransaction = manager.beginTransaction();
        beginTransaction.add(R.id.flo, offLineFragment)
                .add(R.id.flo, onLineFragment)
                .hide(offLineFragment)
                .show(onLineFragment)
                .commit();

        LiveDataBus.get().with("gb", Integer.class).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer gb) {
                if (gb == 100) {
                    finish();
                }
            }
        });
    }

    @Override
    protected void initClick() {
        fastClickChecked(titleLeft, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fastClickChecked(tvOffline, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction()
                        .hide(offLineFragment)
                        .show(onLineFragment)
                        .commit();
                tvOffline.setTextColor(Color.parseColor("#03DAC5"));
                tvOnLine.setTextColor(Color.parseColor("#8A8A8A"));
            }
        });
        fastClickChecked(tvOnLine, new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                manager.beginTransaction()
                        .show(offLineFragment)
                        .hide(onLineFragment)
                        .commit();

                tvOnLine.setTextColor(Color.parseColor("#03DAC5"));
                tvOffline.setTextColor(Color.parseColor("#8A8A8A"));
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //刷新主页状态接口
        LiveDataBus.get().with("refreshhomepage").postValue(10);
    }

}