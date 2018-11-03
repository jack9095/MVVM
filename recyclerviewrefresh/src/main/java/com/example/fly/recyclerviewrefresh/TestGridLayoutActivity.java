package com.example.fly.recyclerviewrefresh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fly.recyclerviewrefresh.pojo.BannerVo;
import com.example.fly.recyclerviewrefresh.pojo.ItemVo;
import com.fly.multitype.Items;

import java.util.Collections;
import java.util.List;


/**
 */
public class TestGridLayoutActivity extends AppCompatActivity {
    private RecyclerView tRecyclerView;
    private Items items;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tRecyclerView = findViewById(R.id.recycler_view);
        items = new Items();
        GridLayoutManager layoutManager = new GridLayoutManager(TestGridLayoutActivity.this, 2);
        adapter = new MyAdapter();
        tRecyclerView.setAdapter(adapter);
        tRecyclerView.setLayoutManager(layoutManager);
        initData();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {
        private List<ItemVo> list;

        public MyAdapter() {
            list = Collections.emptyList();
        }

        public void setList(List list) {
            this.list = list;
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type, parent, false);
            return new Holder(view);
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }


        public class Holder extends RecyclerView.ViewHolder {
            public Holder(View itemView) {
                super(itemView);
            }
        }

    }
    private void initData() {
        items.clear();
        items.add(new BannerVo());
        for (int i = 0; i < 80; i++) {
            items.add(new ItemVo());
        }
        adapter.setList(items);
        adapter.notifyDataSetChanged();
    }
}
