package com.watheq.watheq.account;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.setting.SettingFragment;
import com.watheq.watheq.utils.UserManager;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends BaseFragment {

    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.fmc_profile_image)
    CircleImageView profileImage;


    public static MyAccountFragment getInstance() {
        return new MyAccountFragment();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_my_account;
    }

    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
        //on create view
        collapsingToolbarLayout.setTitle(UserManager.getInstance().getUserData().getName());
        Picasso.with(getContext()).load(UserManager.getInstance().getUserData().getImage()).
                placeholder(ContextCompat.getDrawable(getContext(), R.drawable.ic_avatar)).into(profileImage);
    }

    @OnClick(R.id.setting)
    void onSettingClicked() {
        pushFragment(new SettingFragment(), false);
    }

}
