package com.watheq.watheq.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spanned;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.watheq.watheq.R;
import com.watheq.watheq.utils.Logger;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

import static android.text.TextUtils.isEmpty;

/**
 * Created by mahmoud.diab on 11/13/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {


    private double lat, lng;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(myView());
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        clean();
        super.onDestroy();
    }

    public void makeToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void makeToast(int msgRes) {
        Toast.makeText(this, getString(msgRes), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public abstract void clean();

    public abstract int myView();

    public void hideKeyPad() {
        try {
            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            Logger.e(e);
        }
    }

    public void showSnackBar(int stringResId) {
        final String message = getString(stringResId);
        showSnackBarNotification(message);
    }


    public void showSnackBar(String message) {
        showSnackBarNotification(message);
    }


    public void showSnackBarNotification(Spanned message) {
        if (isEmpty(message)) return;
        final View mRootView = findViewById(android.R.id.content);
        if (mRootView == null) return;
        Snackbar mSnackBar = Snackbar.make(mRootView, message, Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout sbView = (Snackbar.SnackbarLayout) mSnackBar.getView();
        sbView.setBackgroundResource(R.color.snakbar_background);
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        if (textView == null) return;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        CalligraphyUtils.applyFontToTextView(this, textView, getString(R.string.font_bold));
        textView.setText(message);
        mSnackBar.show();

    }

    public void showSnackBarNotification(String message) {
        if (isEmpty(message)) return;
        final View mRootView = findViewById(android.R.id.content);
        if (mRootView == null) return;
        Snackbar mSnackBar = Snackbar.make(mRootView, message, Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout sbView = (Snackbar.SnackbarLayout) mSnackBar.getView();
        sbView.setBackgroundResource(R.color.snakbar_background);
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        if (textView == null) return;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        CalligraphyUtils.applyFontToTextView(this, textView, getString(R.string.font_bold));
        textView.setText(message);
        mSnackBar.show();
    }

    public void showToastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
