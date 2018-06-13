package com.watheq.watheq.base;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.watheq.watheq.R;

import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

import static android.text.TextUtils.isEmpty;

/**
 * Created by mahmoud.diab on 1/10/2018.
 */

public abstract class BaseChatActivity extends AppCompatActivity {
    protected boolean isLoading;
    protected boolean userInteracted;
    private Snackbar mSnackBar;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void showProgress() {
        isLoading = true;
        displayLoadingState();
    }

    public void hideProgress() {
        isLoading = false;
        displayLoadingState();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Icepick.restoreInstanceState(this, savedInstanceState);
        if (savedInstanceState == null) {
            isLoading = false;
            userInteracted = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayLoadingState();
    }

    protected abstract void displayLoadingState();

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void showSnackBarNotification(String message) {
        if (isEmpty(message)) return;
        final View mRootView = findViewById(android.R.id.content);
        if (mRootView == null) return;
        if (mSnackBar == null)
            mSnackBar = Snackbar.make(mRootView, message, Snackbar.LENGTH_LONG);
        Snackbar.SnackbarLayout sbView = (Snackbar.SnackbarLayout) mSnackBar.getView();
        sbView.setBackgroundResource(R.color.snakbar_background);
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        if (textView == null) return;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        CalligraphyUtils.applyFontToTextView(this, textView, getString(R.string.font_bold));
        textView.setText(message);
        if (!mSnackBar.isShown())
            mSnackBar.show();
    }

}
