package com.example.fly.mvvm.core.vm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.config.Constants;
import com.example.fly.mvvm.core.bean.pojo.followdraw.FollowDrawRecommendVo;
import com.example.fly.mvvm.core.bean.pojo.followdraw.FollowDrawTypeVo;
import com.example.fly.mvvm.core.bean.source.FollowDrawRepository;
import com.example.fly.mvvm_library.base.AbsViewModel;
import com.example.fly.mvvm_library.stateview.StateConstants;

/**
 */
public class FollowDrawViewModel extends AbsViewModel<FollowDrawRepository> {

    private MutableLiveData<FollowDrawTypeVo> mFollowDrawTypeData;

    private MutableLiveData<FollowDrawRecommendVo> mFollowDrawData;

    private MutableLiveData<FollowDrawRecommendVo> mFollowDrawRemData;

    public FollowDrawViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<FollowDrawTypeVo> getFollowDrawType() {
        if (mFollowDrawTypeData == null) {
            mFollowDrawTypeData = new MutableLiveData<>();
        }
        return mFollowDrawTypeData;
    }

    public LiveData<FollowDrawRecommendVo> geFollowDrawList() {
        if (mFollowDrawData == null) {
            mFollowDrawData = new MutableLiveData<>();
        }
        return mFollowDrawData;
    }

    public LiveData<FollowDrawRecommendVo> getFollowDrawRemList() {
        if (mFollowDrawRemData == null) {
            mFollowDrawRemData = new MutableLiveData<>();
        }
        return mFollowDrawRemData;
    }


    public void getFollowDrawTypeData() {
        mRepository.loadFollowDrawType(new CallBack<FollowDrawTypeVo>() {
            @Override
            public void onNoNetWork() {

            }

            @Override
            public void onNext(FollowDrawTypeVo followDrawTypeVo) {
                mFollowDrawTypeData.postValue(followDrawTypeVo);
                loadState.postValue(StateConstants.SUCCESS_STATE);
            }

            @Override
            public void onError(String e) {

            }
        });

    }

    public void getFollowDrawList(String maintypeid, String lastId) {
        mRepository.loadFollowDrawList(maintypeid, lastId, Constants.PAGE_RN, new CallBack<FollowDrawRecommendVo>() {
            @Override
            public void onNoNetWork() {

            }

            @Override
            public void onNext(FollowDrawRecommendVo followDrawRecommendObject) {
                mFollowDrawData.postValue(followDrawRecommendObject);
                loadState.postValue(StateConstants.SUCCESS_STATE);
            }

            @Override
            public void onError(String e) {

            }
        });

    }

    public void getFollowDrawRemList(String lastId) {
        mRepository.loadFollowDrawRemList(lastId, Constants.PAGE_RN, new CallBack<FollowDrawRecommendVo>() {
            @Override
            public void onNoNetWork() {

            }

            @Override
            public void onNext(FollowDrawRecommendVo followDrawRecommendObject) {
                mFollowDrawRemData.postValue(followDrawRecommendObject);
                loadState.postValue(StateConstants.SUCCESS_STATE);
            }

            @Override
            public void onError(String e) {

            }
        });

    }
}
