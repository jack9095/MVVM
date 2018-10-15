package com.example.fly.mvvm_library.stateview;

import com.example.fly.mvvm_library.R;
import com.tqzhang.stateview.stateview.BaseStateControl;



public class LoadingState extends BaseStateControl {
    @Override
    protected int onCreateView() {
        return R.layout.loading_view;
    }

    @Override
    public boolean isVisible() {
        return super.isVisible();
    }

}
