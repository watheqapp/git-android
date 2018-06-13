//package com.watheq.watheq.delegation;
//
//
//import android.arch.lifecycle.Observer;
//import android.arch.lifecycle.ViewModelProviders;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CompoundButton;
//import android.widget.RadioButton;
//import android.widget.TextView;
//
//import com.watheq.watheq.MainActivity;
//import com.watheq.watheq.R;
//import com.watheq.watheq.model.ClientInfoModel;
//import com.watheq.watheq.model.OrderLawyerBody;
//import com.watheq.watheq.model.OrderLawyerResponse;
//import com.watheq.watheq.utils.UserManager;
//import com.watheq.watheq.utils.Validations;
//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
//
//import java.util.Calendar;
//
//import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
//import butterknife.BindView;
//import butterknife.OnCheckedChanged;
//import butterknife.OnClick;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class PickDateForArrivingFragment extends BaseDelegate {
//
//    private static final String INFO_CLIENT = "client_info";
//
//    @BindView(R.id.next_step)
//    CircularProgressButton circularProgressButton;
//    @BindView(R.id.info_title)
//    TextView infoTitle;
//    @BindView(R.id.one_hour)
//    RadioButton oneHour;
//    @BindView(R.id.two_hours)
//    RadioButton twoHours;
//    @BindView(R.id.three_hours)
//    RadioButton threeHours;
//    @BindView(R.id.date)
//    TextView dateTv;
//    private OrderLawyerViewModel orderLawyerViewModel;
//    private ClientInfoModel clientInfoModel;
//
//    public static PickDateForArrivingFragment getInstance(ClientInfoModel clientInfoModel) {
//        PickDateForArrivingFragment pickDateFragment = new PickDateForArrivingFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(INFO_CLIENT, clientInfoModel);
//        pickDateFragment.setArguments(args);
//        return pickDateFragment;
//    }
//
//    private final Observer<OrderLawyerResponse> orderLawyer = new Observer<OrderLawyerResponse>() {
//        @Override
//        public void onChanged(@Nullable OrderLawyerResponse orderLawyerResponse) {
//
//            circularProgressButton.revertAnimation();
//            startActivity(new Intent(getActivity(), SearchingForLawyer.class).putExtra("id", orderLawyerResponse.getData().getId()));
//            circularProgressButton.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (((MainActivity) getActivity()) != null) {
//                        ((MainActivity) getActivity()).backToFirstFragment();
//                    }
//                }
//            }, 500);
//
//
//        }
//    };
//
//    public PickDateForArrivingFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public int getLayoutResource() {
//        return R.layout.fragment_pick_date_for_arriving;
//    }
//
//    @Override
//    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
//        orderLawyerViewModel = ViewModelProviders.of(this).get(OrderLawyerViewModel.class);
//        Bundle bundle = this.getArguments();
//        if (bundle != null)
//            clientInfoModel = bundle.getParcelable(INFO_CLIENT);
//        if (getActivity() != null) {
//            if (!clientInfoModel.isHasNoSubs()) {
//                if (clientInfoModel.getDeliveryPlace().equals("home")) {
//                    ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.auth_for)
//                            , (500 / 6) * 6, clientInfoModel.getCost() + clientInfoModel.getDeliveryToHomeFees());
//                    infoTitle.setText(getString(R.string.will_come_at));
//                } else {
//                    ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.auth_for)
//                            , (500 / 6) * 6, clientInfoModel.getCost());
//                    infoTitle.setText(getString(R.string.will_go_to_at));
//                }
//            } else {
//                if (clientInfoModel.getDeliveryPlace().equals("home")) {
//                    ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.auth_for)
//                            , (500 / 5) * 5, clientInfoModel.getCost() + clientInfoModel.getDeliveryToHomeFees());
//                    infoTitle.setText(getString(R.string.will_come_at));
//                } else {
//                    ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.auth_for)
//                            , (500 / 5) * 5, clientInfoModel.getCost());
//                    infoTitle.setText(getString(R.string.will_go_to_at));
//                }
//            }
//        }
//
//        oneHour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                dateTv.setText("");
//            }
//        });
//
//        twoHours.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                dateTv.setText("");
//            }
//        });
//
//        threeHours.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                dateTv.setText("");
//            }
//        });
//    }
//
//
//    @OnClick(R.id.date)
//    void onDateClicked() {
//        Calendar now = Calendar.getInstance();
//        DatePickerDialog dpd = DatePickerDialog.newInstance(
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                        oneHour.setChecked(false);
//                        twoHours.setChecked(false);
//                        threeHours.setChecked(false);
//                        dateTv.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                    }
//                },
//                now.get(Calendar.YEAR),
//                now.get(Calendar.MONTH),
//                now.get(Calendar.DAY_OF_MONTH)
//        );
//        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
//    }
//
//    @OnClick(R.id.next_step)
//    void onNextStepClicked() {
//
//        if (!Validations.isNotEmpty(dateTv.getText().toString())
//                && !oneHour.isChecked() && !twoHours.isChecked() && !threeHours.isChecked()) {
//            showNotification(getString(R.string.validation_error_required));
//            return;
//        }
//
//        OrderLawyerBody orderLawyerBody = new OrderLawyerBody();
//        orderLawyerBody.setRepresentativeName(clientInfoModel.getRepresentativeName());
//        orderLawyerBody.setClientNationalID(clientInfoModel.getCivilRegistry());
//        orderLawyerBody.setClientName(clientInfoModel.getName());
//        orderLawyerBody.setRepresentativeNationalID(clientInfoModel.getClientNationalID());
//        orderLawyerBody.setCategoryId(clientInfoModel.getId());
//        orderLawyerBody.setDelivery(clientInfoModel.getDeliveryPlace());
//        orderLawyerBody.setLatitude(getParentActivity().getLat());
//        orderLawyerBody.setLongitude(getParentActivity().getLng());
//        circularProgressButton.startAnimation();
//        orderLawyerViewModel.orderLawyer(UserManager.getInstance().getUserToken(), orderLawyerBody
//                , this).observe(this, orderLawyer);
//    }
//
//}
