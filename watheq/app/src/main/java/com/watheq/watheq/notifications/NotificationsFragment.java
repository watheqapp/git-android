package com.watheq.watheq.notifications;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.watheq.watheq.R;
import com.watheq.watheq.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends BaseFragment {

    public static NotificationsFragment getInstance() {
        return new NotificationsFragment();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_notifications;
    }

    @Override
    public void inOnCreateView(View root, ViewGroup container, @Nullable Bundle savedInstanceState) {
//on create view
    }

}
