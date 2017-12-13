package com.watheq.watheq.delegation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.model.ClientInfoModel;
import com.watheq.watheq.utils.Validations;
import com.watheq.watheq.views.RecyclerViewEmptySupport;

import butterknife.BindView;
import butterknife.OnClick;


public class ClientInformationFragment extends BaseDelegate {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.civil_registry)
    EditText civilRegistry;

    public ClientInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_client_information;
    }

    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.next_step)
    void onNextStepClicked() {
        if (!Validations.isNotEmpty(name.getText().toString()) || !Validations.isNotEmpty(civilRegistry.getText().toString())) {
            showNotification(getString(R.string.validation_error_required));
            return;
        }

        ClientInfoModel clientInfoModel = new ClientInfoModel();
        clientInfoModel.setName(name.getText().toString());
        clientInfoModel.setCivilRegistry(civilRegistry.getText().toString());

        pushFragment(MainClientFragment.newInstance(clientInfoModel), false);
    }

}
