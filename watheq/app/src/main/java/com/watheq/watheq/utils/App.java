package com.watheq.watheq.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.google.firebase.database.FirebaseDatabase;
import com.watheq.watheq.R;

import java.util.Locale;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mahmoud.diab on 11/14/2017.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PrefsManager.init(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Paper.init(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/DinNextRegular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    private Activity mCurrentActivity = null;
    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }
    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }
    public static void changeLang(Context context) {
        Locale myLocale;
        if(PrefsManager.getInstance().getLang() != null){
            myLocale = new Locale(PrefsManager.getInstance().getLang());
        }else {
            myLocale = new Locale(Locale.getDefault().getLanguage());
        }
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeLang(this);
    }
}
