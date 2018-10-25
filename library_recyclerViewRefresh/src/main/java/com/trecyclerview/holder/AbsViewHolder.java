package com.trecyclerview.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trecyclerview.listener.OnItemClickListener;
import com.trecyclerview.multitype.AbsItemView;

/**
 * @author：tqzhang on 18/8/15 15:04
 */
public abstract class AbsViewHolder<T, VH extends BaseHolder> extends AbsItemView<T, VH> {

    protected Context mContext;


    public AbsViewHolder(Context context) {
        this.mContext = context;
    }

    @Override
    protected @NonNull
    VH onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return createViewHolder(inflater.inflate(getLayoutResId(), parent, false));
    }

    public abstract int getLayoutResId();


    public abstract VH createViewHolder(View view);


}
