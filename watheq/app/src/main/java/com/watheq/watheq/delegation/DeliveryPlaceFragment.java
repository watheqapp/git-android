//package com.watheq.watheq.delegation;
//
//
//import android.arch.lifecycle.Observer;
//import android.arch.lifecycle.ViewModelProviders;
//import android.content.Intent;
//import android.graphics.PorterDuff;
//import android.graphics.PorterDuffColorFilter;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.CardView;
//import android.util.Log;
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
//
//import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class DeliveryPlaceFragment extends BaseDelegate {
//
//    private static final String INFO_CLIENT = "client_info";
//    @BindView(R.id.home_title)
//    TextView homeTitle;
//    @BindView(R.id.office_title)
//    TextView officeTitle;
//    @BindView(R.id.office)
//    CardView office;
//    @BindView(R.id.home)
//    CardView home;
//    @BindView(R.id.confirm_btn)
//    CircularProgressButton circularProgressButton;
//    private String deliveryPlace;
//    private ClientInfoModel clientInfoModel;
//    private OrderLawyerViewModel orderLawyerViewModel;
//
//    public static DeliveryPlaceFragment newInstance(ClientInfoModel clientInfoModel) {
//        DeliveryPlaceFragment deliveryPlaceFragment = new DeliveryPlaceFragment();
//        Bundle args = new Bundle();
//        args.putParcelable(INFO_CLIENT, clientInfoModel);
//        deliveryPlaceFragment.setArguments(args);
//        return deliveryPlaceFragment;
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
//
//    public DeliveryPlaceFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public int getLayoutResource() {
//        return R.layout.fragment_delevery_place;
//    }
//
//    @Override
//    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
//        orderLawyerViewModel = ViewModelProviders.of(this).get(OrderLawyerViewModel.class);
//
//
//        Drawable homeIcon = ContextCompat.getDrawable(getContext(), R.drawable._active);
//        Drawable OfficeIcon = ContextCompat.getDrawable(getContext(), R.drawable.ic_home_active);
//        homeIcon.setColorFilter(new
//                PorterDuffColorFilter(ContextCompat.getColor(getContext(), R.color.pastel_blue), PorterDuff.Mode.MULTIPLY));
//        OfficeIcon.setColorFilter(new
//                PorterDuffColorFilter(ContextCompat.getColor(getContext(), R.color.pastel_blue), PorterDuff.Mode.MULTIPLY));
//
//
//        Bundle bundle = this.getArguments();
//        if (bundle != null)
//            clientInfoModel = bundle.getParcelable(INFO_CLIENT);
//        if (!clientInfoModel.isAuth()) {
//            if (!clientInfoModel.isHasNoSubs())
//                ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), (500 / 6) * 5,
//                        clientInfoModel.getCost());
//            else
//                ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), (500 / 5) * 4,
//                        clientInfoModel.getCost());
//        } else {
//            ((MainActivity) getActivity()).updateToolbarTitle(clientInfoModel.getTitle(), (500 / 2),
//                    clientInfoModel.getCost());
//            homeTitle.setText(getString(R.string.client_place));
//            officeTitle.setText(getString(R.string.authorized_place));
//        }
//        if (deliveryPlace == null) return;
//        if (deliveryPlace.equals("home")) {
//            setHomeBackground();
//            if (!clientInfoModel.isAuth()) {
//                if (!clientInfoModel.isHasNoSubs())
//                    ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), (500 / 6) * 5,
//                            clientInfoModel.getCost() + clientInfoModel.getDeliveryToHomeFees());
//                else
//                    ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), (500 / 5) * 4,
//                            clientInfoModel.getCost() + clientInfoModel.getDeliveryToHomeFees());
//            } else
//                ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), (500 / 2),
//                        clientInfoModel.getCost() + clientInfoModel.getDeliveryToHomeFees());
//        } else {
//            setOfficeBackground();
//            if (!clientInfoModel.isAuth()) {
//                if (!clientInfoModel.isHasNoSubs())
//                    ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), (500 / 6) * 5,
//                            clientInfoModel.getCost());
//                else
//                    ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), (500 / 5) * 4,
//                            clientInfoModel.getCost());
//            } else
//                ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), (500 / 2),
//                        clientInfoModel.getCost());
//        }
//    }
//
//    @OnClick(R.id.home)
//    void onHomeClicked() {
//        deliveryPlace = "home";
//        clientInfoModel.setDeliveryPlace(deliveryPlace);
//        setHomeBackground();
//        if (!clientInfoModel.isAuth()) {
//            if (!clientInfoModel.isHasNoSubs())
//                ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), (500 / 6) * 5,
//                        clientInfoModel.getCost() + clientInfoModel.getDeliveryToHomeFees());
//            else
//                ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), (500 / 5) * 4,
//                        clientInfoModel.getCost() + clientInfoModel.getDeliveryToHomeFees());
//        } else {
//            ((MainActivity) getActivity()).updateToolbarTitle(clientInfoModel.getTitle(), (500 / 2),
//                    clientInfoModel.getCost() + clientInfoModel.getDeliveryToHomeFees());
//        }
////        if (!clientInfoModel.isAuth())
////            ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), 100 * 5,
////                    clientInfoModel.getCost());
////        else {
////            ((MainActivity) getActivity()).updateToolbarTitle(clientInfoModel.getTitle(), (500 / 3) * 2,
////                    clientInfoModel.getCost());
////        }
//        home.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pushFragment(MapFragment.newInstance(clientInfoModel), false);
//            }
//        }, 500);
//    }
//
//    private void setHomeBackground() {
//        home.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.united_blue));
//        homeTitle.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
//        office.setCardBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
//        officeTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.united_blue));
//    }
//
//    private void setOfficeBackground() {
//        office.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.united_blue));
//        officeTitle.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
//        home.setCardBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
//        homeTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.united_blue));
//    }
//
//    @OnClick(R.id.office)
//    void onOfficeClicked() {
//        deliveryPlace = "office";
//        clientInfoModel.setDeliveryPlace(deliveryPlace);
//        setOfficeBackground();
//        if (!clientInfoModel.isAuth()) {
//            if (!clientInfoModel.isHasNoSubs())
//                ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), (500 / 6) * 5,
//                        clientInfoModel.getCost());
//            else
//                ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), (500 / 5) * 4,
//                        clientInfoModel.getCost());
//        } else {
//            ((MainActivity) getActivity()).updateToolbarTitle(clientInfoModel.getTitle(), (500 / 2),
//                    clientInfoModel.getCost());
//        }
////        if (!clientInfoModel.isAuth())
////            ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.choose_loc), 100 * 5,
////                    clientInfoModel.getCost());
////        else {
////            ((MainActivity) getActivity()).updateToolbarTitle(clientInfoModel.getTitle(), (500 / 3) * 2,
////                    clientInfoModel.getCost());
////        }
//        office.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pushFragment(MapFragment.newInstance(clientInfoModel), false);
//            }
//        }, 500);
//    }
//
//    @OnClick(R.id.confirm_btn)
//    void onConfirmClicked() {
//        if (deliveryPlace == null) {
//            showNotification(getString(R.string.choose_location_msg));
//            return;
//        }
//
////        OrderLawyerBody orderLawyerBody = new OrderLawyerBody();
////        orderLawyerBody.setRepresentativeName(clientInfoModel.getRepresentativeName());
////        orderLawyerBody.setClientNationalID(clientInfoModel.getCivilRegistry());
////        orderLawyerBody.setClientName(clientInfoModel.getName());
////        orderLawyerBody.setRepresentativeNationalID(clientInfoModel.getClientNationalID());
////        orderLawyerBody.setCategoryId(clientInfoModel.getId());
////        orderLawyerBody.setDelivery(deliveryPlace);
////        orderLawyerBody.setLatitude(getParentActivity().getLat());
////        orderLawyerBody.setLongitude(getParentActivity().getLng());
////        circularProgressButton.startAnimation();
////        orderLawyerViewModel.orderLawyer(UserManager.getInstance().getUserToken(), orderLawyerBody
////                , this).observe(this, orderLawyer);
//
//    }
//
//}
