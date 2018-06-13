package com.watheq.watheq.lawerList;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.watheq.watheq.R;
import com.watheq.watheq.model.Category;
import com.watheq.watheq.model.Lawer;
import com.watheq.watheq.utils.OnLoadMoreListener;
import com.watheq.watheq.utils.UserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by mahmoud.diab on 1/9/2018.
 */

public class LaywerListAdapter extends RecyclerView.Adapter {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private Context context;
    private List<Lawer> lawers;
    private OnListItemClicked onListItemClicked;
    private int visibleThreshold = 1;
    private int lastVisibleItem, totalItemCount;
    private boolean loading, setEnableLoadMore;
    private OnLoadMoreListener onLoadMoreListener;
    private RecyclerView recyclerView;

    public LaywerListAdapter(Context context, OnListItemClicked onListItemClicked, RecyclerView recyclerView) {
        this.context = context;
        this.onListItemClicked = onListItemClicked;
        this.recyclerView = recyclerView;
        lawers = new ArrayList<>();
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

    void setProductList(final List<Lawer> categories) {
        if (lawers == null) {
            this.lawers = categories;
            notifyItemRangeInserted(0, lawers.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return lawers.size();
                }

                @Override
                public int getNewListSize() {
                    return categories.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return lawers.get(oldItemPosition).getName().equals(
                            categories.get(newItemPosition).getName());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Lawer photo = lawers.get(newItemPosition);
                    Lawer old = lawers.get(oldItemPosition);
                    return photo.getName().equals(old.getName())
                            && Objects.equals(photo.getId(), old.getId());
                }
            });
            result.dispatchUpdatesTo(this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 > lawers.size() && setEnableLoadMore)
            return VIEW_PROG;
        else return VIEW_ITEM;

    }

    public void insertItems(List<Lawer> dataList) {

        this.lawers.addAll(dataList);
        this.notifyDataSetChanged();
        this.notifyItemRangeChanged(0, this.lawers.size());
        this.notifyItemRangeInserted(0, this.lawers.size());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.laywer_list_item, parent, false);
//
//        return new LaywerListHolder(view);

        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.laywer_list_item, parent, false);
            vh = new LaywerListHolder(view);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LaywerListHolder) {
            LaywerListHolder vh = (LaywerListHolder) holder;
            vh.lawyerName.setText(lawers.get(position).getName());
//            vh.star.setText(lawers.get(position).getId() + "");
            Picasso.with(context).load(lawers.get(position).getImage()).
                    placeholder(ContextCompat.getDrawable(context, R.drawable.ic_avatar)).into(vh.lawyerAvatar);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onListItemClicked.onItemClicked(lawers.get(holder.getAdapterPosition()));
                }
            });
        } else {
            ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        if (lawers != null) {
            if (setEnableLoadMore)
                return lawers.size() + 1;
            else
                return lawers.size();
        } else return 0;
    }

    class LaywerListHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lawyer_avatar)
        CircleImageView lawyerAvatar;
        @BindView(R.id.lawyer_name)
        TextView lawyerName;
        @BindView(R.id.star)
        TextView star;
        @BindView(R.id.auth)
        TextView auth;

        public LaywerListHolder(View itemView) {
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
        void onItemClicked(Lawer lawer);
    }
}
