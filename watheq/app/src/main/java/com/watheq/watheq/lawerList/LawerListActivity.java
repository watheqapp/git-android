package com.watheq.watheq.lawerList;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.watheq.watheq.Constants;
import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseActivity;
import com.watheq.watheq.model.Lawer;
import com.watheq.watheq.model.LawyerListResponse;
import com.watheq.watheq.model.OrderLawyerResponse;
import com.watheq.watheq.thread.ThreadActivity;
import com.watheq.watheq.utils.OnLoadMoreListener;
import com.watheq.watheq.utils.UserManager;
import com.watheq.watheq.views.EmptyView;
import com.watheq.watheq.views.RecyclerViewEmptySupport;

import butterknife.BindView;

public class LawerListActivity extends BaseActivity implements LaywerListAdapter.OnListItemClicked {
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout shimmerFrameLayout;
    @BindView(R.id.recycler_view)
    RecyclerViewEmptySupport recyclerView;
    @BindView(R.id.empty_view)
    EmptyView emptyView;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    private int orderId;

    private LaywerListAdapter laywerListAdapter;
    private LawyerViewModel lawyerViewModel;
    private boolean isChoseLawyer;
    private int pageNum = 1;

    private final Observer<LawyerListResponse> lawyerListResponseObserver = new Observer<LawyerListResponse>() {
        @Override
        public void onChanged(@Nullable LawyerListResponse ordersResponseModel) {
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
            pageNum++;
            if (ordersResponseModel.getLawers() != null && !ordersResponseModel.getLawers().isEmpty()) {

                laywerListAdapter.insertItems(ordersResponseModel.getLawers());
                if (ordersResponseModel.getLawers().size() < 10) {
                    laywerListAdapter.setSetEnableLoadMore(false);
                } else {
                    laywerListAdapter.setSetEnableLoadMore(true);
                    laywerListAdapter.setLoaded();
                }
            } else {
                if (laywerListAdapter.getItemCount() <= 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setErrorText(R.string.no_lawyers);
                } else {
                    laywerListAdapter.setSetEnableLoadMore(false);
                    laywerListAdapter.notifyDataSetChanged();
                }
            }
        }
    };

    private final Observer<OrderLawyerResponse> orderLawyer = new Observer<OrderLawyerResponse>() {
        @Override
        public void onChanged(@Nullable OrderLawyerResponse ordersResponseModel) {
            if (ordersResponseModel.getData() != null && ordersResponseModel.getCode() == 200) {
                isChoseLawyer = true;
                Intent intent = new Intent(LawerListActivity.this, ThreadActivity.class);
                intent.putExtra(Constants.USER_ID_EXTRA, ordersResponseModel.getData().getLawyer());
                intent.putExtra("orderId", String.valueOf(orderId));
                intent.putExtra("fromList", true);
                startActivityForResult(intent, 300);
            } else {
                showSnackBar(ordersResponseModel.getMessage());
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbarTitle.setText(getString(R.string.choose_laywer));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        laywerListAdapter = new LaywerListAdapter(this, this, recyclerView);
        lawyerViewModel = ViewModelProviders.of(this).get(LawyerViewModel.class);
        orderId = getIntent().getIntExtra("orderId", 0);
        lawyerViewModel.getLawyerList(UserManager.getInstance().getUserToken()
                , orderId, pageNum, 10).observe(this, lawyerListResponseObserver);
        recyclerView.setAdapter(laywerListAdapter);

        laywerListAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                lawyerViewModel.getLawyerList(UserManager.getInstance().getUserToken()
                        , orderId, pageNum, 10).observe(LawerListActivity.this, lawyerListResponseObserver);
            }
        });
        laywerListAdapter.initLoadMore();


    }


    @Override
    public void clean() {

    }

    @Override
    public int myView() {
        return R.layout.activity_lawer_list;
    }

    @Override
    public void onItemClicked(Lawer lawer) {
        lawyerViewModel.orderLawyer(UserManager.getInstance().getUserToken(),
                orderId, lawer.getId()).observe(this, orderLawyer);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == Activity.RESULT_OK) {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            isRequestedLawyer(true);
            finish();
        }
    }
}
