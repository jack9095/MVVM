package com.example.fly.recyclerviewrefresh.itemView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.fly.recyclerviewrefresh.R;
import com.example.fly.recyclerviewrefresh.pojo.BannerVo;
import com.fly.holder.AbsViewHolder;
import com.fly.holder.BaseHolder;
import com.fly.listener.OnItemClickListener;

/**
 */
public class banner extends AbsViewHolder<BannerVo, banner.ViewHolder> {
    public banner(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.banner;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view,mOnItemClickListener);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BannerVo item) {
        RecyclerView.LayoutParams clp = (RecyclerView.LayoutParams) holder.mBannerView.getLayoutParams();
        if (clp instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) clp).setFullSpan(true);
        }
    }

    static class ViewHolder extends BaseHolder {

        private RelativeLayout mBannerView;
        ViewHolder(@NonNull final View itemView, final OnItemClickListener onItemClickListener) {
            super(itemView);
            mBannerView=getViewById(R.id.rl_root_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null!=onItemClickListener){
                        onItemClickListener.onItemClick(v,getAdapterPosition(),itemView.getTag());
                    }

                }
            });
        }

    }

}
