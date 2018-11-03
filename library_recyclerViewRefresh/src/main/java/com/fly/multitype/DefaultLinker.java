package com.fly.multitype;


import android.support.annotation.NonNull;

/**
 */
public class DefaultLinker <T> implements Linker<T> {
    @Override
    public int index(int var1, @NonNull T var2) {
        return 0;
    }
}
