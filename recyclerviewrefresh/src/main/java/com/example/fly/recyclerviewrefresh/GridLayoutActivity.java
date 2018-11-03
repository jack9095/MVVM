package com.example.fly.recyclerviewrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.example.fly.recyclerviewrefresh.itemView.ItemType;
import com.example.fly.recyclerviewrefresh.itemView.banner;
import com.example.fly.recyclerviewrefresh.pojo.BannerVo;
import com.example.fly.recyclerviewrefresh.pojo.ItemVo;
import com.fly.FRecyclerView;
import com.fly.listener.OnRefreshListener;
import com.fly.multitype.Items;
import com.fly.multitype.MultiTypeAdapter;
import com.fly.pojo.FootVo;
import com.fly.pojo.HeaderVo;
import com.fly.progressindicator.ProgressStyle;
import com.fly.view.FootViewHolder;
import com.fly.view.HeaderViewHolder;


/**
 */
public class GridLayoutActivity extends AppCompatActivity {
    private FRecyclerView fRecyclerView;
    private Items items;
    private MultiTypeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        fRecyclerView = findViewById(R.id.recycler_view);
        items = new Items();
        adapter = new MultiTypeAdapter.Builder()
                .bind(HeaderVo.class, new HeaderViewHolder(GridLayoutActivity.this, ProgressStyle.Pacman))
                .bind(BannerVo.class, new banner(GridLayoutActivity.this))
                .bind(ItemVo.class, new ItemType(GridLayoutActivity.this))
                .bind(FootVo.class, new FootViewHolder(GridLayoutActivity.this, ProgressStyle.SysProgress))
                .build();
        GridLayoutManager layoutManager = new GridLayoutManager(GridLayoutActivity.this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (items.get(position) instanceof BannerVo
                        || items.get(position) instanceof HeaderVo
                        || items.get(position) instanceof FootVo) ? 2 : 1;
            }
        });

        fRecyclerView.setAdapter(adapter);
        fRecyclerView.setLayoutManager(layoutManager);
        setListener();
        initData();
    }

    private void setListener() {
        fRecyclerView.addOnRefreshListener(new OnRefreshListener() {
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
                        fRecyclerView.refreshComplete(items, false);
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
                        fRecyclerView.loadMoreComplete(l, false);
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
        fRecyclerView.refreshComplete(items, false);
    }
}
