/*
 * Copyright 2016 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trecyclerview.multitype;

import android.support.annotation.NonNull;

public interface TypePool {

    <T> void bind(
            @NonNull Class<? extends T> clazz,
            @NonNull AbsItemView<T, ?> binder);

    <T> void bind(@NonNull Class<? extends T> var1, @NonNull AbsItemView<T, ?> var2, @NonNull Linker<T> var3);

    boolean unbind(@NonNull Class<?> clazz);

    int size();

    int firstIndexOf(@NonNull Class<?> clazz);

    @NonNull
    Class<?> getClass(int index);

    @NonNull
    AbsItemView<?, ?> getItemViewBinder(int index);

    @NonNull
    Linker<?> getLinker(int index);
}
