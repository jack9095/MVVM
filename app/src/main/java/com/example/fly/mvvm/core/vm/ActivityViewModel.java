package com.example.fly.mvvm.core.vm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.config.Constants;
import com.example.fly.mvvm.core.bean.pojo.activity.ActivityListVo;
import com.example.fly.mvvm.core.bean.source.ActivityRepository;
import com.example.fly.mvvm_library.base.AbsViewModel;
import com.example.fly.mvvm_library.stateview.StateConstants;

import static com.example.fly.mvvm.utils.Preconditions.checkNotNull;

/**
 */
public class ActivityViewModel extends AbsViewModel<ActivityRepository> {

    private MutableLiveData<ActivityListVo> activityData;

    public ActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ActivityListVo> getActivityList() {
        if (activityData == null) {
            activityData = new MutableLiveData<>();
        }
        return activityData;
    }

    public void getActivityList(String id) {
        checkNotNull(id);
        mRepository.loadActivityList(id, Constants.PAGE_RN, new CallBack<ActivityListVo>() {
            @Override
            public void onNoNetWork() {
                loadState.postValue(StateConstants.NET_WORK_STATE);
            }

            @Override
            public void onNext(ActivityListVo activityListVo) {
                activityData.postValue(activityListVo);
                loadState.postValue(StateConstants.SUCCESS_STATE);
            }

            @Override
            public void onError(String e) {
                loadState.postValue(StateConstants.ERROR_STATE);
            }
        });

    }
}
