package com.watheq.watheq.utils;

import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.watheq.watheq.R;

/**
 * Created by mahmoud.diab on 11/25/2017.
 */

public final class Validations {
    public static void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    public static void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static boolean isValidUserName(String username, TextView validationView) {

        if (isEmpty(username)) {
            setValidationErrorView(validationView, validationView.getResources().getString(R.string.validation_error_required));
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String value) {
        return TextUtils.isEmpty(value);
    }

    public static boolean isValidEmail(String email, TextView validationView) {

        // validate email
        if (isEmpty(email)) {
            Validations.setValidationErrorView(validationView, validationView.getResources().getString(R.string.validation_error_required));
            return false;
        }

        if (!isValidEmail(email)) {
            Validations.setValidationErrorView(validationView, validationView.getResources().getString(R.string.validation_error_email));
            return false;
        }

        return true;
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value) && value.trim().length() > 0;
    }

    public static void setValidationErrorView(TextView validationView, String errorMessage) {
        if (validationView == null) return;
        if (isEmpty(errorMessage)) return;
        validationView.setVisibility(View.VISIBLE);
        validationView.setText(errorMessage);
    }

    public static void removeValidation(final TextView validationView, EditText view) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                validationView.setVisibility(View.GONE);
            }
        });
    }

    public static void removeValidation(TextView validationView) {
        validationView.setVisibility(View.GONE);
    }
}
