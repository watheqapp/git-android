package com.watheq.watheq.notifications;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.watheq.watheq.R;
import com.watheq.watheq.lawerList.LaywerListAdapter;
import com.watheq.watheq.model.NotificationModel;
import com.watheq.watheq.utils.OnLoadMoreListener;
import com.watheq.watheq.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mahmoud.diab on 1/22/2018.
 */

public class NotificationListAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private Context context;
    private List<NotificationModel.NotificationData> notificationModels;
    private OnListItemClicked onListItemClicked;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading, setEnableLoadMore;
    private OnLoadMoreListener onLoadMoreListener;
    private RecyclerView recyclerView;

    public NotificationListAdapter(Context context, OnListItemClicked onListItemClicked, RecyclerView recyclerView) {
        this.context = context;
        this.onListItemClicked = onListItemClicked;
        this.recyclerView = recyclerView;
        notificationModels = new ArrayList<>();
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

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
//        if (position + 1 > lawers.size() && setEnableLoadMore)
//            return VIEW_PROG;
        return VIEW_ITEM;

    }


    public void insertItems(List<NotificationModel.NotificationData> dataList) {

        this.notificationModels.addAll(dataList);
        this.notifyDataSetChanged();
        this.notifyItemRangeChanged(0, this.notificationModels.size());
        this.notifyItemRangeInserted(0, this.notificationModels.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.laywer_list_item, parent, false);
//
//        return new LaywerListHolder(view);

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.notification_item_list, parent, false);
            vh = new NotificationListHolder(view);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NotificationListHolder) {
            NotificationListHolder vh = (NotificationListHolder) holder;
            vh.serviceNum.setText(notificationModels.get(position).getTitle());
            vh.lawyerName.setText(notificationModels.get(position).getContent());
            vh.serviceType.setText(notificationModels.get(position).getType());
            vh.timeAgo.setText(Utils.getTimeAgo(notificationModels.get(position).getCreatedAt(), context));
//            vh.star.setText(notificationModels.get(position).getId() + "");
//            Picasso.with(context).load(notificationModels.get(position).getImage()).
//                    placeholder(ContextCompat.getDrawable(context, R.drawable.ic_avatar)).into(vh.lawyerAvatar);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListItemClicked.onItemClicked(notificationModels.get(holder.getAdapterPosition()));
                }
            });
        } else {
            ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);
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

    @Override
    public int getItemCount() {
        if (notificationModels != null) {
            if (setEnableLoadMore)
                return notificationModels.size() + 1;
            else
                return notificationModels.size();
        } else return 0;
    }

    class NotificationListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lawyer_name)
        TextView lawyerName;
        @BindView(R.id.service_num)
        TextView serviceNum;
        @BindView(R.id.service_type)
        TextView serviceType;
        @BindView(R.id.time_ago)
        TextView timeAgo;

        public NotificationListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnListItemClicked {
        void onItemClicked(NotificationModel.NotificationData notificationModel);
    }
}
