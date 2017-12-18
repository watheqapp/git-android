package com.watheq.watheq.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.watheq.watheq.R;
import com.watheq.watheq.authentication.AuthenticationActivity;
import com.watheq.watheq.delegation.MainCategoriesListAdapter;
import com.watheq.watheq.utils.Errors;
import com.watheq.watheq.utils.UserManager;
import com.watheq.watheq.views.RecyclerViewEmptySupport;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.ButterKnife;

/**
 * Created by mahmoud.diab on 11/13/2017.
 */

public abstract class BaseFragment extends Fragment implements BaseHandlingErrors {

    private FragmentNavigation mFragmentNavigation;
    private BaseActivity parentActivity;

    public BaseActivity getParentActivity() {
        if (parentActivity != null)
            return parentActivity;
        else return null;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            mFragmentNavigation = (FragmentNavigation) context;
        }
        if (context instanceof BaseActivity) {
            parentActivity = (BaseActivity) context;
        }
    }

    public interface FragmentNavigation {
        void pushFragment(Fragment fragment, boolean isAnim);

        void clearStack();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, root);
        inOnCreateView(root, container, savedInstanceState);
        return root;
    }

    @LayoutRes
    public abstract int getLayoutResource();

    public abstract void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState);

    public void showNotification(@StringRes int msgRes) {
        if (parentActivity == null) return;
        parentActivity.showSnackBar(msgRes);
    }

    public void showNotification(String msg) {
        if (parentActivity == null) return;
        parentActivity.showSnackBar(msg);
    }

    @Override
    public void onResponseFail(Errors code) {
        if (code == Errors.UNAUTHORIZE) {
            UserManager.getInstance().logout();
            AuthenticationActivity.start(getContext());
            if (getActivity() != null)
                getActivity().finish();
            return;
        }
        if (getView() != null && getView().findViewById(R.id.recycler_view) != null) {
            RecyclerViewEmptySupport recyclerViewEmptySupport = getView().findViewById(R.id.recycler_view);
            recyclerViewEmptySupport.setAdapter(new MainCategoriesListAdapter(getContext(), null));
            recyclerViewEmptySupport.setEmptyView(getView().findViewById(R.id.empty_view));
        }

        if (getView().findViewById(R.id.shimmer_view_container) != null) {
            ShimmerFrameLayout shimmerFrameLayout = getView().findViewById(R.id.shimmer_view_container);
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
        }

        if (getView().findViewById(R.id.confirm_btn) != null) {
            CircularProgressButton circularProgressButton = getView().findViewById(R.id.confirm_btn);
            circularProgressButton.revertAnimation();
        }

        if (code == Errors.NO_INTERNET)
            showNotification(getString(R.string.all_no_internet));
        else if (code == Errors.SERVER_ERROR)
            showNotification(getString(R.string.something_wrong));
    }

    public void pushFragment(Fragment fragment, boolean isAnim) {
        if (mFragmentNavigation != null)
            mFragmentNavigation.pushFragment(fragment, isAnim);
    }

    public void errorHandlerMsg() {

    }
}
