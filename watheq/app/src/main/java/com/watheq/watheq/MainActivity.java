package com.watheq.watheq;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.watheq.watheq.base.BaseActivity;
import com.watheq.watheq.base.BaseFragment;
import com.watheq.watheq.delegation.BaseDelegate;
import com.watheq.watheq.delegation.DelegationFragment;
import com.watheq.watheq.account.MyAccountFragment;
import com.watheq.watheq.notifications.NotificationsFragment;
import com.watheq.watheq.prices.MyOrdersFragment;
import com.watheq.watheq.utils.FragmentHistory;
import com.watheq.watheq.utils.Utils;
import com.watheq.watheq.views.FragNavController;

import butterknife.BindArray;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    private int[] mTabIconsSelected = {
            R.drawable.tab_bar_2_active,
            R.drawable.tab_bar1_active,
            R.drawable.tab_bar_alert_active,
            R.drawable.tab_bar_account_active};

    private int[] mTabIconsNotSelected = {
            R.drawable.tab_bar_2,
            R.drawable.tab_bar1,
            R.drawable.tab_bar_alert,
            R.drawable.tab_bar_account
    };

    @BindArray(R.array.tab_name)
    String[] tabs;
    @BindView(R.id.bottom_tab_layout)
    TabLayout bottomTabLayout;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.progress)
    ContentLoadingProgressBar progressBar;
    @BindView(R.id.progress_container)
    View progressContainer;

    private FragNavController mNavController;
    private FragmentHistory fragmentHistory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initTab();

        fragmentHistory = new FragmentHistory();


        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.content_frame)
                .transactionListener(this)
                .rootFragmentListener(this, tabs.length)
                .build();

        progressBar.setMax(100 * 5);
        switchTab(0);

        bottomTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                fragmentHistory.push(tab.getPosition());

                switchTab(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // not used
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                mNavController.clearStack();

                switchTab(tab.getPosition());


            }
        });

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);


    }

    @Override
    public void clean() {
// on destroy
    }

    @Override
    public int myView() {
        return R.layout.activity_main;
    }


    private void initTab() {
        if (bottomTabLayout != null) {
            for (int i = 0; i < tabs.length; i++) {
                bottomTabLayout.addTab(bottomTabLayout.newTab());
                TabLayout.Tab tab = bottomTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(getTabView(i));
            }
        }
    }

    private View getTabView(int position) {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.tab_item_bottom, null);
        ImageView icon = view.findViewById(R.id.tab_icon);
        TextView title = view.findViewById(R.id.title);
        title.setText(tabs[position]);
        icon.setImageDrawable(Utils.setDrawableSelector(MainActivity.this, mTabIconsNotSelected[position], mTabIconsSelected[position]));
        return view;
    }

    private void switchTab(int position) {
        mNavController.switchTab(position);
        if (mNavController.getCurrentFrag() instanceof MyAccountFragment)
            toolbar.setVisibility(View.GONE);
        else
            toolbar.setVisibility(View.VISIBLE);
        if (mNavController.getCurrentFrag() instanceof BaseDelegate) {
            progressContainer.setVisibility(View.VISIBLE);
        } else
            progressContainer.setVisibility(View.GONE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (!mNavController.isRootFragment()) {
            mNavController.popFragment();
        } else {

            if (fragmentHistory.isEmpty()) {
                super.onBackPressed();
            } else {


                if (fragmentHistory.getStackSize() > 1) {

                    int position = fragmentHistory.popPrevious();

                    switchTab(position);

                    updateTabSelection(position);

                } else {
                    switchTab(0);

                    updateTabSelection(0);

                    fragmentHistory.emptyStack();

                }
            }

        }
        if (mNavController.getCurrentFrag() instanceof MyAccountFragment)
            toolbar.setVisibility(View.GONE);
        else
            toolbar.setVisibility(View.VISIBLE);
        if (mNavController.getCurrentFrag() instanceof BaseDelegate) {
            progressContainer.setVisibility(View.VISIBLE);
        } else
            progressContainer.setVisibility(View.GONE);
    }


    private void updateTabSelection(int currentTab) {

        for (int i = 0; i < tabs.length; i++) {
            TabLayout.Tab selectedTab = bottomTabLayout.getTabAt(i);
            if (currentTab != i) {
                selectedTab.getCustomView().setSelected(false);
            } else {
                selectedTab.getCustomView().setSelected(true);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNavController != null) {
            mNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void pushFragment(Fragment fragment, boolean isAnim) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment, isAnim);
            if (mNavController.getCurrentFrag() instanceof MyAccountFragment)
                toolbar.setVisibility(View.GONE);
            else
                toolbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void clearStack() {
        if (mNavController != null)
            fragmentHistory.popPrevious();
    }


    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {

            updateToolbar();

        }
    }

    private void updateToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        getSupportActionBar().setDisplayShowHomeEnabled(!mNavController.isRootFragment());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }


    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {

            updateToolbar();

        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {

            case FragNavController.TAB1:
                return DelegationFragment.getInstance();
            case FragNavController.TAB2:
                return MyOrdersFragment.getInstance();
            case FragNavController.TAB3:
                return NotificationsFragment.getInstance();
            case FragNavController.TAB4:
                return MyAccountFragment.getInstance();
            default:
                return DelegationFragment.getInstance();
        }
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }


    public void updateToolbarTitle(String title, int progress) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");
        toolbarTitle.setText(title);
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (currentFragment instanceof BaseDelegate) {
            progressContainer.setVisibility(View.VISIBLE);
            setProgressAnimate(progressBar, progress);
        } else
            progressContainer.setVisibility(View.GONE);
    }

    private void setProgressMax(ContentLoadingProgressBar pb, int max) {
        pb.setMax(max * 100);
    }

    private void setProgressAnimate(ContentLoadingProgressBar pb, int progressTo) {
        ObjectAnimator animation = ObjectAnimator.ofInt(pb, "progress", pb.getProgress(), progressTo);
        animation.setDuration(500);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment frg = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (frg != null) {
            frg.onActivityResult(requestCode, resultCode, data);
        }
    }

}
