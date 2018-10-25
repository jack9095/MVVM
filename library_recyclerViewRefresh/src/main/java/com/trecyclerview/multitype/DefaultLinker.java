package com.trecyclerview.multitype;


import android.support.annotation.NonNull;

/**
 * @author：tqzhang on 18/9/6 11:22
 */
public class DefaultLinker <T> implements Linker<T> {
    @Override
    public int index(int var1, @NonNull T var2) {
        return 0;
    }
}
