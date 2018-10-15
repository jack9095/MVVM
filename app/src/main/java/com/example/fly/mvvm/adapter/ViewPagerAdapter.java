package com.example.fly.mvvm.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.fly.mvvm_library.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;



public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> fragments;

    private List<String> mTitles;


    public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> lists, List<String> titles) {
        super(fm);
        fragments = new ArrayList<>();
        mTitles = new ArrayList<>();
        fragments.addAll(lists);
        mTitles.addAll(titles);
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragments.get(position);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }
}
