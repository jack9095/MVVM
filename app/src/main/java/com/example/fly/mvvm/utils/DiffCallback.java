package com.example.fly.mvvm.utils;

import android.support.v7.util.DiffUtil;

import com.trecyclerview.multitype.Items;


/**
 * https://blog.csdn.net/q919233914/article/details/52996384
 * RecyclerView 比对数据工具类
 */

public class DiffCallback extends DiffUtil.Callback {

    private final Items mOldItems, mNewItems;

    public DiffCallback(Items oldItems, Items mNewItems) {
        this.mOldItems = oldItems;
        this.mNewItems = mNewItems;
    }


    @Override
    public int getOldListSize() {
        return mOldItems != null ? mOldItems.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewItems != null ? mNewItems.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldItems.get(oldItemPosition).equals(mNewItems.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldItems.get(oldItemPosition).hashCode() == mNewItems.get(newItemPosition).hashCode();
    }
}
