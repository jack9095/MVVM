package com.example.fly.recyclerviewrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import com.example.fly.recyclerviewrefresh.headerview.RefreshHeader;
import com.example.fly.recyclerviewrefresh.itemView.ItemType;
import com.example.fly.recyclerviewrefresh.itemView.banner;
import com.example.fly.recyclerviewrefresh.pojo.BannerVo;
import com.example.fly.recyclerviewrefresh.pojo.ItemVo;
import com.trecyclerview.TRecyclerView;
import com.trecyclerview.listener.OnRefreshListener;
import com.trecyclerview.listener.OnTScrollListener;
import com.trecyclerview.multitype.Items;
import com.trecyclerview.multitype.MultiTypeAdapter;
import com.trecyclerview.pojo.FootVo;
import com.trecyclerview.pojo.HeaderVo;
import com.trecyclerview.progressindicator.ProgressStyle;
import com.trecyclerview.view.FootViewHolder;
import com.trecyclerview.view.HeaderViewHolder;


/**
 * 常用的刷新
 */
public class LinearLayoutActivity extends AppCompatActivity {
    private TRecyclerView tRecyclerView;
    private Items items;
    private MultiTypeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        tRecyclerView = findViewById(R.id.recycler_view);
        items = new Items();
        adapter = new MultiTypeAdapter.Builder()
//                .bind(HeaderVo.class, new HeaderViewHolder(LinearLayoutActivity.this, LayoutInflater.from(this).inflate(R.layout.custom_header_view,null)))
                .bind(HeaderVo.class, new HeaderViewHolder(LinearLayoutActivity.this, new RefreshHeader(this), new RefreshHeader(this).getOnTouchMoveListener()))
                .bind(BannerVo.class, new banner(LinearLayoutActivity.this))
                .bind(ItemVo.class, new ItemType(LinearLayoutActivity.this))
//                .bind(FootVo.class, new FootViewHolder(LinearLayoutActivity.this, ProgressStyle.Pacman))
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(LinearLayoutActivity.this);
        tRecyclerView.setAdapter(adapter);
        tRecyclerView.setLayoutManager(layoutManager);
        setListener();
        initData();
    }

    private void setListener() {

        tRecyclerView.addOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        items.clear();
                        items.add(new BannerVo());
                        for (int i = 0; i < 10; i++) {
                            items.add(new ItemVo());
                        }
                        tRecyclerView.refreshComplete(items, false);
                    }

                }, 5000);

            }

            @Override
            public void onLoadMore() {
                final Items l = new Items();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 10; i++) {
                            l.add(new ItemVo());
                        }
                        items.addAll(l);
                        tRecyclerView.loadMoreComplete(l, false);

                        //数据返回是空的  没有更多  tRecyclerView.loadMoreComplete(null, false);
                        //
//                        tRecyclerView.setNoMore(l);
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
//        tRecyclerView.setNoMore(items);
    }
}
