package com.watheq.watheq.delegation.delegationNew;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.delegation.MapsActivity;
import com.watheq.watheq.delegation.OrderLawyerViewModel;
import com.watheq.watheq.delegation.SearchingForLawyer;
import com.watheq.watheq.model.MainCategoriesResponse;
import com.watheq.watheq.model.OrderLawyerBody;
import com.watheq.watheq.model.OrderLawyerResponse;
import com.watheq.watheq.utils.UserManager;
import com.watheq.watheq.utils.Validations;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarriageRequestFragment extends BaseFragment {

    @BindView(R.id.office)
    TextView office;
    @BindView(R.id.home)
    TextView home;
    @BindView(R.id.arrival_date)
    TextView arrivalDate;
    @BindView(R.id.arrival_time)
    TextView arrivalTime;
    @BindView(R.id.next_step)
    CircularProgressButton circularProgressButton;
    private double lat, lng;
    private OrderLawyerViewModel orderLawyerViewModel;
    private String categoreyId, deliveryPlace, deliveryTime, deliveryDate;
    private MainCategoriesResponse mainCategoriesResponse;
    private int price;
    //    private String addressDetails;
    private String oldPlace = "";

    public MarriageRequestFragment() {
        // Required empty public constructor
    }

    public static MarriageRequestFragment newInctance(MainCategoriesResponse mainCategoriesResponse) {
        MarriageRequestFragment marriageRequestFragment = new MarriageRequestFragment();
        Bundle args = new Bundle();
        args.putParcelable("categories", mainCategoriesResponse);
        marriageRequestFragment.setArguments(args);
        return marriageRequestFragment;
    }

    private final Observer<OrderLawyerResponse> orderLawyer = new Observer<OrderLawyerResponse>() {
        @Override
        public void onChanged(@Nullable OrderLawyerResponse orderLawyerResponse) {
            if (orderLawyerResponse.getCode() == 200) {
                circularProgressButton.revertAnimation();
                home.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_background));
                home.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
                office.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_background));
                office.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
                arrivalDate.setText(getString(R.string.arrived_date));
                arrivalTime.setText(getString(R.string.arrived_time));
                categoreyId = "";
                deliveryPlace = "";
                deliveryTime = "";
                deliveryDate = "";
                lat = 0d;
                lat = 0d;
                lng = 0d;
                circularProgressButton.requestFocus();
                startActivity(new Intent(getActivity(), SearchingForLawyer.class).putExtra("id", orderLawyerResponse.getData().getId()));
            } else {
                circularProgressButton.revertAnimation();
            }
        }
    };

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_marriage_request;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
        categoreyId = "13";
        Bundle bundle = this.getArguments();
        mainCategoriesResponse = bundle.getParcelable("categories");
//        home.setText(String.format("%s + %d", getString(R.string.home), mainCategoriesResponse.getData().getDeliverToHomeFees()));
        orderLawyerViewModel = ViewModelProviders.of(this).get(OrderLawyerViewModel.class);
        price = Integer.parseInt(mainCategoriesResponse.getData().getCategories().get(2).getCost());
//        circularProgressButton.setText(getString(R.string.send_request) + " " + "(" + price + ")" + getString(R.string.ksa_currency));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            lat = data.getDoubleExtra("lat", 0);
            lng = data.getDoubleExtra("lag", 0);
//            addressDetails = data.getStringExtra("addressDetails");
            if (deliveryPlace.equals("home")) {
                setSelectedBackground(home, office);
            } else {
                setSelectedBackground(office, home);
            }
            oldPlace = deliveryPlace;
//            if (oldPlace.equals("home"))
//                circularProgressButton.setText(String.format("%s (%d)%s",
//                        getString(R.string.send_request), price + mainCategoriesResponse.getData().getDeliverToHomeFees()
//                        , getString(R.string.ksa_currency)));
//            else circularProgressButton.setText(String.format("%s (%d)%s",
//                    getString(R.string.send_request), price
//                    , getString(R.string.ksa_currency)));
        } else if (requestCode == 111 && resultCode == Activity.RESULT_CANCELED) {
            deliveryPlace = oldPlace;
        }
    }

    @OnClick(R.id.office)
    void onOfficeClicked() {

        deliveryPlace = "office";//getString(R.string.office);
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivityForResult(intent, 111);
    }

    @OnClick(R.id.home)
    void onHomeClicked() {

        deliveryPlace = "home";//getString(R.string.home);
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivityForResult(intent, 111);
    }

    private void setSelectedBackground(final TextView selectedTV, TextView notSelected) {
        selectedTV.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_selected_background));
        selectedTV.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        notSelected.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_background));
        notSelected.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
    }

    @OnClick(R.id.arrival_date)
    void onArrivalDateClicked() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        arrivalDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        deliveryDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }


    @OnClick(R.id.arrival_time)
    void onTimeClicked() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                        String minuteString = minute < 10 ? "0" + minute : "" + minute;
                        arrivalTime.setText(hourString + ":" + minuteString);
                        deliveryTime = hourString + ":" + minuteString;
                    }
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        dpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    @OnClick(R.id.next_step)
    void onNextStepClicked() {

        if (TextUtils.isEmpty(categoreyId)
                || TextUtils.isEmpty(deliveryPlace)
                || TextUtils.isEmpty(deliveryTime)
                || TextUtils.isEmpty(deliveryDate)
                || lat == 0d
                || lng == 0d) {
            showNotification(getString(R.string.validation_error_required));
            return;
        }

        OrderLawyerBody orderLawyerBody = new OrderLawyerBody();
        orderLawyerBody.setCategoryId(Integer.valueOf(categoreyId));
        orderLawyerBody.setDelivery(deliveryPlace);
//        orderLawyerBody.setAddress(addressDetails);
        orderLawyerBody.setMarriageTime(deliveryTime);
        orderLawyerBody.setMarriageDate(deliveryDate);
        orderLawyerBody.setLatitude(lat);
        orderLawyerBody.setLongitude(lng);
        circularProgressButton.startAnimation();
        orderLawyerViewModel.orderLawyer(UserManager.getInstance().getUserToken(), orderLawyerBody
                , this).observe(this, orderLawyer);
    }

}
