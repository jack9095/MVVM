package com.example.fly.imageloagutil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.fly.imageloagutil.widgets.BottomView;
import com.example.fly.imageloagutil.widgets.LoadView;
import com.example.fly.imageloagutil.widgets.RefreshView;
import com.example.refreshlayout.RefreshLayout;

public class SplashActivity extends AppCompatActivity {

    RefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;
    TestAdapter mTestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTestAdapter = new TestAdapter();
        mRecyclerView.setAdapter(mTestAdapter);

        mRefreshLayout.setPullUpEnable(true);   // 开启上拉加载
        mRefreshLayout.setPullDownEnable(true); // 开启下拉刷新
        mRefreshLayout.setScrollEnable(true);

        mRefreshLayout.setHeaderView(new RefreshView(this));
        mRefreshLayout.setFooterView(new LoadView(this));
        mRefreshLayout.setBottomView(new BottomView(this));

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
                }, 1000);
            }

            @Override
            public void onLoadMore() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //上拉加载完成
                        mTestAdapter.notifyDataSetChanged();
                        mRefreshLayout.onLoadMoreComplete();
                        mRefreshLayout.onLoadMoreComplete();

//                if (mData.size() >= 18) {
                        mRefreshLayout.showNoMore(true);
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