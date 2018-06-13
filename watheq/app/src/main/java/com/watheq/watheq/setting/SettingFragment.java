package com.watheq.watheq.setting;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.thefinestartist.finestwebview.FinestWebView;
import com.watheq.watheq.MainActivity;
import com.watheq.watheq.R;
import com.watheq.watheq.authentication.AuthenticationActivity;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.utils.App;
import com.watheq.watheq.utils.PrefsManager;
import com.watheq.watheq.utils.UserManager;
import com.watheq.watheq.views.LogoutDialog;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.STATE_HIDDEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment implements LogoutDialog.OnOkClicked {

    BottomSheetBehavior bottomSheetBehavior;
    @BindView(R.id.bottom_sheet)
    LinearLayout llBottomSheet;
    @BindView(R.id.shadow_view)
    View shadowView;

    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public int getLayoutResource() {
        return R.layout.fragment_setting;
    }

    @OnClick(R.id.terms)
    void onTermsClicked() {
        new FinestWebView.Builder(getActivity()).show("http://159.89.41.54/watheq/public/terms");//http://159.89.41.54/watheq/public/policy
    }

    @OnClick(R.id.policy)
    void onPolicyClicked() {
        new FinestWebView.Builder(getActivity()).show("http://159.89.41.54/watheq/public/policy");//http://159.89.41.54/watheq/public/policy
    }

    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {

        if ((getActivity()) != null) {
            ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.setting), 0);
        }
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setState(STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == STATE_HIDDEN)
                    shadowView.setVisibility(View.GONE);

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @OnClick(R.id.report_problem)
    void onReportClicked() {
        Intent intent = new Intent(getActivity(), ReportProblemActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.logout)
    void onLogoutClicked() {
        LogoutDialog logoutDialog = new LogoutDialog(getActivity(), this);
        logoutDialog.show();

    }

    @OnClick(R.id.change_lang)
    void onChangeLang() {
        shadowView.setVisibility(View.VISIBLE);
        bottomSheetBehavior.setState(STATE_EXPANDED);
    }

    @OnClick(R.id.shadow_view)
    void onShadowClicked() {
        bottomSheetBehavior.setState(STATE_HIDDEN);
    }

    @OnClick(R.id.arabic)
    void onArabicClicked() {
        bottomSheetBehavior.setState(STATE_HIDDEN);
        shadowView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PrefsManager.getInstance().getLang().equals("ar"))
                    return;
                PrefsManager.getInstance().setLang("ar");
                App.changeLang(getContext());
                if (getActivity() != null) {
                    final Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        }, 200);

    }

    @OnClick(R.id.english)
    void onEnglishClicked() {
        bottomSheetBehavior.setState(STATE_HIDDEN);
        shadowView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PrefsManager.getInstance().getLang().equals("en"))
                    return;
                PrefsManager.getInstance().setLang("en");
                App.changeLang(getContext());
                if (getActivity() != null) {
                    final Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        }, 200);

    }

    @Override
    public void onOkClicked() {
        UserManager.getInstance().logout();
        AuthenticationActivity.start(getContext());
        if (getActivity() != null)
            getActivity().finish();
    }
}
