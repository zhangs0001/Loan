package com.loan.vvver.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.example.mvplibrary.activity.BaseActivity;
import com.loan.vvver.R;
import com.loan.vvver.ui.fragment.BankFragment;
import com.loan.vvver.ui.fragment.OwnInFoFragment;
import com.loan.vvver.ui.fragment.UrgenPeopleFragment;
import com.loan.vvver.ui.fragment.WorkFragment;
import com.loan.vvver.utils.LiveDataBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VerifyMessageActivity extends BaseActivity {

    @BindView(R.id.iv_owninfo)
    ImageView ivOwninfo;
    @BindView(R.id.tv_owninfo)
    TextView tvOwninfo;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.iv_urgen)
    ImageView ivUrgen;
    @BindView(R.id.tv_urgrn)
    TextView tvUrgrn;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.iv_work)
    ImageView ivWork;
    @BindView(R.id.tv_work)
    TextView tvWork;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.iv_bank)
    ImageView ivBank;
    @BindView(R.id.tv_bank)
    TextView tvBank;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.flo)
    FrameLayout flo;
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_right)
    TextView titleRight;
    private OwnInFoFragment ownInFoFragment;
    private UrgenPeopleFragment urgenPeopleFragment;
    private WorkFragment workFragment;
    private BankFragment bankFragment;
    private FragmentTransaction transaction;
    private FragmentTransaction transaction0;
    private FragmentTransaction transaction1;
    private FragmentTransaction transaction2;
    private FragmentTransaction transaction3;
    private FragmentManager manager;

    @Override
    protected int createLayoutId() {
        return R.layout.activity_verify_message;
    }

    @Override
    protected void initViewAndData() {
        title.setVisibility(View.VISIBLE);
        titleLeft.setVisibility(View.VISIBLE);
        titleRight.setVisibility(View.VISIBLE);
        titleLeft.setBackgroundResource(R.drawable.ic_back);
        title.setText(getString(R.string.a104));

        Intent intent = getIntent();
        //身份信息是否完善  1完善 0未完善
        int isOwnInFo = intent.getIntExtra("isOwnInFo", 0);
        //紧急联系人信息是否完善  1完善 0未完善
        int isUrgen = intent.getIntExtra("isUrgen", 0);
        //工作信息是否完善  1完善 0未完善
        int isWork = intent.getIntExtra("isWork", 0);
        //银行信息是否完善  1完善 0未完善
        int isBank = intent.getIntExtra("isBank", 0);
        manager = getSupportFragmentManager();
        ownInFoFragment = new OwnInFoFragment();
        urgenPeopleFragment = new UrgenPeopleFragment();
        workFragment = new WorkFragment();
        bankFragment = new BankFragment();
        manager.beginTransaction()
                .add(R.id.flo, ownInFoFragment)
                .add(R.id.flo, urgenPeopleFragment)
                .add(R.id.flo, workFragment)
                .add(R.id.flo, bankFragment)
                .show(ownInFoFragment)
                .hide(urgenPeopleFragment)
                .hide(workFragment)
                .hide(bankFragment)
                .commit();
        transaction = manager.beginTransaction();
        if (isOwnInFo == 0) {
            tabstate1();
        } else if (isUrgen == 0) {
            tabstate2();
        } else if (isWork == 0) {
            tabstate3();
        } else if (isBank == 0) {
            tabstate4();
        }
        transaction.commit();
        LiveDataBus.get().with("tabstate", Integer.class).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer tabstate) {
                transaction1 = manager.beginTransaction();
                if (tabstate == 20) {
                    ivOwninfo.setBackgroundResource(R.drawable.ic_owninfo2);
                    tvOwninfo.setTextColor(Color.parseColor("#58DEFF"));
                    view1.setBackgroundColor(Color.parseColor("#58DEFF"));

                    ivUrgen.setBackgroundResource(R.drawable.ic_urgencontactperson1);
                    tvUrgrn.setTextColor(Color.parseColor("#8A8A8A"));
                    view2.setBackgroundColor(Color.parseColor("#8A8A8A"));

                    ivWork.setBackgroundResource(R.drawable.ic_work1);
                    tvWork.setTextColor(Color.parseColor("#8A8A8A"));
                    view3.setBackgroundColor(Color.parseColor("#8A8A8A"));

                    ivBank.setBackgroundResource(R.drawable.ic_bank1);
                    tvBank.setTextColor(Color.parseColor("#8A8A8A"));
                    view4.setBackgroundColor(Color.parseColor("#8A8A8A"));
                    transaction1
                            .hide(ownInFoFragment)
                            .show(urgenPeopleFragment)
                            .hide(workFragment)
                            .hide(bankFragment);
                    Log.e(TAG, "onChanged: " + tabstate);
                } else if (tabstate == 30) {
                    ivOwninfo.setBackgroundResource(R.drawable.ic_owninfo2);
                    tvOwninfo.setTextColor(Color.parseColor("#58DEFF"));
                    view1.setBackgroundColor(Color.parseColor("#58DEFF"));

                    ivUrgen.setBackgroundResource(R.drawable.ic_urgentcontactperson2);
                    tvUrgrn.setTextColor(Color.parseColor("#58DEFF"));
                    view2.setBackgroundColor(Color.parseColor("#58DEFF"));

                    ivWork.setBackgroundResource(R.drawable.ic_work1);
                    tvWork.setTextColor(Color.parseColor("#8A8A8A"));
                    view3.setBackgroundColor(Color.parseColor("#8A8A8A"));

                    ivBank.setBackgroundResource(R.drawable.ic_bank1);
                    tvBank.setTextColor(Color.parseColor("#8A8A8A"));
                    view4.setBackgroundColor(Color.parseColor("#8A8A8A"));
                    transaction1
                            .hide(ownInFoFragment)
                            .hide(urgenPeopleFragment)
                            .show(workFragment)
                            .hide(bankFragment);
                    Log.e(TAG, "onChanged: " + tabstate);
                } else if (tabstate == 40) {
                    ivOwninfo.setBackgroundResource(R.drawable.ic_owninfo2);
                    tvOwninfo.setTextColor(Color.parseColor("#58DEFF"));
                    view1.setBackgroundColor(Color.parseColor("#58DEFF"));

                    ivUrgen.setBackgroundResource(R.drawable.ic_urgentcontactperson2);
                    tvUrgrn.setTextColor(Color.parseColor("#58DEFF"));
                    view2.setBackgroundColor(Color.parseColor("#58DEFF"));

                    ivWork.setBackgroundResource(R.drawable.ic_work2);
                    tvWork.setTextColor(Color.parseColor("#58DEFF"));
                    view3.setBackgroundColor(Color.parseColor("#58DEFF"));

                    ivBank.setBackgroundResource(R.drawable.ic_bank1);
                    tvBank.setTextColor(Color.parseColor("#8A8A8A"));
                    view4.setBackgroundColor(Color.parseColor("#8A8A8A"));
                    transaction1
                            .hide(ownInFoFragment)
                            .hide(urgenPeopleFragment)
                            .hide(workFragment)
                            .show(bankFragment);
                    Log.e(TAG, "onChanged: " + tabstate);
                }
                transaction1.commit();
            }
        });
        LiveDataBus.get().with("closure", Integer.class).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer closure) {
                if (closure == 10) {
                    finish();
                }
            }
        });
    }

    private void tabstate1() {
        ivOwninfo.setBackgroundResource(R.drawable.ic_owninfo1);
        tvOwninfo.setTextColor(Color.parseColor("#8A8A8A"));
        view1.setBackgroundColor(Color.parseColor("#8A8A8A"));

        ivUrgen.setBackgroundResource(R.drawable.ic_urgencontactperson1);
        tvUrgrn.setTextColor(Color.parseColor("#8A8A8A"));
        view2.setBackgroundColor(Color.parseColor("#8A8A8A"));

        ivWork.setBackgroundResource(R.drawable.ic_work1);
        tvWork.setTextColor(Color.parseColor("#8A8A8A"));
        view3.setBackgroundColor(Color.parseColor("#8A8A8A"));

        ivBank.setBackgroundResource(R.drawable.ic_bank1);
        tvBank.setTextColor(Color.parseColor("#8A8A8A"));
        view4.setBackgroundColor(Color.parseColor("#8A8A8A"));

        transaction
                .show(ownInFoFragment)
                .hide(urgenPeopleFragment)
                .hide(workFragment)
                .hide(bankFragment);

    }

    private void tabstate2() {
        ivOwninfo.setBackgroundResource(R.drawable.ic_owninfo2);
        tvOwninfo.setTextColor(Color.parseColor("#58DEFF"));
        view1.setBackgroundColor(Color.parseColor("#58DEFF"));

        ivUrgen.setBackgroundResource(R.drawable.ic_urgencontactperson1);
        tvUrgrn.setTextColor(Color.parseColor("#8A8A8A"));
        view2.setBackgroundColor(Color.parseColor("#8A8A8A"));

        ivWork.setBackgroundResource(R.drawable.ic_work1);
        tvWork.setTextColor(Color.parseColor("#8A8A8A"));
        view3.setBackgroundColor(Color.parseColor("#8A8A8A"));

        ivBank.setBackgroundResource(R.drawable.ic_bank1);
        tvBank.setTextColor(Color.parseColor("#8A8A8A"));
        view4.setBackgroundColor(Color.parseColor("#8A8A8A"));
        transaction
                .hide(ownInFoFragment)
                .show(urgenPeopleFragment)
                .hide(workFragment)
                .hide(bankFragment);
    }

    private void tabstate3() {
        ivOwninfo.setBackgroundResource(R.drawable.ic_owninfo2);
        tvOwninfo.setTextColor(Color.parseColor("#58DEFF"));
        view1.setBackgroundColor(Color.parseColor("#58DEFF"));

        ivUrgen.setBackgroundResource(R.drawable.ic_urgentcontactperson2);
        tvUrgrn.setTextColor(Color.parseColor("#58DEFF"));
        view2.setBackgroundColor(Color.parseColor("#58DEFF"));

        ivWork.setBackgroundResource(R.drawable.ic_work1);
        tvWork.setTextColor(Color.parseColor("#8A8A8A"));
        view3.setBackgroundColor(Color.parseColor("#8A8A8A"));

        ivBank.setBackgroundResource(R.drawable.ic_bank1);
        tvBank.setTextColor(Color.parseColor("#8A8A8A"));
        view4.setBackgroundColor(Color.parseColor("#8A8A8A"));
        transaction
                .hide(ownInFoFragment)
                .hide(urgenPeopleFragment)
                .show(workFragment)
                .hide(bankFragment);
    }

    private void tabstate4() {
        ivOwninfo.setBackgroundResource(R.drawable.ic_owninfo2);
        tvOwninfo.setTextColor(Color.parseColor("#58DEFF"));
        view1.setBackgroundColor(Color.parseColor("#58DEFF"));

        ivUrgen.setBackgroundResource(R.drawable.ic_urgentcontactperson2);
        tvUrgrn.setTextColor(Color.parseColor("#58DEFF"));
        view2.setBackgroundColor(Color.parseColor("#58DEFF"));

        ivWork.setBackgroundResource(R.drawable.ic_work2);
        tvWork.setTextColor(Color.parseColor("#58DEFF"));
        view3.setBackgroundColor(Color.parseColor("#58DEFF"));

        ivBank.setBackgroundResource(R.drawable.ic_bank1);
        tvBank.setTextColor(Color.parseColor("#8A8A8A"));
        view4.setBackgroundColor(Color.parseColor("#8A8A8A"));
        transaction
                .hide(ownInFoFragment)
                .hide(urgenPeopleFragment)
                .hide(workFragment)
                .show(bankFragment);
    }

    @Override
    protected void initClick() {
        fastClickChecked(titleLeft, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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