package com.example.fly.mvvm.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.fly.mvvm.R;
import com.example.fly.mvvm.base.BaseViewHolder;
import com.example.fly.mvvm.core.bean.pojo.home.CatagoryInfoVo;

import java.util.List;


public class HomeCategoryAdapter extends BaseRecyclerAdapter<CatagoryInfoVo> {


    public HomeCategoryAdapter(Context context, @Nullable List<CatagoryInfoVo> list, int itemLayoutId) {
        super(context, list, R.layout.item_classify);
    }

    @Override
    protected void convert(BaseViewHolder holder, final CatagoryInfoVo catagoryInfoVo, int position, List payloads) {
        ImageView categroyIcon = holder.getView(R.id.iv_classify);
        TextView categroyName = holder.getView(R.id.tv_classify);
        Glide.with(getContext())
                .load(catagoryInfoVo.resId)
                .into(categroyIcon);
        categroyName.setText(catagoryInfoVo.title);

    }
}
