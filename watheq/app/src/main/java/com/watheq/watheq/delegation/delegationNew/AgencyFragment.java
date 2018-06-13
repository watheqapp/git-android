package com.watheq.watheq.delegation.delegationNew;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.Calendar;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgencyFragment extends BaseFragment implements CustomDialog.FilterCallback {

    private static final String TAG = "OnChange";
    @BindView(R.id.office)
    TextView office;
    @BindView(R.id.home)
    TextView home;
    @BindView(R.id.create_agency)
    TextView createAgency;
    @BindView(R.id.cancel_agency)
    TextView cancelAgency;
    @BindView(R.id.agency_kind)
    TextView agencyKind;
    @BindView(R.id.later_time)
    TextView anotherTime;
    @BindView(R.id.one_hour)
    TextView oneHour;
    @BindView(R.id.two_hours)
    TextView twoHours;
    @BindView(R.id.three_hours)
    TextView threeHours;
    @BindView(R.id.arrival_time)
    TextView arrivalTime;
    @BindView(R.id.next_step)
    CircularProgressButton circularProgressButton;
    private OrderLawyerViewModel orderLawyerViewModel;
    private boolean isCreateAgency = true;
    private String categoreyId = "";
    private String deliveryPlace, deliveryTime, deliveryDate;
    private double lat, lng;

    public static final String PERSONALLY = "personally";
    public static final String ORGNIZATION = "orgnization";
    public static final String COMPANY = "company";
    private MainCategoriesResponse mainCategoriesResponse;
    private int price;
    private ArrayList<String> names;
    private ArrayList<String> descriptions;
    //    private String addressDetails;
    private boolean isHome, wasPlaceSelected, isLater;
    private String oldPlace = "";

    public AgencyFragment() {
        // Required empty public constructor
    }

    private final Observer<OrderLawyerResponse> orderLawyer = new Observer<OrderLawyerResponse>() {
        @Override
        public void onChanged(@Nullable OrderLawyerResponse orderLawyerResponse) {

            if (orderLawyerResponse.getCode() == 200) {
                circularProgressButton.revertAnimation();
                categoreyId = "";
                deliveryPlace = "";
                deliveryTime = "";
                deliveryDate = "";
                lat = 0d;
                lng = 0d;
                isCreateAgency = true;
                agencyKind.setText(getString(R.string.againt_kind));
                setSelectedBackground(createAgency, cancelAgency);
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

    public static AgencyFragment getInstance(MainCategoriesResponse mainCategoriesResponse) {
        AgencyFragment agencyFragment = new AgencyFragment();
        Bundle args = new Bundle();
        args.putParcelable("categories", mainCategoriesResponse);
        agencyFragment.setArguments(args);
        return agencyFragment;
    }


    @Override
    public int getLayoutResource() {
        return R.layout.fragment_agency;
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
                        arrivalTime.setText(String.format("%s:%s", hourString, minuteString));
                        deliveryDate = hourString + ":" + minuteString;
                    }
                },
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        isLater = true;
        dpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        mainCategoriesResponse = bundle.getParcelable("categories");
        home.setText(String.format("%s + %d", getString(R.string.home), mainCategoriesResponse.getData().getDeliverToHomeFees()));
//        description.setText(mainCategoriesResponse.getData().getCategories().get(0).getDisc());
        orderLawyerViewModel = ViewModelProviders.of(this).get(OrderLawyerViewModel.class);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            wasPlaceSelected = true;
            lat = data.getDoubleExtra("lat", 0);
            lng = data.getDoubleExtra("lag", 0);
//            addressDetails = data.getStringExtra("addressDetails");
            if (deliveryPlace.equals("home")) {
                setSelectedBackground(home, office);
                circularProgressButton.setText(String.format("%s (%d)%s", getString(R.string.send_request)
                        , price + mainCategoriesResponse.getData().getDeliverToHomeFees(), getString(R.string.ksa_currency)));
            } else {
                setSelectedBackground(office, home);
                circularProgressButton.setText(String.format("%s (%d)%s", getString(R.string.send_request)
                        , price, getString(R.string.ksa_currency)));
            }
            oldPlace = deliveryPlace;
        } else if (requestCode == 111 && resultCode == Activity.RESULT_CANCELED) {
            deliveryPlace = oldPlace;
        }
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

    @SuppressLint("DefaultLocale")
    @OnClick(R.id.office)
    void onOfficeClicked() {

        deliveryPlace = "office";//getString(R.string.office);
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivityForResult(intent, 111);

    }

    @SuppressLint("DefaultLocale")
    @OnClick(R.id.home)
    void onHomeClicked() {
        isHome = true;
        deliveryPlace = "home";//getString(R.string.home);
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivityForResult(intent, 111);
    }

    @OnClick(R.id.cancel_agency)
    void onCancelAgencyClicked() {
        setSelectedBackground(cancelAgency, createAgency);
        isCreateAgency = false;
        categoreyId = "";
        price = 0;
        agencyKind.setText(getString(R.string.againt_kind));
        circularProgressButton.setText(getString(R.string.send_request));
    }

    @OnClick(R.id.create_agency)
    void onCreateAgencyClicked() {
        setSelectedBackground(createAgency, cancelAgency);
        isCreateAgency = true;
        categoreyId = "";
        price = 0;
        agencyKind.setText(getString(R.string.againt_kind));
        circularProgressButton.setText(getString(R.string.send_request));
    }

    @OnClick(R.id.agency_kind)
    void onChooseAgencyClicked() {
        if (names == null) {
            names = new ArrayList<>();
            descriptions = new ArrayList<>();
        }
        names.clear();
        descriptions.clear();
        if (isCreateAgency) {
            names.add(mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(0).getSubs().get(0).getName());
            names.add(mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(0).getSubs().get(1).getName());
            names.add(mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(0).getSubs().get(2).getName());
            descriptions.add(mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(0).getSubs().get(0).getDisc());
            descriptions.add(mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(0).getSubs().get(1).getDisc());
            descriptions.add(mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(0).getSubs().get(2).getDisc());
        } else {
            names.add(mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(1).getSubs().get(0).getName());
            names.add(mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(1).getSubs().get(1).getName());
            names.add(mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(1).getSubs().get(2).getName());
            descriptions.add(mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(1).getSubs().get(0).getDisc());
            descriptions.add(mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(1).getSubs().get(1).getDisc());
            descriptions.add(mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(1).getSubs().get(2).getDisc());
        }
        CustomDialog customDialog = new CustomDialog(getParentActivity(), this,
                isCreateAgency, names, descriptions);
        customDialog.show();
    }

    @OnClick(R.id.later_time)
    void onDateClicked() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        setSelectedBackground(oneHour, twoHours, threeHours, true);
                        anotherTime.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
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

    @SuppressLint("DefaultLocale")
    @Override
    public void onFilterListener(String state, String tag, String title) {
        categoreyId = tag;
        switch (categoreyId) {
            case "3":
                price = mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(0).getSubs().get(0).getCost();
                break;
            case "4":
                price = mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(0).getSubs().get(1).getCost();
                break;
            case "5":
                price = mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(0).getSubs().get(2).getCost();
                break;
            case "7":
                price = mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(1).getSubs().get(0).getCost();
                break;
            case "8":
                price = mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(1).getSubs().get(1).getCost();
                break;
            case "9":
                price = mainCategoriesResponse.getData().getCategories().get(0).getSubs().get(1).getSubs().get(2).getCost();
                break;
        }

        if (oldPlace.equals("home"))
            circularProgressButton.setText(String.format("%s (%d)%s", getString(R.string.send_request)
                    , price + mainCategoriesResponse.getData().getDeliverToHomeFees(), getString(R.string.ksa_currency)));
        else
            circularProgressButton.setText(String.format("%s (%d)%s", getString(R.string.send_request), price, getString(R.string.ksa_currency)));
        agencyKind.setText(title);
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
        circularProgressButton.startAnimation();
        OrderLawyerBody orderLawyerBody = new OrderLawyerBody();
        orderLawyerBody.setCategoryId(Integer.valueOf(categoreyId));
        orderLawyerBody.setDelivery(deliveryPlace);
//        orderLawyerBody.setAddress(addressDetails);
        orderLawyerBody.setTime(deliveryTime);
        orderLawyerBody.setMarriageTime(deliveryDate);
        orderLawyerBody.setLatitude(lat);
        orderLawyerBody.setLongitude(lng);
        orderLawyerViewModel.orderLawyer(UserManager.getInstance().getUserToken(), orderLawyerBody
                , this).observe(this, orderLawyer);
    }
}
