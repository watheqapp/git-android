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
//import android.widget.TextView;
//
//import com.watheq.watheq.MainActivity;
//import com.watheq.watheq.R;
//import com.watheq.watheq.base.BaseFragment;
//import com.watheq.watheq.model.ClientInfoModel;
//import com.watheq.watheq.model.OrderLawyerBody;
//import com.watheq.watheq.model.OrderLawyerResponse;
//import com.watheq.watheq.utils.UserManager;
//import com.watheq.watheq.utils.Validations;
//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
//import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
//
//import java.util.Calendar;
//
//import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class PickDateFragment extends BaseDelegate {
//
//    private static final String INFO_CLIENT = "client_info";
//    @BindView(R.id.time)
//    TextView timeTextview;
//    @BindView(R.id.date)
//    TextView dateTextview;
//    @BindView(R.id.next_step)
//    CircularProgressButton circularProgressButton;
//    @BindView(R.id.info_title)
//    TextView infoTitle;
//    private ClientInfoModel clientInfoModel;
//    private OrderLawyerViewModel orderLawyerViewModel;
//
//    public PickDateFragment() {
//        // Required empty public constructor
//    }
//
//    public static PickDateFragment getInstance(ClientInfoModel clientInfoModel) {
//        PickDateFragment pickDateFragment = new PickDateFragment();
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
//                    ((MainActivity) getActivity()).backToFirstFragment();
//                }
//            }, 500);
//
//
//        }
//    };
//
//    @Override
//    public int getLayoutResource() {
//        return R.layout.fragment_pick_date;
//    }
//
//    @Override
//    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        orderLawyerViewModel = ViewModelProviders.of(this).get(OrderLawyerViewModel.class);
//        Bundle bundle = this.getArguments();
//        if (bundle != null)
//            clientInfoModel = bundle.getParcelable(INFO_CLIENT);
//
//        if (clientInfoModel.getDeliveryPlace().equals("home")) {
//            ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.arrived_time)
//                    , 500, clientInfoModel.getCost() + clientInfoModel.getDeliveryToHomeFees());
//            infoTitle.setText(getString(R.string.order_maazon_around));
//        } else {
//            ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.arrived_time)
//                    , 500, clientInfoModel.getCost());
//            infoTitle.setText(getString(R.string.go_to_maazon_around));
//        }
//    }
//
//    @OnClick(R.id.date)
//    void onDateClicked() {
//        Calendar now = Calendar.getInstance();
//        DatePickerDialog dpd = DatePickerDialog.newInstance(
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                        dateTextview.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
//                    }
//                },
//                now.get(Calendar.YEAR),
//                now.get(Calendar.MONTH),
//                now.get(Calendar.DAY_OF_MONTH)
//        );
//        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
//    }
//
//    @OnClick(R.id.time)
//    void onTimeClicked() {
//        Calendar now = Calendar.getInstance();
//        TimePickerDialog dpd = TimePickerDialog.newInstance(
//                new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
//                        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
//                        String minuteString = minute < 10 ? "0" + minute : "" + minute;
//
//                        String time = hourString + ":" + minuteString;
//                        timeTextview.setText(time);
//                    }
//                },
//                now.get(Calendar.HOUR_OF_DAY),
//                now.get(Calendar.MINUTE),
//                true
//        );
//        dpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
//    }
//
//    @OnClick(R.id.next_step)
//    void onNextStepClicked() {
//        if (!Validations.isNotEmpty(timeTextview.getText().toString()) || !Validations.isNotEmpty(dateTextview.getText().toString())) {
//            showNotification(getString(R.string.validation_error_required));
//            return;
//        }
//
////        pushFragment(DeliveryPlaceFragment.newInstance(clientInfoModel), false);
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
//}
