package com.example.fly.imageloagutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.fly.imageloagutil.widgets.BottomView;
import com.example.fly.imageloagutil.widgets.LoadView;
import com.example.fly.imageloagutil.widgets.RefreshView;
import com.example.refreshlayout.RefreshLoadLayout;

public class SplashActivity extends AppCompatActivity {

    RefreshLoadLayout mRefreshLoadLayout;
    RecyclerView mRecyclerView;
    TestAdapter mTestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mRefreshLoadLayout = findViewById(R.id.refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTestAdapter = new TestAdapter();
        mRecyclerView.setAdapter(mTestAdapter);

        mRefreshLoadLayout.setPullUpEnable(true);   // 开启上拉加载
        mRefreshLoadLayout.setPullDownEnable(true); // 开启下拉刷新
        mRefreshLoadLayout.setScrollEnable(false);

        mRefreshLoadLayout.setHeaderView(new RefreshView(this));
        mRefreshLoadLayout.setFooterView(new LoadView(this));
        mRefreshLoadLayout.setBottomView(new BottomView(this));

        mRefreshLoadLayout.setOnRefreshListener(new RefreshLoadLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mRefreshLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTestAdapter.notifyDataSetChanged();
                        //下拉刷新完成
                        mRefreshLoadLayout.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                mRefreshLoadLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //上拉加载完成
                        mTestAdapter.notifyDataSetChanged();
                        mRefreshLoadLayout.onLoadMoreComplete();

//                if (mData.size() >= 18) {
                        mRefreshLoadLayout.showNoMore(true);
//                }
                    }
                }, 1000);
            }
        });


        // 自定义刷新布局请实现IHeaderWrapper接口
//        mRefreshLayout.setHeaderView();

        // 自定义上拉加载布局请实现IFooterWrapper接口
//        mRefreshLayout.setFooterView();

        // 自定义无更多布局请实现IBottomWrapper接口
//        mRefreshLayout.setBottomView();
    }
}