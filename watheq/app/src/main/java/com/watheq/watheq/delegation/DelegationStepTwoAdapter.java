//package com.watheq.watheq.delegation;
//
//import android.content.Context;
//import android.support.v7.util.DiffUtil;
//import android.support.v7.widget.CardView;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.watheq.watheq.R;
//import com.watheq.watheq.model.Category;
//import com.watheq.watheq.model.Sub;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
///**
// * Created by mahmoud.diab on 12/7/2017.
// */
//
//public class DelegationStepTwoAdapter extends RecyclerView.Adapter<DelegationStepTwoAdapter.CategoriesListHolder> {
//
//    private final Context context;
//    private ArrayList<Sub> subArrayList;
//    private OnListItemClicked onListItemClicked;
//
//    void setProductList(final ArrayList<Sub> subs) {
//        if (subArrayList == null) {
//            this.subArrayList = subs;
//            notifyItemRangeInserted(0, subArrayList.size());
//        } else {
//            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
//                @Override
//                public int getOldListSize() {
//                    return subArrayList.size();
//                }
//
//                @Override
//                public int getNewListSize() {
//                    return subs.size();
//                }
//
//                @Override
//                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//                    return subArrayList.get(oldItemPosition).getName().equals(
//                            subArrayList.get(newItemPosition).getName());
//                }
//
//                @Override
//                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//                    Sub photo = subArrayList.get(newItemPosition);
//                    Sub old = subArrayList.get(oldItemPosition);
//                    return photo.getName().equals(old.getName())
//                            && Objects.equals(photo.getSubs(), old.getSubs())
//                            && Objects.equals(photo.getHasSubs(), old.getHasSubs())
//                            && photo.getCost() == old.getCost();
//                }
//            });
//            result.dispatchUpdatesTo(this);
//        }
//    }
//
//    public DelegationStepTwoAdapter(Context context, OnListItemClicked onListItemClicked) {
//        this.context = context;
//        this.onListItemClicked = onListItemClicked;
//    }
//
//    @Override
//    public CategoriesListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.main_categories_item, parent, false);
//
//        return new CategoriesListHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final CategoriesListHolder holder, int position) {
//        holder.delegationDesc.setText(subArrayList.get(position).getDisc());
//        holder.delegationTitle.setText(subArrayList.get(position).getName());
//        if (position == 0)
//            holder.delegationImage.setImageResource(R.drawable.ic_delegation_type1);
//        else if (position == 1)
//            holder.delegationImage.setImageResource(R.drawable.ic_delegation_type2);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onListItemClicked.onItemClicked(subArrayList.get(holder.getAdapterPosition()));
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return subArrayList.size();
//    }
//
//    class CategoriesListHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.delegation_image)
//        ImageView delegationImage;
//        @BindView(R.id.delegation_descreption)
//        TextView delegationDesc;
//        @BindView(R.id.delegation_title)
//        TextView delegationTitle;
//        @BindView(R.id.card_view)
//        CardView cardView;
//
//        public CategoriesListHolder(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//    public interface OnListItemClicked {
//        void onItemClicked(Sub sub);
//    }
//}
