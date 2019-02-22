package com.example.fly.refreshlayoutdemo.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.fly.refreshlayoutdemo.R;
import com.example.fly.refreshlayoutdemo.adapter.RecyclerRefreshAdapter;
import com.example.fly.refreshlayoutdemo.widgets.SwipeRefreshLayoutRecycler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RefreshRecyclerViewActivity extends AppCompatActivity implements SwipeRefreshLayoutRecycler.RefreshListener {

    SwipeRefreshLayoutRecycler mSwipeRefreshLayoutRecycler;
    RecyclerRefreshAdapter mRecyclerRefreshAdapter;
    List<String> lists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recycler_view);
        mSwipeRefreshLayoutRecycler = (SwipeRefreshLayoutRecycler) findViewById(R.id.swipe_refresh_layout_recycler);
        mSwipeRefreshLayoutRecycler.setRefreshListener(this);

        for(int i = 0; i < 10; i++){
            lists.add("" + i);
        }

        mRecyclerRefreshAdapter = new RecyclerRefreshAdapter();
        mSwipeRefreshLayoutRecycler.getRecyclerView().setAdapter(mRecyclerRefreshAdapter);
        mRecyclerRefreshAdapter.setData(lists);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onRefreshListener() {
        Log.e("RefreshRecyclerViewActivity","刷新数据");
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onLoadListener(int newState, int lastVisibleItem) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mRecyclerRefreshAdapter.getItemCount()) {

            for(int i = 0; i < 10; i++){
                lists.add("" + i);
            }
            Log.e("RefreshRecyclerViewActivity","滑动到底部");

            mSwipeRefreshLayoutRecycler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerRefreshAdapter.setData(lists);
                }
            },2000);
        }
    }
}
