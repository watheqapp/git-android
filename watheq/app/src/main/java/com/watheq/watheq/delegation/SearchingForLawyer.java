package com.watheq.watheq.delegation;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.watheq.watheq.R;
import com.watheq.watheq.authentication.AuthViewModel;
import com.watheq.watheq.base.BaseActivity;
import com.watheq.watheq.lawerList.LawerListActivity;
import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.OrderLawyerResponse;
import com.watheq.watheq.utils.UserManager;
import com.watheq.watheq.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchingForLawyer extends BaseActivity {


    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.timer)
    TextView timer;
    @BindView(R.id.first)
    ImageView firstImage;
    @BindView(R.id.third)
    ImageView thirdImage;
    @BindView(R.id.center)
    ImageView centerImage;
    @BindView(R.id.order_id)
    TextView orderId;
    @BindView(R.id.choose_lawyer)
    TextView chooseLawyer;
    @BindView(R.id.call_me)
    TextView callMe;
    @BindView(R.id.cancel)
    TextView cancel;
    CountDownTimer cdt;

    void setIntervalTime(long timeToStart) {
        cdt = new CountDownTimer(timeToStart, 1000) {// def 2 mins
            @Override
            public void onTick(long millisUntilFinished) {
                displayTimerValue(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                cancel.setVisibility(View.GONE);
                displayTimerValue(0L);
                chooseLawyer.setVisibility(View.VISIBLE);
                callMe.setVisibility(View.VISIBLE);
                chooseLawyer.setEnabled(true);
                callMe.setEnabled(true);
                chooseLawyer.setTextColor(ContextCompat.getColor(SearchingForLawyer.this, R.color.united_blue));
                callMe.setTextColor(ContextCompat.getColor(SearchingForLawyer.this, R.color.united_blue));
            }
        };

        cdt.start();
    }

    private void displayTimerValue(long value) {
        timer.setText(Utils.timerTextFormat(value));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbarTitle.setText(getString(R.string.search));
        setIntervalTime(120000);
        orderId.setText(getString(R.string.request_num) + " " + getIntent().getIntExtra("id", 0));
        chooseLawyer.setEnabled(false);
        callMe.setEnabled(false);
        chooseLawyer.setTextColor(ContextCompat.getColor(this, R.color.dark_gray));
        callMe.setTextColor(ContextCompat.getColor(this, R.color.dark_gray));
    }

    @OnClick(R.id.cancel)
    void onCancelClicked() {
        MoveToSupportViewModel moveToSupportViewModel = ViewModelProviders.of(this).get(MoveToSupportViewModel.class);
        moveToSupportViewModel.CancelOrder(UserManager.getInstance().getUserToken()
                , getIntent().getIntExtra("id", 0)).observe(this, cancelOrder);
    }

    @Override
    public void clean() {
        cdt.cancel();
    }

    @Override
    public int myView() {
        return R.layout.activity_searching_for_lawyer;
    }

    @OnClick(R.id.choose_lawyer)
    void onChooseLawyerClicked() {
        Intent intent = new Intent(SearchingForLawyer.this, LawerListActivity.class);
        intent.putExtra("orderId", getIntent().getIntExtra("id", 0));
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.call_me)
    void onCallMeClicked() {
        MoveToSupportViewModel moveToSupportViewModel = ViewModelProviders.of(this).get(MoveToSupportViewModel.class);
        moveToSupportViewModel.registerDeviceToken(UserManager.getInstance().getUserToken()
                , getIntent().getIntExtra("id", 0)).observe(this, getSupport);
    }

    private final Observer<BaseModel> getSupport = new Observer<BaseModel>() {
        @Override
        public void onChanged(@Nullable BaseModel baseModel) {
            finish();
        }
    };

    private final Observer<OrderLawyerResponse> cancelOrder = new Observer<OrderLawyerResponse>() {
        @Override
        public void onChanged(@Nullable OrderLawyerResponse baseModel) {

            finish();
        }
    };

    @Override
    public void onBackPressed() {
        if (chooseLawyer.isEnabled()) {
            super.onBackPressed();
        }
    }

}
