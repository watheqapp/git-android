package com.watheq.watheq.delegation.delegationNew;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.watheq.watheq.MainActivity;
import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.delegation.DelegationViewModel;
import com.watheq.watheq.model.MainCategoriesResponse;
import com.watheq.watheq.myOrder.PagerAdapter;
import com.watheq.watheq.utils.UserManager;
import com.watheq.watheq.views.EmptyView;
import com.watheq.watheq.views.NonSwipeableViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends BaseFragment {

    @BindView(R.id.pager)
    NonSwipeableViewPager pager;
    @BindView(R.id.new_orders)
    TextView newOrders;
    @BindView(R.id.opened_orders)
    TextView openedOrders;
    @BindView(R.id.closed_orders)
    TextView closedOrders;
    @BindView(R.id.empty_view)
    EmptyView emptyView;
    @BindView(R.id.loading)
    AVLoadingIndicatorView loading;
    @BindView(R.id.tabs_holder)
    View tabsHolder;
    private int pagePosition;
    private static final String TAG = "OnChange";
    private MainCategoriesResponse mainCategoriesResponse;

    public RequestFragment() {
        // Required empty public constructor
    }

    public static RequestFragment getInstance() {
        return new RequestFragment();
    }


    private final Observer<MainCategoriesResponse> getCategories = new Observer<MainCategoriesResponse>() {
        @Override
        public void onChanged(@Nullable MainCategoriesResponse mainCategoriesResponse) {
            Log.d(TAG, "onChanged: " + mainCategoriesResponse);
            loading.hide();
            if (mainCategoriesResponse != null) {
                if (mainCategoriesResponse.getCode() == 200) {
                    RequestFragment.this.mainCategoriesResponse = mainCategoriesResponse;
                    pager.setVisibility(View.VISIBLE);
                    tabsHolder.setVisibility(View.VISIBLE);
                    List<BaseFragment> fragments = new ArrayList<>();
                    fragments.add(AgencyFragment.getInstance(mainCategoriesResponse));
                    fragments.add(RequestContractFragment.newInstance(mainCategoriesResponse));
                    fragments.add(MarriageRequestFragment.newInctance(mainCategoriesResponse));


                    PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), fragments);
                    pager.setAdapter(adapter);
                    pager.setCurrentItem(0, false);
                    pager.setOffscreenPageLimit(3);
                    switch (pagePosition) {
                        case 0:
                            setupClosedOrdersViews();
                            break;
                        case 1:
                            setupOpenedOrdersViews();
                            break;
                        case 2:
                            setupNewOrdersViews();
                            break;
                    }
                } else {
                    emptyView.setVisibility(View.VISIBLE);
                }
            } else
                emptyView.setVisibility(View.VISIBLE);
        }
    };


    @Override
    public int getLayoutResource() {
        return R.layout.fragment_request;
    }

    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
        if ((getActivity()) != null) {
            ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.new_order), 0);
        }
        loading.show();
        DelegationViewModel delegationViewModel = ViewModelProviders.of(this).get(DelegationViewModel.class);
        delegationViewModel.getCategories(UserManager.getInstance().getUserToken(), this).observe(this, getCategories);
//        List<BaseFragment> fragments = new ArrayList<>();
//        fragments.add(new AgencyFragment());
//        fragments.add(new RequestContractFragment());
//        fragments.add(new MarriageRequestFragment());
//        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), fragments);
//        pager.setAdapter(adapter);
//        pager.setOffscreenPageLimit(3);
//        switch (pagePosition) {
//            case 0:
//                setupClosedOrdersViews();
//                break;
//            case 1:
//                setupOpenedOrdersViews();
//                break;
//            case 2:
//                setupNewOrdersViews();
//                break;
//        }

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.closed_orders, R.id.opened_orders, R.id.new_orders})
    void onChangePage(View view) {
        switch (view.getId()) {
            case R.id.new_orders:
                setupNewOrdersViews();
                break;
            case R.id.opened_orders:
                setupOpenedOrdersViews();
                break;
            case R.id.closed_orders:
                setupClosedOrdersViews();
                break;
        }
    }

    private void setupNewOrdersViews() {
        openedOrders.setBackgroundResource(R.drawable.rounded_corner);
        closedOrders.setBackgroundResource(R.drawable.rounded_corner);
        newOrders.setBackgroundResource(R.drawable.red_rounded_corner);
        openedOrders.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
        closedOrders.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
        newOrders.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        pager.setCurrentItem(2, false);
    }

    private void setupOpenedOrdersViews() {
        openedOrders.setBackgroundResource(R.drawable.red_rounded_corner);
        closedOrders.setBackgroundResource(R.drawable.rounded_corner);
        newOrders.setBackgroundResource(R.drawable.rounded_corner);
        openedOrders.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        closedOrders.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
        newOrders.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
        pager.setCurrentItem(1, false);
    }

    private void setupClosedOrdersViews() {
        openedOrders.setBackgroundResource(R.drawable.rounded_corner);
        closedOrders.setBackgroundResource(R.drawable.red_rounded_corner);
        newOrders.setBackgroundResource(R.drawable.rounded_corner);
        openedOrders.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
        closedOrders.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        newOrders.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
        pager.setCurrentItem(0, false);
    }

}
