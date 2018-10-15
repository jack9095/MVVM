package com.example.fly.mvvm.base;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fly.mvvm.R;
import com.example.fly.mvvm.adapter.ViewPagerAdapter;
import com.example.fly.mvvm.widget.NestedViewPager;
import com.example.fly.mvvm_library.base.AbsLifecycleFragment;
import com.example.fly.mvvm_library.base.AbsViewModel;
import com.example.fly.mvvm_library.base.BaseFragment;
import com.example.fly.mvvm_library.stateview.LoadingState;
import com.flyco.tablayout.SlidingTabLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public abstract class BaseViewPagerFragment<T extends AbsViewModel> extends AbsLifecycleFragment<T> {

    protected SlidingTabLayout mTabLayout;

    protected NestedViewPager mViewPager;

    protected RelativeLayout mTitleBar;

    protected TextView mTitle;

    protected ViewPagerAdapter adapter;

    protected List<BaseFragment> mFragments;

    protected List<String> mTitles;

    protected String[] mArrTitles;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_viewpager;
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        loadManager.showStateView(LoadingState.class);
        mTabLayout = getViewById(R.id.tab_layout);
        mViewPager = getViewById(R.id.view_pager);
        mTitleBar = getViewById(R.id.rl_title_bar);
        mTitle = getViewById(R.id.tv_title);
        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();
    }

    /**
     * init adapter
     */
    protected void setAdapter() {
        mTitles.addAll(Arrays.asList(createPageTitle()));
        adapter = new ViewPagerAdapter(getChildFragmentManager(), createFragments(), mTitles);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(mTitles.size());
        mTabLayout.setViewPager(mViewPager, mTitles.toArray(createPageTitle()));
    }

    /**
     * create ViewPager title
     *
     * @return String[]
     */
    protected abstract String[] createPageTitle();

    /**
     * create Fragment
     *
     * @return List<BaseFragment>
     */
    protected abstract List<BaseFragment> createFragments();


    /**
     * set title
     * @param titleName
     */
    protected void setTitle(String titleName) {
        mTitleBar.setVisibility(View.VISIBLE);
        mTitle.setText(titleName);
    }
}
