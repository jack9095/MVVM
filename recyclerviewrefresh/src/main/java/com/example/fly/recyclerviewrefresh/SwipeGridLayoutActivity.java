package com.example.fly.recyclerviewrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.example.fly.recyclerviewrefresh.itemView.ItemType;
import com.example.fly.recyclerviewrefresh.itemView.banner;
import com.example.fly.recyclerviewrefresh.pojo.BannerVo;
import com.example.fly.recyclerviewrefresh.pojo.ItemVo;
import com.fly.SwipeRecyclerView;
import com.fly.listener.OnLoadMoreListener;
import com.fly.multitype.Items;
import com.fly.multitype.MultiTypeAdapter;
import com.fly.pojo.FootVo;
import com.fly.pojo.HeaderVo;
import com.fly.progressindicator.ProgressStyle;
import com.fly.view.FootViewHolder;


/**
 */
public class SwipeGridLayoutActivity extends AppCompatActivity {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRecyclerView tRecyclerView;
    private Items items;
    private MultiTypeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type2);
        tRecyclerView = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        items = new Items();
        adapter = new MultiTypeAdapter.Builder()
                .bind(BannerVo.class, new banner(SwipeGridLayoutActivity.this))
                .bind(ItemVo.class, new ItemType(SwipeGridLayoutActivity.this))
                .bind(FootVo.class, new FootViewHolder(SwipeGridLayoutActivity.this, ProgressStyle.SysProgress,"努力加载","没有更多啦。。"))
                .build();
        GridLayoutManager layoutManager = new GridLayoutManager(SwipeGridLayoutActivity.this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (items.get(position) instanceof BannerVo
                        || items.get(position) instanceof HeaderVo
                        || items.get(position) instanceof FootVo) ? 2 : 1;
            }
        });

        tRecyclerView.setAdapter(adapter);
        tRecyclerView.setLayoutManager(layoutManager);
        setListener();
        initData();

        //设置刷新时动画的颜色，可以设置4个
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setProgressViewOffset(false, 0, 60);
            mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        }
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        items.clear();
                        items.add(new BannerVo());
                        for (int i = 0; i < 10; i++) {
                            items.add(new ItemVo());
                        }
                        tRecyclerView.refreshComplete(items, false);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                }, 5000);
            }
        });

    }

    private void setListener() {
        tRecyclerView.addOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                final Items item = new Items();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            item.add(new ItemVo());
                        }
                        items.addAll(item);
                        tRecyclerView.loadMoreComplete(item, false);
//                        tRecyclerView.setNoMore(20);
                    }

                }, 2000);
            }
        });
    }

    private void initData() {
        items.clear();
        items.add(new BannerVo());
        for (int i = 0; i < 10; i++) {
            items.add(new ItemVo());
        }
        tRecyclerView.refreshComplete(items, true);
    }
}
