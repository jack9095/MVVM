package com.tqzhang.stateview.stateview;

import android.content.Context;
import android.view.View;


/**
 * @author zhangtianqiu
 */
public class SuccessState extends BaseStateControl {

    public SuccessState(View view, Context context, OnRefreshListener onRefreshListener) {
        super(view, context, onRefreshListener);
    }

    @Override
    protected int onCreateView() {
        return 0;
    }

    public void hide() {
        getRootView.setVisibility(View.INVISIBLE);
    }

    public void show() {
        getRootView().setVisibility(View.VISIBLE);
    }

    public void showWithStateView(boolean successVisible) {
        getRootView().setVisibility(successVisible ? View.VISIBLE : View.INVISIBLE);
    }

}
