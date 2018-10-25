package com.example.fly.loadstate.fragment;

import android.os.Bundle;
import android.os.Handler;

import com.example.fly.loadstate.R;
import com.example.fly.loadstate.view.ErrorState;
import com.example.fly.loadstate.view.LoadingState;


/**
 */
public class Fragment3 extends BaseFragment {
    @Override
    protected void initView(Bundle savedInstanceState) {
        loadManager.showStateView(ErrorState.class, "3");


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_3;
    }

    @Override
    protected void onStateRefresh() {
        loadManager.showStateView(LoadingState.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadManager.showSuccess();
            }
        }, 3000);
    }
}
