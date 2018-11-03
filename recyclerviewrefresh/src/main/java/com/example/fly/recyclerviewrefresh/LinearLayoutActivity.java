package com.example.fly.recyclerviewrefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.fly.recyclerviewrefresh.headerview.RefreshHeader;
import com.example.fly.recyclerviewrefresh.itemView.ItemType;
import com.example.fly.recyclerviewrefresh.itemView.banner;
import com.example.fly.recyclerviewrefresh.pojo.BannerVo;
import com.example.fly.recyclerviewrefresh.pojo.ItemVo;
import com.fly.FRecyclerView;
import com.fly.listener.OnItemClickListener;
import com.fly.listener.OnRefreshListener;
import com.fly.multitype.Items;
import com.fly.multitype.MultiTypeAdapter;
import com.fly.pojo.FootVo;
import com.fly.pojo.HeaderVo;
import com.fly.progressindicator.ProgressStyle;
import com.fly.view.FootViewHolder;
import com.fly.view.HeaderViewHolder;


/**
 * 常用的刷新
 */
public class LinearLayoutActivity extends AppCompatActivity implements OnItemClickListener {
    private FRecyclerView fRecyclerView;
    private Items items;
    private MultiTypeAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_type);
        fRecyclerView = findViewById(R.id.recycler_view);
        items = new Items();
        ItemType itemType = new ItemType(LinearLayoutActivity.this);
        itemType.setOnItemClickListener(this);
        adapter = new MultiTypeAdapter.Builder()
//                .bind(HeaderVo.class, new HeaderViewHolder(LinearLayoutActivity.this, LayoutInflater.from(this).inflate(R.layout.custom_header_view,null)))
                .bind(HeaderVo.class, new HeaderViewHolder(LinearLayoutActivity.this, new RefreshHeader(this), new RefreshHeader(this).getOnTouchMoveListener()))
                .bind(BannerVo.class, new banner(LinearLayoutActivity.this))
                .bind(ItemVo.class, itemType)
                .bind(FootVo.class, new FootViewHolder(LinearLayoutActivity.this, ProgressStyle.Pacman))
                .build();

        LinearLayoutManager layoutManager = new LinearLayoutManager(LinearLayoutActivity.this);
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

                        //数据返回是空的  没有更多
                         fRecyclerView.loadMoreComplete(null, false);
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
        fRecyclerView.refreshComplete(items, true);
//        tRecyclerView.setNoMore(items);
    }

    @Override
    public void onItemClick(View view, int position, Object o) {
        if (o instanceof ItemVo) {
            ItemVo itemVo = (ItemVo) o;
            Toast.makeText(this,"" + position,Toast.LENGTH_SHORT).show();
        }
    }
}
