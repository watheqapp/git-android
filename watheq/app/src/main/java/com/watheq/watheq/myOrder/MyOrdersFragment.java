package com.watheq.watheq.myOrder;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watheq.watheq.MainActivity;
import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.model.OrderLawyerResponse;
import com.watheq.watheq.views.NonSwipeableViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends BaseFragment {

    @BindView(R.id.pager)
    NonSwipeableViewPager pager;
    @BindView(R.id.new_orders)
    TextView newOrders;
    @BindView(R.id.opened_orders)
    TextView openedOrders;
    @BindView(R.id.closed_orders)
    TextView closedOrders;
    private int pagePosition;

    public static MyOrdersFragment getInstance() {
        return new MyOrdersFragment();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_prices;
    }

    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
        if ((getActivity()) != null) {
            ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.my_orders), 0);
        }
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new ClosedOrdersFragment());
        fragments.add(new OpenedOrdersFragment());
        fragments.add(new NewOrdersFragment());
        PagerAdapter adapter = new PagerAdapter(getChildFragmentManager(), fragments);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        setupNewOrdersViews();
//        switch (pagePosition) {
//            case 0:
//                setupClosedOrdersViews();
//                break;
//            case 1:
//                setupOpenedOrdersViews();
//                break;
//            case 2:
//
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
