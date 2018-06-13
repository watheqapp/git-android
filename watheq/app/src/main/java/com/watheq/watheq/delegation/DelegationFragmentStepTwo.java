//package com.watheq.watheq.delegation;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.watheq.watheq.MainActivity;
//import com.watheq.watheq.R;
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
//public class DelegationFragmentStepTwo extends BaseDelegate implements DelegationStepTwoAdapter.OnListItemClicked {
//
//    private static final String CATEGORY_KEY = "category_key";
//    @BindView(R.id.recycler_view)
//    RecyclerViewEmptySupport recyclerView;
//    Category category;
//
//    public static DelegationFragmentStepTwo newInstance(Category category) {
//        DelegationFragmentStepTwo delegationFragmentStepTwo = new DelegationFragmentStepTwo();
//        Bundle args = new Bundle();
//        args.putParcelable(CATEGORY_KEY, category);
//        delegationFragmentStepTwo.setArguments(args);
//        return delegationFragmentStepTwo;
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
//            category = bundle.getParcelable(CATEGORY_KEY);
//        }
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        DelegationStepTwoAdapter delegationStepTwoAdapter = new DelegationStepTwoAdapter(getContext(), this);
//        if (category != null) {
//            if ((getActivity()) != null) {
//                if (category.getId() == 1)
//                    ((MainActivity) getActivity()).updateToolbarTitle(category.getName(), 500 / 6);
//                else
//                    ((MainActivity) getActivity()).updateToolbarTitle(category.getName(), 500 / 5);
//            }
//            delegationStepTwoAdapter.setProductList(category.getSubs());
//        }
//        recyclerView.setAdapter(delegationStepTwoAdapter);
//
//    }
//
//    @Override
//    public void onItemClicked(Sub sub) {
//        if (sub.getHasSubs() == 1) {
//            sub.setDeliveryToHomeFees(category.getDeliveryToHomeFees());
//            pushFragment(DelegationFragmentStepThree.newInstance(sub), false);
//        } else {
//            sub.setDeliveryToHomeFees(category.getDeliveryToHomeFees());
//            sub.setHasNoSubs(true);
//            pushFragment(ClientInformationFragment.newInstance(sub), false);
//        }
//    }
//}
