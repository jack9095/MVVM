package com.example.fly.mvvm.core.vm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.config.Constants;
import com.example.fly.mvvm.core.bean.pojo.dynamic.DynamicListVo;
import com.example.fly.mvvm.core.bean.source.DynamicRepository;
import com.example.fly.mvvm_library.base.AbsViewModel;
import com.example.fly.mvvm_library.stateview.StateConstants;

import static com.example.fly.mvvm.utils.Preconditions.checkNotNull;

/**
 */
public class DynamicViewModel extends AbsViewModel<DynamicRepository> {

    private MutableLiveData<DynamicListVo> mDynamicListData;

    public DynamicViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<DynamicListVo> getDynamicList() {
        if (mDynamicListData == null) {
            mDynamicListData = new MutableLiveData<>();
        }
        return mDynamicListData;
    }

    public void getDynamicList(String token, String lastId) {
        checkNotNull(token);
        mRepository.loadDynamicList(Constants.PAGE_RN, token, lastId, new CallBack<DynamicListVo>() {
            @Override
            public void onNoNetWork() {
                loadState.postValue(StateConstants.NET_WORK_STATE);
            }

            @Override
            public void onNext(DynamicListVo dynamicListVo) {
                mDynamicListData.postValue(dynamicListVo);
                loadState.postValue(StateConstants.SUCCESS_STATE);
            }

            @Override
            public void onError(String e) {
                loadState.postValue(StateConstants.ERROR_STATE);
            }
        });

    }
}
