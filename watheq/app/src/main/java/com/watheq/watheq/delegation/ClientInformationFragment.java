package com.watheq.watheq.delegation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.watheq.watheq.MainActivity;
import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.model.ClientInfoModel;
import com.watheq.watheq.utils.Validations;
import com.watheq.watheq.views.RecyclerViewEmptySupport;

import butterknife.BindView;
import butterknife.OnClick;


public class ClientInformationFragment extends BaseDelegate {

    private static final String CATEGORY_ID = "category_id";
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.civil_registry)
    EditText civilRegistry;
    private int categoryId;

    public static ClientInformationFragment newInstance(int id) {
        ClientInformationFragment clientInformationFragment = new ClientInformationFragment();
        Bundle args = new Bundle();
        args.putInt(CATEGORY_ID, id);
        clientInformationFragment.setArguments(args);
        return clientInformationFragment;
    }

    public ClientInformationFragment() {
        // Required empty public constructor
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_client_information;
    }

    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() != null)
            ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.client_info), 100 * 3);
        Bundle bundle = this.getArguments();
        if (bundle != null)
            categoryId = bundle.getInt(CATEGORY_ID);
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
        clientInfoModel.setId(categoryId);
        pushFragment(MainClientFragment.newInstance(clientInfoModel), false);
    }

}
