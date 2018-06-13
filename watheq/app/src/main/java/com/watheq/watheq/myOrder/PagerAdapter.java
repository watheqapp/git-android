package com.watheq.watheq.myOrder;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.watheq.watheq.base.BaseFragment;

import java.util.List;

/**
 * Created by mahmoud.diab on 12/18/2017.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragments;

    public PagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
