package com.example.fly.loadstate.view;

import com.example.fly.loadstate.R;
import com.fly.stateview.stateview.BaseStateControl;

/**
 */
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
