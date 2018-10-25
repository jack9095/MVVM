package com.trecyclerview.multitype;

import android.support.annotation.NonNull;

public interface ClassLinker<T> {
    @NonNull
    Class<? extends AbsItemView<T, ?>> index(int var1, @NonNull T t);
}
