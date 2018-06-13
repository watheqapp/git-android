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
import android.widget.EditText;
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
import com.watheq.watheq.views.CustomDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestContractFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.next_step)
    CircularProgressButton circularProgressButton;
    @BindView(R.id.later_time)
    TextView anotherTime;
    @BindView(R.id.one_hour)
    TextView oneHour;
    @BindView(R.id.two_hours)
    TextView twoHours;
    @BindView(R.id.three_hours)
    TextView threeHours;
    @BindView(R.id.office)
    TextView office;
    @BindView(R.id.home)
    TextView home;
    @BindView(R.id.arrival_time)
    TextView arrivalTime;
    private double lat, lng;
    private OrderLawyerViewModel orderLawyerViewModel;
    private String categoreyId, deliveryPlace, deliveryTime, deliveryDate;
    private MainCategoriesResponse mainCategoriesResponse;
    private int price;
    //    private String addressDetails;
    private String oldPlace = "";
    private boolean isLater;

    public RequestContractFragment() {
        // Required empty public constructor
    }

    private final Observer<OrderLawyerResponse> orderLawyer = new Observer<OrderLawyerResponse>() {
        @Override
        public void onChanged(@Nullable OrderLawyerResponse orderLawyerResponse) {

            if (orderLawyerResponse.getCode() == 200) {
                circularProgressButton.revertAnimation();
                deliveryPlace = "";
                deliveryTime = "";
                deliveryDate = "";
                lat = 0d;
                lng = 0d;
                home.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_background));
                home.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
                office.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_background));
                office.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
                setSelectedBackground(oneHour, twoHours, threeHours, true);
                anotherTime.setText(getString(R.string.another_time));
                arrivalTime.setText(getString(R.string.arrived_time));
                circularProgressButton.requestFocus();
                startActivity(new Intent(getActivity(), SearchingForLawyer.class).putExtra("id", orderLawyerResponse.getData().getId()));
            } else {
                circularProgressButton.revertAnimation();
            }
        }
    };

    public static RequestContractFragment newInstance(MainCategoriesResponse mainCategoriesResponse) {
        RequestContractFragment contractFragment = new RequestContractFragment();
        Bundle args = new Bundle();
        args.putParcelable("categories", mainCategoriesResponse);
        contractFragment.setArguments(args);
        return contractFragment;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_request_contract;
    }

    @OnClick(R.id.arrival_time)
    void onTimeClicked() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                        setSelectedBackground(oneHour, twoHours, threeHours, true);
                        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                        String minuteString = minute < 10 ? "0" + minute : "" + minute;
                        arrivalTime.setText(hourString + ":" + minuteString);
                        deliveryDate = hourString + ":" + minuteString;
                    }
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        dpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
        categoreyId = "10";

        Bundle bundle = this.getArguments();
        mainCategoriesResponse = bundle.getParcelable("categories");
        price = Integer.parseInt(mainCategoriesResponse.getData().getCategories().get(1).getCost());
        home.setText(String.format("%s + %d", getString(R.string.home), mainCategoriesResponse.getData().getDeliverToHomeFees()));
        orderLawyerViewModel = ViewModelProviders.of(this).get(OrderLawyerViewModel.class);
        circularProgressButton.setText(String.format("%s (%d)%s", getString(R.string.send_request), price, getString(R.string.ksa_currency)));
    }

    @OnClick(R.id.one_hour)
    void onOneHourClicked() {
        anotherTime.setText(getString(R.string.another_time));
        deliveryDate = "";
        arrivalTime.setText(getString(R.string.arrived_time));
        setSelectedBackground(oneHour, twoHours, threeHours, false);
        deliveryTime = "oneHour";//getString(R.string.one_hour);
        isLater = false;
    }

    @OnClick(R.id.two_hours)
    void onTwoHourClicked() {
        anotherTime.setText(getString(R.string.another_time));
        deliveryDate = "";
        arrivalTime.setText(getString(R.string.arrived_time));
        setSelectedBackground(twoHours, oneHour, threeHours, false);
        deliveryTime = "twoHours";//getString(R.string.two_hours);
        isLater = false;
    }

    @OnClick(R.id.three_hours)
    void onThreeHourClicked() {
        anotherTime.setText(getString(R.string.another_time));
        deliveryDate = "";
        arrivalTime.setText(getString(R.string.arrived_time));
        setSelectedBackground(threeHours, twoHours, oneHour, false);
        deliveryTime = "threeHours";//getString(R.string.three_hours);
        isLater = false;
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

    @OnClick(R.id.later_time)
    void onDateClicked() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        setSelectedBackground(oneHour, twoHours, threeHours, true);
                        anotherTime.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear + 1, year));
                        deliveryTime = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        isLater = true;
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
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
            if (oldPlace.equals("home"))
                circularProgressButton.setText(String.format("%s (%d)%s",
                        getString(R.string.send_request), price + mainCategoriesResponse.getData().getDeliverToHomeFees()
                        , getString(R.string.ksa_currency)));
            else circularProgressButton.setText(String.format("%s (%d)%s",
                    getString(R.string.send_request), price
                    , getString(R.string.ksa_currency)));
        } else if (requestCode == 111 && resultCode == Activity.RESULT_CANCELED) {
            deliveryPlace = oldPlace;
        }
    }

    private void setSelectedBackground(final TextView selectedTV, TextView notSelected) {
        selectedTV.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_selected_background));
        selectedTV.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        notSelected.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_background));
        notSelected.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
    }

    private void setSelectedBackground(final TextView selectedTV, TextView notSelected, TextView notSelect, boolean isAll) {
        if (!isAll) {
            selectedTV.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_selected_background));
            selectedTV.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        } else {
            selectedTV.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_background));
            selectedTV.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
        }
        notSelected.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_background));
        notSelected.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
        notSelect.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.category_background));
        notSelect.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    @OnClick(R.id.next_step)
    void onNextStepClicked() {

        if (TextUtils.isEmpty(categoreyId)
                || TextUtils.isEmpty(deliveryPlace)
                || TextUtils.isEmpty(deliveryTime)
                || lat == 0d
                || lng == 0d) {
            showNotification(getString(R.string.validation_error_required));
            return;
        }

        if (isLater && (TextUtils.isEmpty(deliveryTime) || TextUtils.isEmpty(deliveryDate))) {
            showNotification(getString(R.string.validation_error_required));
            return;
        }

        OrderLawyerBody orderLawyerBody = new OrderLawyerBody();
        orderLawyerBody.setCategoryId(Integer.valueOf(categoreyId));
        orderLawyerBody.setDelivery(deliveryPlace);
//        orderLawyerBody.setAddress(addressDetails);
        orderLawyerBody.setTime(deliveryTime);
        orderLawyerBody.setMarriageTime(deliveryDate);
        orderLawyerBody.setLatitude(lat);
        orderLawyerBody.setLongitude(lng);
        circularProgressButton.startAnimation();
        orderLawyerViewModel.orderLawyer(UserManager.getInstance().getUserToken(), orderLawyerBody
                , this).observe(this, orderLawyer);
    }
}
