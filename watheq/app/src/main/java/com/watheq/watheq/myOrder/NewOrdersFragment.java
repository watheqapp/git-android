package com.watheq.watheq.myOrder;


import android.app.Activity;
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
import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.base.BaseHandlingErrors;

import com.watheq.watheq.delegation.SearchingForLawyer;
import com.watheq.watheq.lawerList.LawerListActivity;
import com.watheq.watheq.model.Data;
import com.watheq.watheq.model.OrdersResponseModel;
import com.watheq.watheq.utils.Errors;
import com.watheq.watheq.utils.OnLoadMoreListener;
import com.watheq.watheq.utils.UserManager;
import com.watheq.watheq.views.EmptyView;
import com.watheq.watheq.views.RecyclerViewEmptySupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewOrdersFragment extends BaseFragment implements OrdersListAdapter.OnListItemClicked,
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
    public static String TAG = "NewOrdersFragment";

    public NewOrdersFragment() {
        // Required empty public constructor
    }

    private final Observer<OrdersResponseModel> getNewOrders = new Observer<OrdersResponseModel>() {
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
                    emptyView.setErrorText(R.string.no_new_orders);
                } else {
                    ordersListAdapter.setSetEnableLoadMore(false);
                    ordersListAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_new_orders;
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
        ordersViewModel.getNewOrders(UserManager.getInstance().getUserToken(), 1, 10, this).observe(this, getNewOrders);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        ordersListAdapter = new OrdersListAdapter(getContext(),
                NewOrdersFragment.this, recyclerView, TAG);
        recyclerView.setAdapter(ordersListAdapter);
        ordersListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                ordersViewModel.getNewOrders(UserManager.getInstance().getUserToken(), pageNum, 10, NewOrdersFragment.this)
                        .observe(NewOrdersFragment.this, getNewOrders);
            }
        });
        ordersListAdapter.initLoadMore();
    }

    @Override
    public void onResume() {
        super.onResume();
//        shimmerFrameLayout.startShimmerAnimation();
//        shimmerFrameLayout.setVisibility(View.VISIBLE);
//        pageNum = 1;
//        ordersViewModel = ViewModelProviders.of(this).get(OrdersViewModel.class);
//        ordersViewModel.getNewOrders(UserManager.getInstance().getUserToken(), 1, 10, this).observe(this, getNewOrders);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setHasFixedSize(true);
//        ordersListAdapter = new OrdersListAdapter(getContext(),
//                NewOrdersFragment.this, recyclerView, TAG);
//        recyclerView.setAdapter(ordersListAdapter);
//        ordersListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                ordersViewModel.getNewOrders(UserManager.getInstance().getUserToken(), pageNum, 10, NewOrdersFragment.this)
//                        .observe(NewOrdersFragment.this, getNewOrders);
//            }
//        });
//        ordersListAdapter.initLoadMore();
    }

    @Override
    public void onItemClicked(Data data) {
        Intent intent = new Intent(getContext(), LawerListActivity.class);
        intent.putExtra("orderId", data.getId());
        startActivityForResult(intent, 300);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == Activity.RESULT_OK) {
            pageNum = 1;
            ordersViewModel.getNewOrders(UserManager.getInstance().getUserToken(),
                    1, 10, this).observe(this, getNewOrders);
            ordersListAdapter.setSetEnableLoadMore(false);
            ordersListAdapter.clearList();
        }
    }
}
