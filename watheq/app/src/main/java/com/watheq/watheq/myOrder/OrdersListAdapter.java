package com.watheq.watheq.myOrder;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.watheq.watheq.R;
import com.watheq.watheq.model.Data;
import com.watheq.watheq.model.OrdersResponseModel;
import com.watheq.watheq.utils.OnLoadMoreListener;
import com.watheq.watheq.utils.OrdersDiffCallback;
import com.watheq.watheq.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mahmoud.diab on 12/20/2017.
 */

public class OrdersListAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private List<Data> responseModelss = new ArrayList<>();
    private Context context;
    private OnListItemClicked onListItemClicked;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading, setEnableLoadMore;
    private OnLoadMoreListener onLoadMoreListener;
    private RecyclerView recyclerView;
    private String tag;

    public OrdersListAdapter(Context context, OnListItemClicked onListItemClicked
            , RecyclerView recyclerView, String tag) {
        this.context = context;
        this.onListItemClicked = onListItemClicked;
        this.recyclerView = recyclerView;
        this.tag = tag;
    }

    public void initLoadMore() {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();

            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading &&
                                    setEnableLoadMore && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();

            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading &&
                                    setEnableLoadMore && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }

    public void setLoaded() {
        loading = false;
    }


    public void setSetEnableLoadMore(boolean setEnableLoadMore) {
        this.setEnableLoadMore = setEnableLoadMore;
    }

    public void clearList() {
        responseModelss.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 > responseModelss.size() && setEnableLoadMore)
            return VIEW_PROG;
        else return VIEW_ITEM;

    }

    public void insertItems(List<Data> dataList) {

        this.responseModelss.addAll(dataList);
        this.notifyDataSetChanged();
        this.notifyItemRangeChanged(0, this.responseModelss.size());
        this.notifyItemRangeInserted(0, this.responseModelss.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.oreders_list_item, parent, false);
            vh = new OrdersListHolder(view);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrdersListHolder) {
            final OrdersListHolder ordersListHolder = (OrdersListHolder) holder;
            ordersListHolder.timeAgo.setText(Utils.getTimeAgo(responseModelss.get(position).getCreatedAt(), context));
            ordersListHolder.serviceNum.setText(context.getString(R.string.service_num_holder) + " # " + responseModelss.get(position).getId());
            if (responseModelss.get(position).getLawyer() != null &&
                    responseModelss.get(position).getLawyer().getImage() != null)
                Picasso.with(context).load(responseModelss.get(position).getLawyer().getImage()).
                        placeholder(ContextCompat.getDrawable(context, R.drawable.ic_avatar)).into(ordersListHolder.lawyerAvatar);
            ordersListHolder.serviceType.setText(responseModelss.get(position).getCategory().getName());
            if (tag.equals(NewOrdersFragment.TAG)) {
                ordersListHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onListItemClicked.onItemClicked(responseModelss.get(ordersListHolder.getAdapterPosition()));
                    }
                });
                ordersListHolder.select.setVisibility(View.VISIBLE);
            }
            if (tag.equals(OpenedOrdersFragment.TAG)) {
                ordersListHolder.lunchChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onListItemClicked.onItemClicked(responseModelss.get(ordersListHolder.getAdapterPosition()));
                    }
                });
                if (responseModelss.get(position).getLawyer() != null)
                    ordersListHolder.lawyerName.setText(responseModelss.get(position).getLawyer().getName());
            }
            if (tag.equals(ClosedOrdersFragment.TAG)) {
                ordersListHolder.lunchChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onListItemClicked.onItemClicked(responseModelss.get(ordersListHolder.getAdapterPosition()));
                    }
                });
                if (responseModelss.get(position).getLawyer() != null)
                    ordersListHolder.lawyerName.setText(responseModelss.get(position).getLawyer().getName());
            }
        } else {
            ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemCount() {
        if (responseModelss != null) {
            if (setEnableLoadMore)
                return responseModelss.size() + 1;
            else
                return responseModelss.size();
        } else return 0;
    }

    class OrdersListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lawyer_avatar)
        CircleImageView lawyerAvatar;
        @BindView(R.id.lunch_chat)
        CircleImageView lunchChat;
        @BindView(R.id.lawyer_name)
        TextView lawyerName;
        @BindView(R.id.service_num)
        TextView serviceNum;
        @BindView(R.id.service_type)
        TextView serviceType;
        @BindView(R.id.time_ago)
        TextView timeAgo;
        @BindView(R.id.select)
        TextView select;

        public OrdersListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar1)
        ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnListItemClicked {
        void onItemClicked(Data data);
    }
}