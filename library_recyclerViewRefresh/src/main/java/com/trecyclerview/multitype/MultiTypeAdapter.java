
package com.trecyclerview.multitype;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.trecyclerview.listener.OnItemClickListener;
import com.trecyclerview.util.Preconditions;

import java.util.Collections;
import java.util.List;

import static com.trecyclerview.util.Preconditions.checkNotNull;

public class MultiTypeAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final String TAG = "MultiTypeAdapter";

    private LayoutInflater inflater;

    private @NonNull
    List<?> items;
    private @NonNull
    TypePool typePool;

    private OnItemClickListener mOnItemClickListener;

    private MultiTypeAdapter.Builder builder;

    public MultiTypeAdapter(Builder builder) {
        checkNotNull(builder);
        items = Collections.emptyList();
        this.builder = builder;
        this.typePool = builder.typePool;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public static class Builder<T> {

        private TypePool typePool;

        private Class<? extends T> clazz;

        private AbsItemView<T, ?>[] binders;

        public Builder() {
            typePool = new MultiTypePool();
        }

        public Builder bind(@NonNull Class<? extends T> clazz, @NonNull AbsItemView binder) {
            checkNotNull(clazz);
            checkNotNull(binder);
            this.typePool.bind(clazz, binder);
            return this;
        }

        public Builder bind(@NonNull Class<? extends T> clazz, @NonNull AbsItemView binder, @NonNull Linker<T> linker) {
            checkNotNull(clazz);
            checkNotNull(binder);
            checkNotNull(linker);
            this.typePool.bind(clazz, binder, linker);
            return this;
        }

        @CheckResult
        @NonNull
        public Builder bindArray(@NonNull Class<? extends T> clazz, @NonNull AbsItemView... binders) {
            Preconditions.checkNotNull(clazz);
            Preconditions.checkNotNull(binders);
            this.clazz = clazz;
            this.binders = binders;
            return this;
        }

        public Builder withClass(@NonNull ClassLinker<T> classLinker) {
            Preconditions.checkNotNull(classLinker);
            Linker<T> linker = ClassLinkerWrapper.wrap(classLinker, binders);
            AbsItemView[] var2 = this.binders;
            for (AbsItemView binder : var2) {
                this.bind(this.clazz, binder, linker);
            }
            return this;
        }

        public MultiTypeAdapter build() {
            return new MultiTypeAdapter(this);
        }
    }

    public void setItems(@NonNull List<?> items) {
        checkNotNull(items);
        this.items = items;
    }

    public @NonNull
    List<?> getItems() {
        return items;
    }


    public @NonNull
    TypePool getTypePool() {
        return typePool;
    }


    @Override
    public final int getItemViewType(int position) {
        Object item = items.get(position);
        return indexInTypesOf(position, item);
    }


    @Override
    public final ViewHolder onCreateViewHolder(ViewGroup parent, int indexViewType) {
        if (null == inflater) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        AbsItemView<?, ?> binder = typePool.getItemViewBinder(indexViewType);
        if (null != mOnItemClickListener) {
            binder.setOnItemClickListener(mOnItemClickListener);
        }

        return binder.onCreateViewHolder(inflater, parent);
    }

    @Override
    @Deprecated
    public final void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        onBindViewHolder(holder, position, Collections.emptyList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void onBindViewHolder(ViewHolder holder, int position, @NonNull List<Object> payloads) {
        AbsItemView binder = typePool.getItemViewBinder(holder.getItemViewType());
        binder.onBindViewHolder(holder, items.get(position), payloads);
    }


    @Override
    public final int getItemCount() {
        return items.size();
    }


    @Override
    @SuppressWarnings("unchecked")
    public final long getItemId(int position) {
        Object item = items.get(position);
        int itemViewType = getItemViewType(position);
        AbsItemView binder = typePool.getItemViewBinder(itemViewType);
        return binder.getItemId(item);
    }


    @Override
    @SuppressWarnings("unchecked")
    public final void onViewRecycled(@NonNull ViewHolder holder) {
        getRawBinderByViewHolder(holder).onViewRecycled(holder);
    }


    @Override
    @SuppressWarnings("unchecked")
    public final boolean onFailedToRecycleView(@NonNull ViewHolder holder) {
        return getRawBinderByViewHolder(holder).onFailedToRecycleView(holder);
    }


    @Override
    @SuppressWarnings("unchecked")
    public final void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        getRawBinderByViewHolder(holder).onViewAttachedToWindow(holder);
    }


    @Override
    @SuppressWarnings("unchecked")
    public final void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        getRawBinderByViewHolder(holder).onViewDetachedFromWindow(holder);
    }

    private @NonNull
    AbsItemView getRawBinderByViewHolder(@NonNull ViewHolder holder) {
        return typePool.getItemViewBinder(holder.getItemViewType());
    }

    public int indexInTypesOf(int position, @NonNull Object item) throws BinderNotFoundException {
        int index = this.typePool.firstIndexOf(item.getClass());
        if (index != -1) {
            Linker<Object> linker = (Linker<Object>) this.typePool.getLinker(index);
            return index + linker.index(position, item);
        } else {
            throw new BinderNotFoundException(item.getClass());
        }
    }
}
