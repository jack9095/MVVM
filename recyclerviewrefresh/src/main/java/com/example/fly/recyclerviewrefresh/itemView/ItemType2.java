package com.example.fly.recyclerviewrefresh.itemView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.fly.recyclerviewrefresh.R;
import com.example.fly.recyclerviewrefresh.pojo.Item2Vo;
import com.fly.holder.AbsViewHolder;
import com.fly.holder.BaseHolder;

/**
 */
public class ItemType2 extends AbsViewHolder<Item2Vo, ItemType2.ViewHolder> {
    public ItemType2(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.type_2;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Item2Vo item) {

    }

    static class ViewHolder extends BaseHolder {

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

}
