package com.example.fly.mvvm;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.bottomnavigation.BottomNavigationBar;
import com.bottomnavigation.BottomNavigationItem;
import com.example.fly.mvvm.config.Constants;
import com.example.fly.mvvm.core.ui.fragment.HomeFragment;
import com.example.fly.mvvm.core.ui.fragment.MineFragment;
import com.example.fly.mvvm.core.ui.fragment.VideoFragment;
import com.example.fly.mvvm.core.ui.fragment.WorkFragment;
import com.example.fly.mvvm.utils.SystemUtil;
import com.example.fly.mvvm_library.base.BaseActivity;

public class MainActivity extends BaseActivity {

    private HomeFragment mHomeFragment;

    private WorkFragment mWorkFragment;

    private VideoFragment mVideoFragment;

    private MineFragment mMineFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        loadManager.showSuccess();
        //int Bottom tabBar
        initNavBar();
        //init fragment
        initFragment(0);
        showSystemParameter();
    }

    private void initNavBar() {
        BottomNavigationBar mBottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.tab_home_icon, R.string.home_title_name).setInactiveIconResource(R.mipmap.tab_home_icon_def))
                .addItem(new BottomNavigationItem(R.mipmap.tab_works_icon, R.string.work_title_name).setInactiveIconResource(R.mipmap.tab_works_icon_def))
                .addItem(new BottomNavigationItem(R.mipmap.tab_course_icon, R.string.video_title_name).setInactiveIconResource(R.mipmap.tab_course_icon_def))
                .addItem(new BottomNavigationItem(R.mipmap.tab_mine_icon, R.string.mine_title_name).setInactiveIconResource(R.mipmap.tab_mine_icon_def))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                initFragment(position);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

    private void initFragment(int i) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (i) {
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance();
                    fragmentTransaction.add(R.id.home_content, mHomeFragment, Constants.HOME_TAG);
                } else {
                    fragmentTransaction.show(mHomeFragment);
                }
                break;

            case 1:
                if (mWorkFragment == null) {
                    mWorkFragment = WorkFragment.newInstance();
                    fragmentTransaction.add(R.id.home_content, mWorkFragment, Constants.WORK_TAG);
                } else {
                    fragmentTransaction.show(mWorkFragment);
                }
                break;
            case 2:
                if (mVideoFragment == null) {
                    mVideoFragment = VideoFragment.newInstance();
                    fragmentTransaction.add(R.id.home_content, mVideoFragment, Constants.VIDEO_TAG);
                } else {
                    fragmentTransaction.show(mVideoFragment);
                }
                break;
            case 3:
                if (mMineFragment == null) {
                    mMineFragment = MineFragment.newInstance();
                    fragmentTransaction.add(R.id.home_content, mMineFragment, Constants.MINE_TAG);
                } else {
                    fragmentTransaction.show(mMineFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();

    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mHomeFragment != null) {
            fragmentTransaction.hide(mHomeFragment);
        }

        if (mWorkFragment != null) {
            fragmentTransaction.hide(mWorkFragment);
        }
        if (mVideoFragment != null) {
            fragmentTransaction.hide(mVideoFragment);
        }

        if (mMineFragment != null) {
            fragmentTransaction.hide(mMineFragment);
        }
    }

    private void showSystemParameter() {
        String TAG = "系统参数：";
        Log.e(TAG, "手机厂商：" + SystemUtil.getDeviceBrand());
        Log.e(TAG, "手机型号：" + SystemUtil.getSystemModel());
        Log.e(TAG, "手机当前系统语言：" + SystemUtil.getSystemLanguage());
        Log.e(TAG, "Android系统版本号：" + SystemUtil.getSystemVersion());
        Log.e(TAG, "手机IMEI：" + SystemUtil.getIMEI(getApplicationContext()));
    }
}