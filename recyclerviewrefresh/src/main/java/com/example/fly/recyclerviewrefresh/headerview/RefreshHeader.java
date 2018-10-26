package com.example.fly.recyclerviewrefresh.headerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.fly.recyclerviewrefresh.R;
import com.trecyclerview.listener.OnTouchMoveListener;
import com.trecyclerview.view.ArrowRefreshHeader;
import com.trecyclerview.view.BaseRefreshHeader;

/**
 */
public class RefreshHeader extends LinearLayout implements OnTouchMoveListener {
    View view;
    ProgressBar progressbar;

    public RefreshHeader(Context context) {
        super(context);
        initView();
    }

    public RefreshHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(getContext()).inflate(
                R.layout.custom_header_view, null);
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        progressbar = view.findViewById(R.id.progress_bar);
    }


    @Override
    public void onMove(float delta) {
        Log.e("onMove", "" + delta);
    }

    @Override
    public void onRefreshState(int state) {
        Log.e("onRefreshState", "" + state);
    }

    public OnTouchMoveListener getOnTouchMoveListener() {
        return this;
    }
}
