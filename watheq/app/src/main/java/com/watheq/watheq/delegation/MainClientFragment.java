package com.watheq.watheq.delegation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.model.ClientInfoModel;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainClientFragment extends BaseDelegate {


    private static final String INFO_CLIENT = "client_info";

    public MainClientFragment() {
        // Required empty public constructor
    }

    public static MainClientFragment newInstance(ClientInfoModel clientInfoModel) {
        MainClientFragment mainClientFragment = new MainClientFragment();
        Bundle args = new Bundle();
        args.putParcelable(INFO_CLIENT, clientInfoModel);
        mainClientFragment.setArguments(args);
        return mainClientFragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_main_client;
    }

    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.next_step)
    void onNextStepClicked() {

    }

}
