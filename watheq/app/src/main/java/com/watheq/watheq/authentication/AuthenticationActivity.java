package com.watheq.watheq.authentication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.watheq.watheq.MainActivity;
import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseActivity;
import com.watheq.watheq.model.LoginBody;
import com.watheq.watheq.model.LoginModelResponse;
import com.watheq.watheq.utils.AnimationHelper;
import com.watheq.watheq.utils.UserManager;
import com.watheq.watheq.utils.Utils;
import com.watheq.watheq.utils.Validations;

import java.util.concurrent.TimeUnit;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

public class AuthenticationActivity extends BaseActivity {

    private static final String TAG = "AuthenticationActivity";
    @BindView(R.id.aAuth_confirm_btn)
    CircularProgressButton confirmBtn;
    @BindView(R.id.aAuth_verific_code_edt)
    EditText verificationEdt;
    @BindView(R.id.aAuth_timer)
    TextView timer;
    @BindView(R.id.aAuth_phone_num_edt)
    EditText phoneNumEdt;
    private AuthViewModel authViewModel;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks calls;
    private FirebaseAuth firebaseAuth;
    private boolean isConfirmClick;
    private String verificationId;

    private final Observer<Long> elapsedTimeObserver = new Observer<Long>() {
        @Override
        public void onChanged(final Long newValue) {
            if (newValue != 0L) {
                displayTimerValue(newValue);
                Validations.disableViews(timer);
            } else {
                timer.setText(Utils.setUnderLineText(getString(R.string.logn_scrn_resend)));
                Validations.enableViews(phoneNumEdt, timer);
            }
        }
    };

    private final Observer<LoginModelResponse> getLoginResponse = new Observer<LoginModelResponse>() {
        @Override
        public void onChanged(@Nullable LoginModelResponse loginModel) {
            Log.d(TAG, "onChanged: " + loginModel);
            if (loginModel.getThrowable() == null && loginModel.getCode() == 200) {
                UserManager.getInstance().addUser(loginModel);
                completeProfileOrHome(loginModel.getResponse().getIsCompleteProfile());
            } else if (loginModel.getThrowable() == null) {
                showError(loginModel.getMessage());
            } else {
                showError(getString(R.string.all_no_internet));
            }
        }
    };

    private void completeProfileOrHome(int isCompleteProfile) {
        if (isCompleteProfile == 0) {
            CompleteProfileActivity.start(this, confirmBtn, this);
            confirmBtn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1500);
        } else {
            MainActivity.start(this);
            confirmBtn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1500);
        }
    }

    private void showError(String error) {
        showSnackBarNotification(error);
        confirmBtn.revertAnimation();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        authViewModel.getIntervalTime().observe(this, elapsedTimeObserver);
        authViewModel.getApiResponse().observe(this, getLoginResponse);
        firebaseAuth = FirebaseAuth.getInstance();
        calls = authCallBack();
    }

    @OnClick(R.id.aAuth_confirm_btn)
    void onConfirmClicked() {
        if (!Utils.isNetworkAvailable(this)) {
            showSnackBarNotification(getString(R.string.all_no_internet));
            return;
        }
        if (!Validations.isNotEmpty(phoneNumEdt.getText().toString())) {
            showSnackBarNotification(getString(R.string.logn_scrn_phone_empty));
            return;
        }

        if (!isConfirmClick) {
            confirmBtn.startAnimation();
            isConfirmClick = true;
            verifyPhoneNumber(phoneNumEdt.getText().toString().trim(), phoneNumEdt);

        } else {
            if (!Validations.isNotEmpty(verificationEdt.getText().toString())) {
                showSnackBarNotification(getString(R.string.logn_scrn_verfe_empty));
                return;
            }
            confirmBtn.startAnimation();
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId,
                    verificationEdt.getText().toString());
            signInWithPhoneAuthCredential(phoneAuthCredential);

        }
    }

    @OnClick(R.id.aAuth_timer)
    void onResendClicked() {
        if (!Utils.isNetworkAvailable(this)) {
            showSnackBarNotification(getString(R.string.all_no_internet));
            return;
        }
        verifyPhoneNumber(phoneNumEdt.getText().toString().trim(), phoneNumEdt);
    }

    @Override
    public void clean() {

    }

    @Override
    public int myView() {
        return R.layout.activity_authentication;
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, AuthenticationActivity.class);
        context.startActivity(starter);
    }

    private void displayTimerValue(long value) {
        timer.setText(Utils.timerTextFormat(value));
    }

    private void verifyPhoneNumber(String number, View view) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+2" + number,
                60, TimeUnit.SECONDS, this, calls);
        Validations.disableViews(view);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks authCallBack() {
        return new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted: " + phoneAuthCredential.getSmsCode());
                verificationEdt.setText(phoneAuthCredential.getSmsCode());
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.d(TAG, "onVerificationFailed: " + e.getMessage());
                confirmBtn.revertAnimation();
                isConfirmClick = false;
                Validations.enableViews(phoneNumEdt);
                if (e.getMessage().contains("network"))
                    showSnackBarNotification(getString(R.string.all_no_internet));
                else if (e.getMessage().contains("format of the phone is incorrect"))
                    showSnackBarNotification(getString(R.string.logn_scrn_incorrect_number));
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);
                Log.d(TAG, "onCodeSent: " + verificationId);
                AuthenticationActivity.this.verificationId = verificationId;
                AnimationHelper.animateFadeIn(AuthenticationActivity.this, verificationEdt);
                AnimationHelper.animateFadeIn(AuthenticationActivity.this, timer);
                authViewModel.setIntervalTime(120000);
                verificationEdt.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        confirmBtn.revertAnimation();
                    }
                }, 1000);
            }
        };
    }

    private void signInWithPhoneAuthCredential(final PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            authViewModel.loginUser(new LoginBody(task.getResult().getUser().getPhoneNumber()));
                        }
                    }


                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "signInWithCredential:failure", e);
                confirmBtn.revertAnimation();
                if (e.getMessage().contains("network"))
                    showSnackBar(getString(R.string.all_no_internet));
                else
                    showSnackBar(getString(R.string.invalid_code));
            }
        });
    }
}