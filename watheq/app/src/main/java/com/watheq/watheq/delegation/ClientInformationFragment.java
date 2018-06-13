//package com.watheq.watheq.delegation;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.InputType;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.watheq.watheq.MainActivity;
//import com.watheq.watheq.R;
//import com.watheq.watheq.base.BaseFragment;
//import com.watheq.watheq.model.ClientInfoModel;
//import com.watheq.watheq.model.Sub;
//import com.watheq.watheq.utils.Validations;
//import com.watheq.watheq.views.RecyclerViewEmptySupport;
//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
//
//import java.util.Calendar;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
//
//public class ClientInformationFragment extends BaseDelegate implements DatePickerDialog.OnDateSetListener {
//
//    private static final String CATEGORY_ID = "category_id";
//    private static final String IS_HAS_NO_SUB = "cost";
//    @BindView(R.id.name)
//    EditText name;
//    @BindView(R.id.civil_registry)
//    EditText civilRegistry;
//    @BindView(R.id.info_title)
//    TextView infoTitle;
//    @BindView(R.id.company_case)
//    TextView companyCase;
//    @BindView(R.id.civil_registry_tv)
//    TextView civilRegistryTv;
//    //    private int categoryId,cost;
//    private Sub sub;
//    private boolean jumpToDelivery;
//
//    public static ClientInformationFragment newInstance(Sub sub) {
//        ClientInformationFragment clientInformationFragment = new ClientInformationFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(CATEGORY_ID, sub);
//
////        args.putInt(COST, cost);
//        clientInformationFragment.setArguments(args);
//        return clientInformationFragment;
//    }
//
//    public ClientInformationFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public int getLayoutResource() {
//        return R.layout.fragment_client_information;
//    }
//
//    @Override
//    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//            sub = bundle.getParcelable(CATEGORY_ID);
//        }
//        if (getActivity() != null) {
//            if (!sub.isHasNoSubs()) {
//                ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.client_info), (500 / 6) * 3);
//                infoTitle.setText(getString(R.string.client_info));
//                name.setHint(getString(R.string.name));
//                name.setInputType(InputType.TYPE_CLASS_TEXT);
//                civilRegistry.setInputType(InputType.TYPE_CLASS_NUMBER);
//                civilRegistry.setHint(getString(R.string.civil_registry));
//                civilRegistryTv.setVisibility(View.GONE);
//                civilRegistry.setVisibility(View.VISIBLE);
//                companyCase.setVisibility(View.VISIBLE);
//                jumpToDelivery = false;
//            } else {
//                infoTitle.setText(getString(R.string.client_msg));
//                companyCase.setVisibility(View.GONE);
//                name.setInputType(InputType.TYPE_CLASS_NUMBER);
//                ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.letter_num), (500 / 5) * 2);
//                name.setHint(getString(R.string.letter_num));
//                jumpToDelivery = true;
//                civilRegistryTv.setVisibility(View.VISIBLE);
//                civilRegistry.setVisibility(View.GONE);
//                civilRegistryTv.setHint(getString(R.string.date));
//                civilRegistryTv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Calendar now = Calendar.getInstance();
//                        DatePickerDialog dpd = DatePickerDialog.newInstance(
//                                ClientInformationFragment.this,
//                                now.get(Calendar.YEAR),
//                                now.get(Calendar.MONTH),
//                                now.get(Calendar.DAY_OF_MONTH)
//                        );
//                        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
//                    }
//                });
//            }
//        }
//    }
//
//    @OnClick(R.id.next_step)
//    void onNextStepClicked() {
//        if (!Validations.isNotEmpty(name.getText().toString())) {
//            showNotification(getString(R.string.validation_error_required));
//            return;
//        }
//        if (!sub.isHasNoSubs()) {
//            if (!Validations.isNotEmpty(civilRegistry.getText().toString())) {
//                showNotification(getString(R.string.validation_error_required));
//                return;
//            }
//        } else if (!Validations.isNotEmpty(civilRegistryTv.getText().toString())) {
//            showNotification(getString(R.string.validation_error_required));
//            return;
//        }
//
//        ClientInfoModel clientInfoModel = new ClientInfoModel();
//        clientInfoModel.setName(name.getText().toString());
//        clientInfoModel.setCivilRegistry(civilRegistry.getText().toString());
//        clientInfoModel.setId(sub.getId());
//        clientInfoModel.setCost(sub.getCost());
//        clientInfoModel.setHasNoSubs(sub.isHasNoSubs());
//        clientInfoModel.setDeliveryToHomeFees(sub.getDeliveryToHomeFees());
//        if (!jumpToDelivery)
//            pushFragment(MainClientFragment.newInstance(clientInfoModel), false);
//        else
//            pushFragment(DeliveryPlaceFragment.newInstance(clientInfoModel), false);
//    }
//
//    @Override
//    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//        civilRegistryTv.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//    }
//}
