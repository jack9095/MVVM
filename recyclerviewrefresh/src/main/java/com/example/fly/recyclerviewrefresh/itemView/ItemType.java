package com.example.fly.recyclerviewrefresh.itemView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.fly.recyclerviewrefresh.R;
import com.example.fly.recyclerviewrefresh.pojo.ItemVo;
import com.fly.holder.AbsViewHolder;
import com.fly.holder.BaseHolder;
import com.fly.listener.OnItemClickListener;

/**
 */
public class ItemType extends AbsViewHolder<ItemVo, ItemType.ViewHolder> {
    public ItemType(Context context) {
        super(context);
    }


    @Override
    public int getLayoutResId() {
        return R.layout.type;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view,mOnItemClickListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull ItemVo item) {
        holder.textView.setText(item.type);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    static class ViewHolder extends BaseHolder {
        TextView textView;
        ViewHolder(@NonNull final View itemView, final OnItemClickListener mOnItemClickListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null!=mOnItemClickListener){
                        mOnItemClickListener.onItemClick(v,getAdapterPosition(),itemView.getTag());
                    }

                }
            });
        }
    }

}
