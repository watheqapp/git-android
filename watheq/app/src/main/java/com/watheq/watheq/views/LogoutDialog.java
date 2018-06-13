package com.watheq.watheq.views;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.watheq.watheq.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mahmoud.diab on 3/16/2018.
 */

public class LogoutDialog extends Dialog implements
        View.OnClickListener {

    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.ok)
    TextView ok;
    private OnOkClicked onOkClicked;

    public LogoutDialog(Activity a, OnOkClicked onOkClicked) {
        super(a);
        // TODO Auto-generated constructor stub
        this.onOkClicked = onOkClicked;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.logout_dialog);
        ButterKnife.bind(this);
        ok.setOnClickListener(this);
        edit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit:
                dismiss();
                break;
            case R.id.ok:
                onOkClicked.onOkClicked();
                break;
        }
    }

    public interface OnOkClicked {
        void onOkClicked();
    }
}
