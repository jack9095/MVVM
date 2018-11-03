package com.example.fly.recyclerviewrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.example.fly.recyclerviewrefresh.itemView.ItemType;
import com.example.fly.recyclerviewrefresh.itemView.ItemType1;
import com.example.fly.recyclerviewrefresh.itemView.ItemType2;
import com.example.fly.recyclerviewrefresh.itemView.banner;
import com.example.fly.recyclerviewrefresh.pojo.BannerVo;
import com.example.fly.recyclerviewrefresh.pojo.Item1Vo;
import com.example.fly.recyclerviewrefresh.pojo.Item2Vo;
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
public class MultiTypeActivity extends AppCompatActivity {
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
                .bind(HeaderVo.class, new HeaderViewHolder(MultiTypeActivity.this, ProgressStyle.Pacman))
                .bind(BannerVo.class, new banner(MultiTypeActivity.this))
                .bind(ItemVo.class, new ItemType(MultiTypeActivity.this))
                .bind(Item1Vo.class, new ItemType1(MultiTypeActivity.this))
                .bind(Item2Vo.class, new ItemType2(MultiTypeActivity.this))
                .bind(FootVo.class, new FootViewHolder(MultiTypeActivity.this, ProgressStyle.Pacman))
                .build();
        GridLayoutManager layoutManager = new GridLayoutManager(MultiTypeActivity.this, 4);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (items.get(position) instanceof BannerVo
                        || items.get(position) instanceof HeaderVo
                        || items.get(position) instanceof Item1Vo
                        || items.get(position) instanceof FootVo) {
                    return 4;
                } else if (items.get(position) instanceof ItemVo) {
                    return 2;
                } else if (items.get(position) instanceof Item2Vo) {
                    return 1;
                }
                return 4;
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
                        initData();
                    }

                }, 5000);

            }

            @Override
            public void onLoadMore() {
                final Items item = new Items();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        item.add(new Item1Vo("Python"));
                        for (int i = 0; i < 6; i++) {
                            item.add(new ItemVo());
                        }
                        item.add(new Item1Vo("Go"));
                        for (int i = 0; i < 12; i++) {
                            item.add(new ItemVo());
                        }
                        items.addAll(item);
                        fRecyclerView.loadMoreComplete(item, false);
//                        tRecyclerView.setNoMore(20);
                    }

                }, 2000);
            }
        });
    }

    private void initData() {
        items.clear();
        items.add(new BannerVo());
        for (int i = 0; i < 8; i++) {
            items.add(new Item2Vo());
        }
        items.add(new Item1Vo("java"));
        for (int i = 0; i < 6; i++) {
            items.add(new ItemVo());
        }
        items.add(new Item1Vo("android"));
        for (int i = 0; i < 6; i++) {
            items.add(new ItemVo());
        }
        fRecyclerView.refreshComplete(items, false);
    }
}
