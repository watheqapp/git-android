package com.watheq.watheq.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.watheq.watheq.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mahmoud.diab on 12/21/2017.
 */

public class LoadMoreRecyclerView extends LinearLayout {
    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private View emptyView;
    @BindView(R.id.progressBar1)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private boolean isLoadMoreEnabled;

    final private RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };

    public LoadMoreRecyclerView(Context context) {
        super(context);
        initialize();
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }


    private void initialize() {
        inflate(getContext(), R.layout.load_more_recyclerview, this);
        ButterKnife.bind(this);

    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        recyclerView.setHasFixedSize(hasFixedSize);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    private void init() {
        inflate(getContext(), R.layout.load_more_recyclerview, this);
        ButterKnife.bind(this);
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView,
                                       int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager
                            .findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null && isLoadMoreEnabled) {
                            onLoadMoreListener.onLoadMore();
                            showLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    public void setEnableLoadMore(boolean isLoadMoreEnabled) {
        this.isLoadMoreEnabled = isLoadMoreEnabled;
    }

    public void showLoadMore() {
        progressBar.setVisibility(VISIBLE);
        progressBar.setIndeterminate(true);
    }

    public void hideLoadMore() {
        progressBar.setVisibility(GONE);
    }

    void checkIfEmpty() {
        if (emptyView != null && recyclerView.getAdapter() != null) {
            final boolean emptyViewVisible = recyclerView.getAdapter().getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? VISIBLE : GONE);
            setVisibility(emptyViewVisible ? GONE : VISIBLE);
        }
    }

//    @Override
//    public void setAdapter(Adapter adapter) {
//        final Adapter oldAdapter = getAdapter();
//        if (oldAdapter != null) {
//            oldAdapter.unregisterAdapterDataObserver(observer);
//        }
//        super.setAdapter(adapter);
//        if (adapter != null) {
//            adapter.registerAdapterDataObserver(observer);
//        }
//
//        checkIfEmpty();
//    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void setLoaded() {
        loading = false;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
