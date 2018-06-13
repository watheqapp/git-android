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
//import com.squareup.picasso.Picasso;
//import com.watheq.watheq.R;
//import com.watheq.watheq.model.Category;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
///**
// * Created by mahmoud.diab on 12/3/2017.
// */
//
//public class MainCategoriesListAdapter extends RecyclerView.Adapter<MainCategoriesListAdapter.CategoriesListHolder> {
//    private ArrayList<Category> categoriess;
//    private Context context;
//    private OnListItemClicked onListItemClicked;
//
//    public MainCategoriesListAdapter(Context context, OnListItemClicked onListItemClicked) {
//        this.context = context;
//        this.onListItemClicked = onListItemClicked;
//    }
//
//    void setProductList(final ArrayList<Category> categories) {
//        if (categoriess == null) {
//            this.categoriess = categories;
//            notifyItemRangeInserted(0, categoriess.size());
//        } else {
//            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
//                @Override
//                public int getOldListSize() {
//                    return categoriess.size();
//                }
//
//                @Override
//                public int getNewListSize() {
//                    return categories.size();
//                }
//
//                @Override
//                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//                    return categoriess.get(oldItemPosition).getName().equals(
//                            categories.get(newItemPosition).getName());
//                }
//
//                @Override
//                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//                    Category photo = categoriess.get(newItemPosition);
//                    Category old = categoriess.get(oldItemPosition);
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
//    @Override
//    public MainCategoriesListAdapter.CategoriesListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.main_categories_item, parent, false);
//
//        return new CategoriesListHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final CategoriesListHolder holder, int position) {
//        holder.delegationDesc.setText(categoriess.get(position).getDisc());
//        holder.delegationTitle.setText(categoriess.get(position).getName());
//        if (position == 0)
//            holder.delegationImage.setImageResource(R.drawable.ic_type1);
//        else if (position == 1)
//            holder.delegationImage.setImageResource(R.drawable.ic_type2);
//        else
//            holder.delegationImage.setImageResource(R.drawable.ic_type3);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onListItemClicked.onItemClicked(categoriess.get(holder.getAdapterPosition()));
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        if (categoriess != null)
//            return categoriess.size();
//        else return 0;
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
//        void onItemClicked(Category category);
//    }
//}
