package com.example.fly.mvvm;

import android.content.Context;
import com.example.fly.mvvm.core.bean.pojo.banner.BannerListVo;
import com.example.fly.mvvm.core.bean.pojo.book.BookList;
import com.example.fly.mvvm.core.bean.pojo.common.TypeVo;
import com.example.fly.mvvm.core.bean.pojo.correct.WorksListVo;
import com.example.fly.mvvm.core.bean.pojo.course.CourseInfoVo;
import com.example.fly.mvvm.core.bean.pojo.home.CatagoryVo;
import com.example.fly.mvvm.core.bean.pojo.live.LiveRecommendVo;
import com.example.fly.mvvm.core.bean.pojo.material.MatreialSubjectVo;
import com.example.fly.mvvm.core.ui.adapter.BookItemHolder;
import com.example.fly.mvvm.core.ui.adapter.CorrectItemHolder;
import com.example.fly.mvvm.core.ui.adapter.CourseItemHolder;
import com.example.fly.mvvm.core.ui.view.CategoryItemView;
import com.example.fly.mvvm.core.ui.view.HomeLiveItemView;
import com.example.fly.mvvm.core.ui.view.HomeMaterialItemView;
import com.example.fly.mvvm.core.ui.view.TypeItemView;
import com.example.fly.mvvm.widget.banner.BannerItemView;
import com.trecyclerview.multitype.MultiTypeAdapter;
import com.trecyclerview.pojo.FootVo;
import com.trecyclerview.pojo.HeaderVo;
import com.trecyclerview.progressindicator.ProgressStyle;
import com.trecyclerview.view.FootViewHolder;
import com.trecyclerview.view.HeaderViewHolder;

/**
 *
 */
public class AdapterPool {
    private static AdapterPool adapterPool;

    public static AdapterPool newInstance() {
        if (adapterPool == null) {
            synchronized (AdapterPool.class) {
                if (adapterPool == null) {
                    adapterPool = new AdapterPool();
                }
            }
        }
        return adapterPool;
    }

    public MultiTypeAdapter getWorkAdapter(Context context) {
        return getAdapter(new MultiTypeAdapter.Builder<>()
                .bind(BannerListVo.class, new BannerItemView(context))
                .bind(WorksListVo.Works.class, new CorrectItemHolder(context)), context);
    }

    public MultiTypeAdapter getAdapter(MultiTypeAdapter.Builder builder, Context context) {
        return builder.bind(HeaderVo.class, new HeaderViewHolder(context, ProgressStyle.Pacman))
                .bind(FootVo.class, new FootViewHolder(context, ProgressStyle.Pacman))
                .build();
    }

    public MultiTypeAdapter getHomeAdapter(Context context) {
        return getNoFootAdapter(new MultiTypeAdapter.Builder<>()
                .bind(BannerListVo.class, new BannerItemView(context))
                .bind(TypeVo.class, new TypeItemView(context))
                .bind(CatagoryVo.class, new CategoryItemView(context))
                .bind(BookList.class, new BookItemHolder(context))
                .bind(CourseInfoVo.class, new CourseItemHolder(context))
                .bind(LiveRecommendVo.class, new HomeLiveItemView(context))
                .bind(MatreialSubjectVo.class, new HomeMaterialItemView(context)), context);
    }

    public MultiTypeAdapter getNoFootAdapter(MultiTypeAdapter.Builder builder, Context context) {
        return builder.bind(HeaderVo.class, new HeaderViewHolder(context, ProgressStyle.Pacman))
                .build();
    }

    public MultiTypeAdapter getCourseListAdapter(Context context) {
        return getAdapter(new MultiTypeAdapter.Builder<>()
                .bind(CourseInfoVo.class, new CourseItemHolder(context)), context);
    }

    public MultiTypeAdapter getCourseRemAdapter(Context context) {
        return getNoFootAdapter(new MultiTypeAdapter.Builder<>()
                .bind(TypeVo.class, new TypeItemView(context))
                .bind(BannerListVo.class, new BannerItemView(context))
                .bind(CourseInfoVo.class, new CourseItemHolder(context))
                .bind(LiveRecommendVo.class, new HomeLiveItemView(context)), context);

    }
}
