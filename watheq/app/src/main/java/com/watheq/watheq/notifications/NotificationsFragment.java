package com.watheq.watheq.notifications;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.watheq.watheq.MainActivity;
import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.model.NotificationModel;
import com.watheq.watheq.myOrder.OrdersListAdapter;
import com.watheq.watheq.utils.Errors;
import com.watheq.watheq.utils.OnLoadMoreListener;
import com.watheq.watheq.utils.UserManager;
import com.watheq.watheq.views.EmptyView;
import com.watheq.watheq.views.RecyclerViewEmptySupport;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends BaseFragment implements NotificationListAdapter.OnListItemClicked {

    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerFrameLayout;
    @BindView(R.id.recycler_view)
    RecyclerViewEmptySupport recyclerView;
    @BindView(R.id.empty_view)
    EmptyView emptyView;
    private NotificationListAdapter notificationListAdapter;
    private NotificationViewModel viewModel;

    private final Observer<List<NotificationModel.NotificationData>> getNotificationList =
            new Observer<List<NotificationModel.NotificationData>>() {
                @Override
                public void onChanged(@Nullable List<NotificationModel.NotificationData> ordersResponseModel) {
                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);
//            pageNum++;
                    if (ordersResponseModel != null && !ordersResponseModel.isEmpty()) {
                        notificationListAdapter.insertItems(ordersResponseModel);
                    } else {
                        emptyView.setVisibility(View.VISIBLE);
                        emptyView.setErrorText(R.string.no_closed_orders);
                    }
                }
            };

    public static NotificationsFragment getInstance() {
        return new NotificationsFragment();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_notifications;
    }

    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
        emptyView.setErrorText(R.string.no_notification);
        ((MainActivity) getParentActivity()).updateToolbarTitle(getString(R.string.notification), 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
//        pageNum = 1;
        viewModel = ViewModelProviders.of(this).get(NotificationViewModel.class);
        viewModel.getNotificationList(UserManager.getInstance().getUserToken(), this).observe(this, getNotificationList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        notificationListAdapter = new NotificationListAdapter(getContext(),
                this, recyclerView);
        recyclerView.setAdapter(notificationListAdapter);

    }

    @Override
    public void onItemClicked(NotificationModel.NotificationData notificationModel) {

    }

    @Override
    public void onResponseFail(Errors code) {
        super.onResponseFail(code);
//        ordersListAdapter.setSetEnableLoadMore(false);
        notificationListAdapter.notifyDataSetChanged();
        emptyView.setVisibility(View.VISIBLE);
        if (code == Errors.NO_INTERNET)
            emptyView.setErrorText(R.string.all_no_internet);
        else if (code == Errors.SERVER_ERROR)
            emptyView.setErrorText(R.string.something_wrong);
    }
}
