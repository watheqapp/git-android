package com.watheq.watheq.myOrder;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.watheq.watheq.Constants;
import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.base.BaseHandlingErrors;
import com.watheq.watheq.model.Data;
import com.watheq.watheq.model.OrdersResponseModel;
import com.watheq.watheq.thread.ThreadActivity;
import com.watheq.watheq.utils.Errors;
import com.watheq.watheq.utils.OnLoadMoreListener;
import com.watheq.watheq.utils.UserManager;
import com.watheq.watheq.views.EmptyView;
import com.watheq.watheq.views.RecyclerViewEmptySupport;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClosedOrdersFragment extends BaseFragment implements OrdersListAdapter.OnListItemClicked,
        BaseHandlingErrors {


    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerFrameLayout;
    @BindView(R.id.recycler_view)
    RecyclerViewEmptySupport recyclerView;
    @BindView(R.id.empty_view)
    EmptyView emptyView;
    private OrdersListAdapter ordersListAdapter;
    private OrdersViewModel ordersViewModel;
    private int pageNum;
    public static String TAG = "ClosedOrdersFragment";

    public ClosedOrdersFragment() {
        // Required empty public constructor
    }

    private final Observer<OrdersResponseModel> getClosedOrders = new Observer<OrdersResponseModel>() {
        @Override
        public void onChanged(@Nullable OrdersResponseModel ordersResponseModel) {
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
            pageNum++;
            if (ordersResponseModel.getData() != null) {
                ordersListAdapter.insertItems(ordersResponseModel.getData());
                if (ordersResponseModel.getData().size() < 10) {
                    ordersListAdapter.setSetEnableLoadMore(false);
                } else {
                    ordersListAdapter.setSetEnableLoadMore(true);
                    ordersListAdapter.setLoaded();
                }
            } else {
                if (ordersListAdapter.getItemCount() <= 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setErrorText(R.string.no_closed_orders);
                } else {
                    ordersListAdapter.setSetEnableLoadMore(false);
                    ordersListAdapter.notifyDataSetChanged();
                }
            }
        }
    };


    @Override
    public int getLayoutResource() {
        return R.layout.fragment_closed_orders;
    }

    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        if (ordersListAdapter != null && ordersListAdapter.getItemCount() > 0) {
            ordersListAdapter.clearList();
            ordersListAdapter.setSetEnableLoadMore(false);
        }
        pageNum = 1;
        ordersViewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);
        ordersViewModel.getClosedOrders(UserManager.getInstance().getUserToken(), 1, 10, this).observe(this, getClosedOrders);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ordersListAdapter = new OrdersListAdapter(getContext(),
                ClosedOrdersFragment.this, recyclerView, TAG);
        recyclerView.setAdapter(ordersListAdapter);
        ordersListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                ordersViewModel.getClosedOrders(UserManager.getInstance().getUserToken(), pageNum, 10, ClosedOrdersFragment.this)
                        .observe(ClosedOrdersFragment.this, getClosedOrders);
            }
        });
        ordersListAdapter.initLoadMore();
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (((BaseActivity) getActivity()).isRequestedLawyer()) {
//            shimmerFrameLayout.startShimmerAnimation();
//            shimmerFrameLayout.setVisibility(View.VISIBLE);
//            pageNum = 1;
//            ordersViewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);
//            ordersViewModel.getClosedOrders(UserManager.getInstance().getUserToken(), 1, 10, this).observe(this, getClosedOrders);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setHasFixedSize(true);
//            ordersListAdapter = new OrdersListAdapter(getContext(),
//                    ClosedOrdersFragment.this, recyclerView, TAG);
//            recyclerView.setAdapter(ordersListAdapter);
//            ordersListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//                @Override
//                public void onLoadMore() {
//                    ordersViewModel.getClosedOrders(UserManager.getInstance().getUserToken(), pageNum, 10, ClosedOrdersFragment.this)
//                            .observe(ClosedOrdersFragment.this, getClosedOrders);
//                }
//            });
//            ordersListAdapter.initLoadMore();
//        }
    }

    @Override
    public void onItemClicked(Data data) {
        Intent intent = new Intent(getActivity(), ThreadActivity.class);
        intent.putExtra(Constants.USER_ID_EXTRA, data.getLawyer());
        intent.putExtra("orderId", String.valueOf(data.getId()));
        intent.putExtra("isClosed", true);
        startActivity(intent);
    }

    @Override
    public void onResponseFail(Errors code) {
        super.onResponseFail(code);
        ordersListAdapter.setSetEnableLoadMore(false);
        ordersListAdapter.notifyDataSetChanged();
        emptyView.setVisibility(View.VISIBLE);
        if (code == Errors.NO_INTERNET)
            emptyView.setErrorText(R.string.all_no_internet);
        else if (code == Errors.SERVER_ERROR)
            emptyView.setErrorText(R.string.something_wrong);
    }

}
