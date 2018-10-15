package com.example.fly.mvvm.utils;

import android.support.annotation.NonNull;


/**
 * 前提 先决条件
 */

public class Preconditions {

    /**
     * 检查是否为空
     * @param reference
     * @param <T>
     * @return
     */
    public static @NonNull <T> T checkNotNull(final T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
