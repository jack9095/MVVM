package com.fly.listener;

import android.view.View;

/**
 * item点击事件
 */
public interface OnItemClickListener <T> {
    void onItemClick(View view, int position, T t);
}
