package com.example.fly.mvvm.core.ui.fragment;

import android.os.Bundle;

import com.example.fly.mvvm.R;
import com.example.fly.mvvm.base.BaseViewPagerFragment;
import com.example.fly.mvvm.core.bean.pojo.course.CourseTypeVo;
import com.example.fly.mvvm.core.vm.CourseViewModel;
import com.example.fly.mvvm_library.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class VideoFragment extends BaseViewPagerFragment<CourseViewModel> {
    private List<CourseTypeVo.DataBean> titleName = new ArrayList<>();

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public void initView(Bundle state) {
        super.initView(state);
        setTitle(getResources().getString(R.string.video_title_name));
        getTabData();
    }

    @Override
    protected void dataObserver() {
        mViewModel.getCourseType().observe(this, courseTypeVo -> {
            if (courseTypeVo == null) {
                return;
            }
            setData(courseTypeVo);
        });
    }

    @Override
    protected void onStateRefresh() {
        super.onStateRefresh();
        getTabData();
    }

    @Override
    protected String[] createPageTitle() {
        return mArrTitles;
    }

    @Override
    protected List<BaseFragment> createFragments() {
        return mFragments;
    }

    private void getTabData() {
        mViewModel.getCourseTypeData();
    }


    private void setData(CourseTypeVo courseTypeVo) {
        mArrTitles = new String[courseTypeVo.data.size() + 1];
        titleName.clear();
        CourseTypeVo.DataBean dataBean = new CourseTypeVo.DataBean();
        dataBean.name = getResources().getString(R.string.recommend_tab_name);
        mArrTitles[0] = getResources().getString(R.string.recommend_tab_name);
        titleName.add(dataBean);
        for (int j = 0; j < courseTypeVo.data.size(); j++) {
            titleName.add(courseTypeVo.data.get(j));
            mArrTitles[j + 1] = (courseTypeVo.data.get(j).name);
        }
        initFragment();
        setAdapter();
    }

    private void initFragment() {
        for (int i = 0; i < titleName.size(); i++) {
            if (i == 0) {
                CourseRecommendFragment courseRecommendFragment = CourseRecommendFragment.newInstance();
                mFragments.add(courseRecommendFragment);
            } else {
                CourseListFragment courseListFragment = CourseListFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString("f_catalog_id", titleName.get(i).id);
                bundle.putSerializable("s_catalog", titleName.get(i).s_catalog);
                courseListFragment.setArguments(bundle);
                mFragments.add(courseListFragment);
            }

        }
    }
}
