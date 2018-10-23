package com.example.fly.mvvm.widget.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.fly.mvvm.R;
import com.example.fly.mvvm.core.bean.pojo.banner.BannerListVo;
import com.trecyclerview.holder.AbsViewHolder;
import com.trecyclerview.holder.BaseHolder;

import java.util.ArrayList;
import java.util.List;



public class BannerItemView extends AbsViewHolder<BannerListVo, BannerItemView.ViewHolder> {

    public BannerItemView(Context context) {
        super(context);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.common_banner_view;
    }

    @Override
    public ViewHolder createViewHolder(View view) {
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull BannerItemView.ViewHolder holder, @NonNull final BannerListVo bannerAdListVo) {
        holder.mBannerView.delayTime(5).setBannerView(new BannerView.OnBindView() {
            @Override
            public List<ImageView> bindView() {
                List<ImageView> imageViewList = new ArrayList<>();
                for (int i = 0; i < bannerAdListVo.data.size(); i++) {
                    ImageView mImageView = new ImageView(mContext);
                    mImageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    Glide.with(mContext).load(bannerAdListVo.data.get(i).topimage1 == null ? bannerAdListVo.data.get(i).topimage : bannerAdListVo.data.get(i).topimage1).centerCrop().into(mImageView);
                    imageViewList.add(mImageView);
                }
                return imageViewList;
            }
        }).build(bannerAdListVo.data);

    }

    @Override
    protected void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        RecyclerView.LayoutParams clp = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        if (clp instanceof StaggeredGridLayoutManager.LayoutParams) {
            ((StaggeredGridLayoutManager.LayoutParams) clp).setFullSpan(true);
        }
    }

    static class ViewHolder extends BaseHolder {

        private BannerView mBannerView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mBannerView = getViewById(R.id.banner);
        }

    }

}
