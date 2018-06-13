//package com.watheq.watheq.delegation;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.watheq.watheq.MainActivity;
//import com.watheq.watheq.R;
//import com.watheq.watheq.base.BaseFragment;
//import com.watheq.watheq.model.Category;
//import com.watheq.watheq.model.Sub;
//import com.watheq.watheq.views.RecyclerViewEmptySupport;
//
//import butterknife.BindView;
//
///**
// * Created by mahmoud.diab on 12/7/2017.
// */
//
//public class DelegationFragmentStepThree extends BaseDelegate implements DelegationStepTwoAdapter.OnListItemClicked {
//
//    private static final String CATEGORY_KEY = "category_key";
//    @BindView(R.id.recycler_view)
//    RecyclerViewEmptySupport recyclerView;
//    private Sub sub;
//
//    public static DelegationFragmentStepThree newInstance(Sub sub) {
//        DelegationFragmentStepThree delegationFragmentStepThree = new DelegationFragmentStepThree();
//        Bundle args = new Bundle();
//        args.putParcelable(CATEGORY_KEY, sub);
//        delegationFragmentStepThree.setArguments(args);
//        return delegationFragmentStepThree;
//    }
//
//    @Override
//    public int getLayoutResource() {
//        return R.layout.delegation_fragment_step_two;
//    }
//
//    @Override
//    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        Bundle bundle = this.getArguments();
//
//        if (bundle != null) {
//            sub = bundle.getParcelable(CATEGORY_KEY);
//        }
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        DelegationStepTwoAdapter delegationStepTwoAdapter = new DelegationStepTwoAdapter(getContext(), this);
//        if (sub != null) {
//            if ((getActivity()) != null) {
//                ((MainActivity) getActivity()).updateToolbarTitle(sub.getName(), (500 / 6) * 2);
//            }
//            delegationStepTwoAdapter.setProductList(sub.getSubs());
//        }
//        recyclerView.setAdapter(delegationStepTwoAdapter);
//
//
//    }
//
//    @Override
//    public void onItemClicked(Sub subCategory) {
//        subCategory.setDeliveryToHomeFees(sub.getDeliveryToHomeFees());
//        pushFragment(ClientInformationFragment.newInstance(subCategory), false);
//    }
//}
