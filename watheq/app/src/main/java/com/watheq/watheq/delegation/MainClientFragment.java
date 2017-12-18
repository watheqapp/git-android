package com.watheq.watheq.delegation;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.watheq.watheq.MainActivity;
import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.model.ClientInfoModel;
import com.watheq.watheq.utils.Validations;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainClientFragment extends BaseDelegate {


    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.civil_registry)
    EditText civilRegistry;
    private ClientInfoModel clientInfoModel;

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
        if (getActivity() != null)
            ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.auth_for), 100 * 4);
        Bundle bundle = this.getArguments();
        if (bundle != null)
            clientInfoModel = bundle.getParcelable(INFO_CLIENT);
        getParentActivity().setLng(0d);
        getParentActivity().setLat(0d);
    }

    @OnClick(R.id.next_step)
    void onNextStepClicked() {
        if (!Validations.isNotEmpty(name.getText().toString()) || !Validations.isNotEmpty(civilRegistry.getText().toString())) {
            showNotification(getString(R.string.validation_error_required));
            return;
        }
        getParentActivity().hideKeyPad();
        clientInfoModel.setRepresentativeName(name.getText().toString());
        clientInfoModel.setClientNationalID(civilRegistry.getText().toString());
        pushFragment(DeliveryPlaceFragment.newInstance(clientInfoModel), false);
    }

}
