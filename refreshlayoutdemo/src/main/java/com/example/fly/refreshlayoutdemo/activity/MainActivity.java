package com.example.fly.refreshlayoutdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.fly.refreshlayoutdemo.R;
import com.example.fly.refreshlayoutdemo.adapter.TestAdapter;
import com.example.fly.refreshlayoutdemo.widgets.RefreshView;
import com.example.refreshlayout.RefreshLayout;

public class MainActivity extends AppCompatActivity {
    RefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;
    TestAdapter mTestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTestAdapter = new TestAdapter();
        mRecyclerView.setAdapter(mTestAdapter);

        mRefreshLayout.setPullDownEnable(true); // 开启下拉刷新
        mRefreshLayout.setScrollEnable(true);  // 是否允许视图滑动

        mRefreshLayout.setHeaderView(new RefreshView(this));
//        mRefreshLayout.setEffectivePullDownRange(300);
        mRefreshLayout.setChildHeaderHeight(90);

        mRefreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTestAdapter.notifyDataSetChanged();
                        //下拉刷新完成
                        mRefreshLayout.onRefreshComplete();
                    }
                }, 5000);
            }
        });
    }
}
