package com.watheq.watheq.delegation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.watheq.watheq.MainActivity;
import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.model.Category;
import com.watheq.watheq.model.Sub;
import com.watheq.watheq.views.RecyclerViewEmptySupport;

import butterknife.BindView;

/**
 * Created by mahmoud.diab on 12/7/2017.
 */

public class DelegationFragmentStepTwo extends BaseDelegate implements DelegationStepTwoAdapter.OnListItemClicked {

    private static final String CATEGORY_KEY = "category_key";
    @BindView(R.id.recycler_view)
    RecyclerViewEmptySupport recyclerView;


    public static DelegationFragmentStepTwo newInstance(Category category) {
        DelegationFragmentStepTwo delegationFragmentStepTwo = new DelegationFragmentStepTwo();
        Bundle args = new Bundle();
        args.putParcelable(CATEGORY_KEY, category);
        delegationFragmentStepTwo.setArguments(args);
        return delegationFragmentStepTwo;
    }

    @Override
    public int getLayoutResource() {
        return R.layout.delegation_fragment_step_two;
    }

    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        Category category = null;
        if (bundle != null) {
            category = bundle.getParcelable(CATEGORY_KEY);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        DelegationStepTwoAdapter delegationStepTwoAdapter = new DelegationStepTwoAdapter(getContext(), this);
        if (category != null) {
            if ((getActivity()) != null) {
                ((MainActivity) getActivity()).updateToolbarTitle(category.getName(), 50);
            }
            delegationStepTwoAdapter.setProductList(category.getSubs());
        }
        recyclerView.setAdapter(delegationStepTwoAdapter);

    }

    @Override
    public void onItemClicked(Sub sub) {
        if (sub.getHasSubs() == 1)
            pushFragment(DelegationFragmentStepThree.newInstance(sub), false);
    }
}
