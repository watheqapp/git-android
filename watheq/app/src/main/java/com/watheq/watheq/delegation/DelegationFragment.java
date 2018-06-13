//package com.watheq.watheq.delegation;
//
//
//import android.arch.lifecycle.Observer;
//import android.arch.lifecycle.ViewModelProviders;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.facebook.shimmer.ShimmerFrameLayout;
//import com.watheq.watheq.MainActivity;
//import com.watheq.watheq.R;
//import com.watheq.watheq.base.BaseFragment;
//import com.watheq.watheq.model.Category;
//import com.watheq.watheq.model.ClientInfoModel;
//import com.watheq.watheq.model.MainCategoriesResponse;
//import com.watheq.watheq.utils.UserManager;
//import com.watheq.watheq.views.EmptyView;
//import com.watheq.watheq.views.RecyclerViewEmptySupport;
//
//import butterknife.BindView;
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class DelegationFragment extends BaseFragment implements MainCategoriesListAdapter.OnListItemClicked {
//
//    private static final String TAG = "DelegationFragment";
//    private MainCategoriesListAdapter categoriesListAdapter;
//    private MainCategoriesResponse mainCategoriesResponse;
//    @BindView(R.id.shimmer_view_container)
//    ShimmerFrameLayout shimmerFrameLayout;
//    @BindView(R.id.recycler_view)
//    RecyclerViewEmptySupport recyclerView;
//    @BindView(R.id.empty_view)
//    EmptyView emptyView;
//
//    public static DelegationFragment getInstance() {
//        return new DelegationFragment();
//    }
//
//    private final Observer<MainCategoriesResponse> getCategories = new Observer<MainCategoriesResponse>() {
//        @Override
//        public void onChanged(@Nullable MainCategoriesResponse mainCategoriesResponse) {
//            Log.d(TAG, "onChanged: " + mainCategoriesResponse);
//            DelegationFragment.this.mainCategoriesResponse = mainCategoriesResponse;
//            shimmerFrameLayout.stopShimmerAnimation();
//            shimmerFrameLayout.setVisibility(View.GONE);
//            if (categoriesListAdapter == null) {
//                categoriesListAdapter = new MainCategoriesListAdapter(getContext(), DelegationFragment.this);
//                if (mainCategoriesResponse != null) {
//                    categoriesListAdapter.setProductList(mainCategoriesResponse.getData().getCategories());
//                }
//                recyclerView.setAdapter(categoriesListAdapter);
//            }
//
//        }
//    };
//
//    @Override
//    public int getLayoutResource() {
//        return R.layout.fragment_delegation;
//    }
//
//    @Override
//    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
//        setRetainInstance(true);
//        if ((getActivity()) != null) {
//            ((MainActivity) getActivity()).updateToolbarTitle(getString(R.string.wathaq), 0);
//        }
//        DelegationViewModel delegationViewModel = ViewModelProviders.of(this).get(DelegationViewModel.class);
//        delegationViewModel.getCategories(UserManager.getInstance().getUserToken(), this).observe(this, getCategories);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);
//        if (mainCategoriesResponse != null) {
//            recyclerView.setAdapter(categoriesListAdapter);
//            shimmerFrameLayout.setVisibility(View.GONE);
//        }
//    }
//
//
//    @Override
//    public void onItemClicked(Category category) {
//        if (category.getHasSubs() == 1) {
//            category.setDeliveryToHomeFees(mainCategoriesResponse.getData().getDeliverToHomeFees());
//            pushFragment(DelegationFragmentStepTwo.newInstance(category), false);
//        }else {
//            ClientInfoModel clientInfoModel = new ClientInfoModel();
//            clientInfoModel.setCost(category.getCost());
//            clientInfoModel.setDeliveryToHomeFees(mainCategoriesResponse.getData().getDeliverToHomeFees());
//            clientInfoModel.setTitle(category.getName());
//            clientInfoModel.setId(category.getId());
//            clientInfoModel.setAuth(true);
//
//            pushFragment(DeliveryPlaceFragment.newInstance(clientInfoModel), false);
//        }
//    }
//}
