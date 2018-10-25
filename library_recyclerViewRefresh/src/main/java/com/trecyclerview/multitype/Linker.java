package com.trecyclerview.multitype;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

public interface Linker<T> {
    @IntRange(
            from = 0L
    )
    int index(int var1, @NonNull T var2);
}
