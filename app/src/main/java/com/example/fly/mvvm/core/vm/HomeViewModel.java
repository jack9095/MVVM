package com.example.fly.mvvm.core.vm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.core.bean.pojo.banner.BannerListVo;
import com.example.fly.mvvm.core.bean.pojo.home.HomeListVo;
import com.example.fly.mvvm.core.bean.pojo.home.HomeMergeVo;
import com.example.fly.mvvm.core.bean.source.HomeRepository;
import com.example.fly.mvvm_library.base.AbsViewModel;
import com.example.fly.mvvm_library.stateview.StateConstants;

/**
 */
public class HomeViewModel extends AbsViewModel<HomeRepository> {

    private MutableLiveData<HomeListVo> homeData;  // 列表

    private MutableLiveData<BannerListVo> bannerData;  // 轮播

    private MutableLiveData<HomeMergeVo> mergeData;

    private final HomeMergeVo homeMergeVo = new HomeMergeVo();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<HomeListVo> getHomeList() {
        if (homeData == null) {
            homeData = new MutableLiveData<>();
        }
        return homeData;
    }

    public LiveData<BannerListVo> getBannerList() {
        if (bannerData == null) {
            bannerData = new MutableLiveData<>();
        }
        return bannerData;
    }

    public LiveData<HomeMergeVo> getMergeData() {
        if (mergeData == null) {
            mergeData = new MutableLiveData<>();
        }
        return mergeData;
    }

    private void loadHomeList(String id) {
        mRepository.loadHomeData(id);
    }

    private void getBannerData(String posType,
                               String fCatalogId,
                               String sCatalogId,
                               String tCatalogId,
                               String province) {
        mRepository.loadBannerData(posType, fCatalogId, sCatalogId, tCatalogId, province);

    }

    public void getRequestMerge() {
        getBannerData("1", "4", "109", "", null);
        loadHomeList("0");
        mRepository.loadRequestMerge(new CallBack<Object>() {
            @Override
            public void onNoNetWork() {
                loadState.postValue(StateConstants.NET_WORK_STATE);
            }

            @Override
            public void onNext(Object object) {
                if (object instanceof BannerListVo) {
                    homeMergeVo.bannerListVo = (BannerListVo) object;
                } else if (object instanceof HomeListVo) {
                    homeMergeVo.homeListVo = (HomeListVo) object;
                    mergeData.postValue(homeMergeVo);
                    loadState.postValue(StateConstants.SUCCESS_STATE);
                }

            }

            @Override
            public void onError(String e) {

            }
        });
    }
}
