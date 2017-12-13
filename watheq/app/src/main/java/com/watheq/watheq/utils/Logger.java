package com.watheq.watheq.utils;

import android.util.Log;

import com.watheq.watheq.BuildConfig;

import static android.text.TextUtils.isEmpty;

/**
 * Created by mohamed.ibrahim on 8/3/2016.
 */

public final class Logger {
    private Logger() {
        /**
         * no constructor
         */
    }


    private static final String TAG = "watheq";
    private static final Boolean _D = BuildConfig.DEBUG;


    public static void d(String message) {
        d(TAG, message);
    }

    public static void d(String tag, String message) {
        if (!_D) return;
        if (isEmpty(tag)) return;
        if (isEmpty(message)) return;

        Log.d(tag, message);
    }


    public static void e(String message) {
        e(TAG, message);
    }

    public static void e(String tag, String message) {
        if (!_D) return;
        if (isEmpty(tag)) return;
        if (isEmpty(message)) return;

        Log.e(tag, message);
    }


    public static void e(Throwable e) {
        if (e == null) return;
        e(e.getMessage());
    }
}
