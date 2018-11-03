package com.fly.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fly.multitype.AbsItemView;


public abstract class AbsViewHolder<T, VH extends BaseHolder> extends AbsItemView<T, VH> {

    protected Context mContext;


    public AbsViewHolder(Context context) {
        this.mContext = context;
    }

    @Override
    protected @NonNull VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return createViewHolder(inflater.inflate(getLayoutResId(), parent, false));
    }

    /**
     * item的布局
     * @return
     */
    public abstract int getLayoutResId();


    /**
     * 创建布局对应的 ViewHolder
     * @param view
     * @return
     */
    public abstract VH createViewHolder(View view);


}
