package com.example.fly.mvvm.core.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import com.danikula.videocache.Preconditions;
import com.example.fly.mvvm.AdapterPool;
import com.example.fly.mvvm.R;
import com.example.fly.mvvm.base.BaseListFragment;
import com.example.fly.mvvm.core.vm.WorkViewModel;
import com.trecyclerview.multitype.MultiTypeAdapter;

/**
 */
public class WorkFragment extends BaseListFragment<WorkViewModel> {
    private String uTime;
    private String LiveBus2;

    public static WorkFragment newInstance() {
        return new WorkFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        setTitle(getResources().getString(R.string.work_title_name));
    }

    @Override
    protected void dataObserver() {
        mViewModel.getWorkMergeData().observe(this, workMergeVo -> {
            if (workMergeVo != null) {
                newItems.clear();
                newItems.add(workMergeVo.bannerListVo);
                lastId = workMergeVo.worksListVo.data.content.get(workMergeVo.worksListVo.data.content.size() - 1).tid;
                uTime = workMergeVo.worksListVo.data.content.get(workMergeVo.worksListVo.data.content.size() - 1).utime;
                setData(workMergeVo.worksListVo.data.content);
            }
        });
        mViewModel.getWorkMoreData().observe(this, worksListVo -> {
            Preconditions.checkAllNotNull(worksListVo);
            if (worksListVo == null) {
                return;
            }
            lastId = worksListVo.data.content.get(worksListVo.data.content.size() - 1).tid;
            uTime = worksListVo.data.content.get(worksListVo.data.content.size() - 1).utime;
            setData(worksListVo.data.content);
        });

    }

    @Override
    protected RecyclerView.LayoutManager createLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Override
    protected MultiTypeAdapter createAdapter() {
        return AdapterPool.newInstance().getWorkAdapter(getActivity());
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getNetWorkData();

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getNetWorkData();
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mViewModel.getWorkMoreData("", lastId, uTime);
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getNetWorkData();
    }

    private void getNetWorkData() {
        mViewModel.getRequestMerge();
    }

}
