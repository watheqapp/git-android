package com.watheq.watheq.setting;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseActivity;
import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.BaseModel;
import com.watheq.watheq.model.ReportProblemModel;
import com.watheq.watheq.utils.Errors;
import com.watheq.watheq.utils.UserManager;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

public class ReportProblemActivity extends BaseActivity implements BaseHandlingErrors {

    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.next_step)
    CircularProgressButton nextStep;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.content)
    EditText content;
    private ReportProblemViewModel problemViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toolbarTitle.setText(getString(R.string.call_us));
        problemViewModel = ViewModelProviders.of(this).get(ReportProblemViewModel.class);

    }

    @Override
    public void clean() {

    }

    @Override
    public int myView() {
        return R.layout.activity_report_problem;
    }

    @OnClick(R.id.next_step)
    void Submit() {
        if (TextUtils.isEmpty(title.getText().toString())
                || TextUtils.isEmpty(title.getText().toString())) {
            showSnackBar(getString(R.string.validation_error_required));
            return;
        }

        if (title.getText().toString().length() < 3 ||
                content.getText().toString().length() < 3) {
            showSnackBar(R.string.min_length);
            return;
        }
        ReportProblemModel reportProblemModel = new ReportProblemModel();
        reportProblemModel.setTitle(title.getText().toString());
        reportProblemModel.setContent(content.getText().toString());
        nextStep.startAnimation();
        problemViewModel.reportProblem(UserManager.getInstance().getUserToken(), reportProblemModel
                , this).observe(this, reportProblem);
    }

    private final Observer<BaseModel> reportProblem = new Observer<BaseModel>() {
        @Override
        public void onChanged(@Nullable BaseModel baseModel) {
            nextStep.revertAnimation();
            if (baseModel.getCode() == 200) {
                showSnackBar(R.string.your_request_submited);
            }
        }
    };

    @Override
    public void onResponseFail(Errors msg) {
        showSnackBar(R.string.something_wrong);
    }
}
