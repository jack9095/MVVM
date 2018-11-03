package com.example.fly.recyclerviewrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.fly.recyclerviewrefresh.itemView.StageredItemType;
import com.example.fly.recyclerviewrefresh.itemView.StageredItemType2;
import com.example.fly.recyclerviewrefresh.itemView.banner;
import com.example.fly.recyclerviewrefresh.pojo.BannerVo;
import com.example.fly.recyclerviewrefresh.pojo.ItemVo;
import com.fly.FRecyclerView;
import com.fly.listener.OnRefreshListener;
import com.fly.multitype.AbsItemView;
import com.fly.multitype.ClassLinker;
import com.fly.multitype.Items;
import com.fly.multitype.MultiTypeAdapter;
import com.fly.pojo.FootVo;
import com.fly.pojo.HeaderVo;
import com.fly.progressindicator.ProgressStyle;
import com.fly.view.FootViewHolder;
import com.fly.view.HeaderViewHolder;


/**
 */
public class StaggeredGridLayoutActivity extends AppCompatActivity {
    private FRecyclerView fRecyclerView;
    private Items items;
    private MultiTypeAdapter adapter;
    private int indexPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        fRecyclerView = findViewById(R.id.recycler_view);
        items = new Items();
        adapter = new MultiTypeAdapter.Builder()
                .bind(HeaderVo.class, new HeaderViewHolder(StaggeredGridLayoutActivity.this, ProgressStyle.Pacman))
                .bind(BannerVo.class, new banner(StaggeredGridLayoutActivity.this))
                .bindArray(ItemVo.class, new StageredItemType(StaggeredGridLayoutActivity.this), new StageredItemType2(StaggeredGridLayoutActivity.this))
                .withClass(new ClassLinker<ItemVo>() {
                    @NonNull
                    @Override
                    public Class<? extends AbsItemView<ItemVo, ?>> index(int var1, @NonNull ItemVo var2) {
                        if (Integer.parseInt(var2.type) == 1) {
                            return StageredItemType.class;
                        } else {
                            return StageredItemType2.class;
                        }
                    }
                })
                .bind(FootVo.class, new FootViewHolder(StaggeredGridLayoutActivity.this, ProgressStyle.Pacman))
                .build();
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
                        for (int i = 0; i < 20; i++) {
                            if (i % 2 == 0) {
                                items.add(new ItemVo("" + 1));
                            } else {
                                items.add(new ItemVo("" + 2));
                            }

                        }
                        fRecyclerView.refreshComplete(items, false);

                    }

                }, 5000);

            }

            @Override
            public void onLoadMore() {
                final Items item = new Items();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        indexPage += 1;
                        for (int i = 0; i < 20; i++) {
                            item.add(new ItemVo(i + ""));
                        }
                        items.addAll(item);
                        //模拟加载多页没有更多
                        if (indexPage == 4) {
                            fRecyclerView.loadMoreComplete(item, true);
                        } else {
                            fRecyclerView.loadMoreComplete(item, false);
                        }

                    }

                }, 2000);
            }
        });
    }

    private void initData() {
        items.clear();
        items.add(new BannerVo());
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                items.add(new ItemVo("" + 1));
            } else {
                items.add(new ItemVo("" + 2));
            }
        }
        fRecyclerView.refreshComplete(items, true);
    }
}
