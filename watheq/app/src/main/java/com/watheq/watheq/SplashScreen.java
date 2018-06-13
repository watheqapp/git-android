package com.watheq.watheq;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.iid.FirebaseInstanceId;
import com.watheq.watheq.authentication.AuthenticationActivity;
import com.watheq.watheq.authentication.CompleteProfileActivity;
import com.watheq.watheq.utils.App;
import com.watheq.watheq.utils.UserManager;

public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        FirebaseInstanceId.getInstance().getToken();
        App.changeLang(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (UserManager.getInstance().getUser() == null ||
                        UserManager.getInstance().getUserData() == null ||
                        UserManager.getInstance().getUserToken() == null)
                    AuthenticationActivity.start(SplashScreen.this);
                else if (UserManager.getInstance().getUserData().getIsCompleteProfile() == 0)
                    CompleteProfileActivity.start(SplashScreen.this);
                else
                    MainActivity.start(SplashScreen.this);
                finish();
            }
        }, 3000);
    }
}
