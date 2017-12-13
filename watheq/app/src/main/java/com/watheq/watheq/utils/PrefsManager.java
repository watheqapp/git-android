package com.watheq.watheq.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

import static android.text.TextUtils.isEmpty;

/**
 * Created by mohamed.ibrahim on 5/29/2017.
 */

public class PrefsManager {

    private static final String PREFS_NAME = "training_app_prefs";

    private static PrefsManager instance;
    private SharedPreferences preferences;

    private PrefsManager(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }


    public static void init(Context context) {
        if (instance == null) {
            synchronized (PrefsManager.class) {
                instance = new PrefsManager(context.getApplicationContext());
            }
        }
    }

    public static PrefsManager getInstance() {
        return instance;
    }


    private void addString(String key, String value) {
        if (preferences == null) return;
        if (isEmpty(key)) return;
        preferences.edit().putString(key, value).apply();
    }

    private String getString(String key, String defaultVal) {
        if (preferences == null) return defaultVal;
        if (isEmpty(key)) return defaultVal;
        return preferences.getString(key, defaultVal);
    }


    private void addInt(String key, int val) {
        if (preferences == null) return;
        if (isEmpty(key)) return;
        preferences.edit().putInt(key, val).apply();
    }

    private int getInt(String key, int defaultVal) {
        if (preferences == null) return defaultVal;
        if (isEmpty(key)) return defaultVal;
        return preferences.getInt(key, defaultVal);
    }


    private void addBoolean(String key, boolean val) {
        if (preferences == null) return;
        if (isEmpty(key)) return;
        preferences.edit().putBoolean(key, val).apply();
    }

    private boolean getBoolean(String key, boolean defaultVal) {
        if (preferences == null) return defaultVal;
        if (isEmpty(key)) return defaultVal;
        return preferences.getBoolean(key, defaultVal);
    }


    private void addLong(String key, long val) {
        if (preferences == null) return;
        if (isEmpty(key)) return;
        preferences.edit().putLong(key, val).apply();
    }


    private long getLong(String key, long defaultVal) {
        if (preferences == null) return defaultVal;
        if (isEmpty(key)) return defaultVal;
        return preferences.getLong(key, defaultVal);
    }


    //


    public boolean isAppOpenBefore() {
//        return getBoolean("FIRST_TIME_OPEN_APP", false);
        return true;
    }

    public void setAppIsOpenedBefore(boolean b) {
        addBoolean("FIRST_TIME_OPEN_APP", b);

    }

    public void setLang(String lang) {
        addString("APP_LANG", lang);
    }


    public String getLang() {
        return getString("APP_LANG", Locale.getDefault().getLanguage());
    }

    public void setIntervalTime(long timeInterval) {
        addLong("INTERVAL_TIME", timeInterval);
    }

    public long getIntervalTime() {
        return getLong("INTERVAL_TIME", 0);
    }
}
