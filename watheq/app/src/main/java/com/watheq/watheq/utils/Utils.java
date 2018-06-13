package com.watheq.watheq.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.watheq.watheq.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by f22labs on 07/03/17.
 */

public class Utils {


    private Utils() {

    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }

    public static final void showToast(Context context, String message) {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    public static final String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }


    public static final String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        return pInfo.versionName;
    }


    public static void setButtonBackgroundColor(Context context, Button button, int color) {
        button.setBackgroundColor(ContextCompat.getColor(context, color));
    }


    public static void setButtonBackgroundColor(Context context, TextView textView, int color) {
        textView.setBackgroundColor(ContextCompat.getColor(context, color));
    }


    public static Drawable setDrawableSelector(Context context, int normal, int selected) {

        Drawable statePressed = ContextCompat.getDrawable(context, selected);

        Drawable stateNormal = ContextCompat.getDrawable(context, normal);
        StateListDrawable drawable = new StateListDrawable();

        drawable.addState(new int[]{android.R.attr.state_selected},
                statePressed);
        drawable.addState(new int[]{android.R.attr.state_enabled},
                stateNormal);

        if (statePressed != null) {
            statePressed.setColorFilter(new
                    PorterDuffColorFilter(ContextCompat.getColor(context, R.color.united_blue), PorterDuff.Mode.SRC_IN));
        }

        return drawable;
    }


    public static StateListDrawable selectorRadioImage(Drawable normal, Drawable pressed) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_checked}, pressed);
        states.addState(new int[]{}, normal);
        return states;
    }

    public static StateListDrawable selectorRadioButton(int normal, int pressed) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_checked}, new ColorDrawable(pressed));
        states.addState(new int[]{}, new ColorDrawable(normal));
        return states;
    }

    public static ColorStateList selectorRadioText(int normal, int pressed) {
        return new ColorStateList(new int[][]{new int[]{android.R.attr.state_checked}, new int[]{}}, new int[]{pressed, normal});

    }


    public static StateListDrawable selectorBackgroundColor(int normal, int pressed) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(pressed));
        states.addState(new int[]{}, new ColorDrawable(normal));
        return states;
    }

    public static StateListDrawable selectorBackgroundDrawable(Drawable normal, Drawable pressed) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed}, pressed);
        states.addState(new int[]{}, normal);
        return states;
    }

    public static ColorStateList selectorText(int normal, int pressed) {
        return new ColorStateList(new int[][]{new int[]{android.R.attr.state_pressed}, new int[]{}}, new int[]{pressed, normal});

    }

    public static String timerTextFormat(long value) {
        return String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(value) % 60,
                TimeUnit.MILLISECONDS.toSeconds(value) % 60);
    }

    public static SpannableString setUnderLineText(String msg) {
        SpannableString content = new SpannableString(msg);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        return content;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}
