package com.watheq.watheq.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;

import com.watheq.watheq.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.watheq.watheq.delegation.delegationNew.AgencyFragment.COMPANY;
import static com.watheq.watheq.delegation.delegationNew.AgencyFragment.ORGNIZATION;
import static com.watheq.watheq.delegation.delegationNew.AgencyFragment.PERSONALLY;

/**
 * Created by mahmoud.diab on 1/31/2018.
 */

public class CustomDialog extends Dialog implements DialogInterface.OnDismissListener {

    public FilterCallback callback;
    private String SELECTED_STATE = "";
    private String TAG = "";
    @BindView(R.id.personally)
    RadioButton presonally;
    @BindView(R.id.orgnization)
    RadioButton orgnization;
    @BindView(R.id.company)
    RadioButton company;
    private String title;

    public CustomDialog(@NonNull Context context, FilterCallback callback
            , boolean isCreateAgency, ArrayList<String> names, ArrayList<String> desc) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_layout);
        this.callback = callback;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setOnDismissListener(this);
        ButterKnife.bind(this);
        if (getWindow() != null)
            getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded);
        if (isCreateAgency) {
            presonally.setTag("3");
            orgnization.setTag("4");
            company.setTag("5");
        } else {
            presonally.setTag("7");
            orgnization.setTag("8");
            company.setTag("9");
        }
        presonally.setText(String.format("%s : %s", names.get(0), desc.get(0)));
        orgnization.setText(String.format("%s : %s", names.get(1), desc.get(1)));
        company.setText(String.format("%s : %s", names.get(2), desc.get(2)));
    }


    @OnClick({R.id.personally, R.id.orgnization, R.id.company})
    public void onRadioButtonClicked(RadioButton radioButton) {
        // Is the button now checked?
        boolean checked = radioButton.isChecked();

        // Check which radio button was clicked
        switch (radioButton.getId()) {
            case R.id.personally:
                if (checked) {
                    SELECTED_STATE = PERSONALLY;
                    title = radioButton.getText().toString();
                    TAG = radioButton.getTag().toString();
                }
                break;
            case R.id.orgnization:
                if (checked) {
                    SELECTED_STATE = ORGNIZATION;
                    title = radioButton.getText().toString();
                    TAG = radioButton.getTag().toString();
                }
                break;

            case R.id.company:
                if (checked) {
                    SELECTED_STATE = COMPANY;
                    title = radioButton.getText().toString();
                    TAG = radioButton.getTag().toString();
                }
                break;
        }
    }


    @OnClick(R.id.select)
    void onSelectClicked() {
        if (callback != null) callback.onFilterListener(SELECTED_STATE, TAG, title);
        dismiss();

    }


    @OnClick(R.id.cancel)
    void CancelClicked() {
        dismiss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        callback = null;
    }

    public interface FilterCallback {
        void onFilterListener(String state, String tag, String title);
    }

}
