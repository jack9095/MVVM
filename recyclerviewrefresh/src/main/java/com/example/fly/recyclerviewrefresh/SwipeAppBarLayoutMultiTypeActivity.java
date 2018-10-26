package com.example.fly.recyclerviewrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.fly.recyclerviewrefresh.itemView.ItemType;
import com.example.fly.recyclerviewrefresh.itemView.ItemType1;
import com.example.fly.recyclerviewrefresh.itemView.ItemType2;
import com.example.fly.recyclerviewrefresh.itemView.banner;
import com.example.fly.recyclerviewrefresh.pojo.BannerVo;
import com.example.fly.recyclerviewrefresh.pojo.Item1Vo;
import com.example.fly.recyclerviewrefresh.pojo.Item2Vo;
import com.example.fly.recyclerviewrefresh.pojo.ItemVo;
import com.trecyclerview.SwipeRecyclerView;
import com.trecyclerview.listener.OnItemClickListener;
import com.trecyclerview.listener.OnLoadMoreListener;
import com.trecyclerview.listener.OnTScrollListener;
import com.trecyclerview.multitype.Items;
import com.trecyclerview.multitype.MultiTypeAdapter;
import com.trecyclerview.pojo.FootVo;
import com.trecyclerview.pojo.HeaderVo;
import com.trecyclerview.progressindicator.ProgressStyle;
import com.trecyclerview.view.FootViewHolder;
import com.trecyclerview.view.HeaderViewHolder;


/**
 */
public class SwipeAppBarLayoutMultiTypeActivity extends AppCompatActivity implements SwipeRecyclerView.AppBarStateListener, OnItemClickListener {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SwipeRecyclerView tRecyclerView;
    private Items items;
    private MultiTypeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type3);
        tRecyclerView = findViewById(R.id.recycler_view);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        items = new Items();

        adapter = new MultiTypeAdapter.Builder()
                .bind(HeaderVo.class, new HeaderViewHolder(SwipeAppBarLayoutMultiTypeActivity.this, ProgressStyle.Pacman))
                .bind(BannerVo.class, new banner(SwipeAppBarLayoutMultiTypeActivity.this))
                .bind(ItemVo.class, new ItemType(SwipeAppBarLayoutMultiTypeActivity.this))
                .bind(Item1Vo.class, new ItemType1(SwipeAppBarLayoutMultiTypeActivity.this))
                .bind(Item2Vo.class, new ItemType2(SwipeAppBarLayoutMultiTypeActivity.this))
                .bind(FootVo.class, new FootViewHolder(SwipeAppBarLayoutMultiTypeActivity.this, ProgressStyle.Pacman))
                .build();
        adapter.setOnItemClickListener(this);
        GridLayoutManager layoutManager = new GridLayoutManager(SwipeAppBarLayoutMultiTypeActivity.this, 4);
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
                        initData();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                }, 5000);
            }
        });


        tRecyclerView.setAppBarStateListener(this);
        tRecyclerView.addOnTScrollListener(new OnTScrollListener() {
            @Override
            public void onScrolled(int dx, int dy) {

            }

            @Override
            public void onScrollStateChanged(int state) {
                if (!tRecyclerView.canScrollVertically(-1)) {
                    if (isOffsetScroll == SwipeRecyclerView.State.EXPANDED) {
                        mSwipeRefreshLayout.setEnabled(true);
                    }
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
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
                        item.add(new Item1Vo("Python"));
                        for (int i = 0; i < 6; i++) {
                            item.add(new ItemVo());
                        }
                        item.add(new Item1Vo("Go"));
                        for (int i = 0; i < 12; i++) {
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
        tRecyclerView.refreshComplete(items, false);
    }

    private SwipeRecyclerView.State isOffsetScroll = SwipeRecyclerView.State.EXPANDED;

    @Override
    public void onChanged(AppBarLayout appBarLayout, SwipeRecyclerView.State state) {
        if (state == SwipeRecyclerView.State.EXPANDED) {
            mSwipeRefreshLayout.setEnabled(true);
            isOffsetScroll = state;
        } else {
            mSwipeRefreshLayout.setEnabled(false);
            isOffsetScroll = state;
        }
    }

    @Override
    public void onItemClick(View view, int position, Object o) {
       if (o instanceof ItemVo){
           ItemVo itemVo= (ItemVo) o;
           Toast.makeText(this, ""+itemVo.type, Toast.LENGTH_SHORT).show();

       }else if (o instanceof Item1Vo){
           Item1Vo  item1Vo= (Item1Vo) items.get(position);
           item1Vo.type="刷新";
           tRecyclerView.notifyItemRangeChanged(position,1);

       }

      }
}
