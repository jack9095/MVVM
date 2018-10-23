package com.example.fly.mvvm.core.vm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.core.bean.pojo.qa.QaListVo;
import com.example.fly.mvvm.core.bean.source.QaRepository;
import com.example.fly.mvvm_library.base.AbsViewModel;
import com.example.fly.mvvm_library.stateview.StateConstants;

/**
 */
public class QaViewModel extends AbsViewModel<QaRepository> {

    private MutableLiveData<QaListVo> mQAData;

    public QaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<QaListVo> getQAList() {
        if (mQAData == null) {
            mQAData = new MutableLiveData<>();
        }
        return mQAData;
    }

    public void getQAList(String lastId, String rn) {
        mRepository.loadQAList(lastId, rn, new CallBack<QaListVo>() {
            @Override
            public void onNoNetWork() {
                loadState.postValue(StateConstants.NET_WORK_STATE);
            }

            @Override
            public void onNext(QaListVo articleObject) {
                mQAData.postValue(articleObject);
                loadState.postValue(StateConstants.SUCCESS_STATE);
            }

            @Override
            public void onError(String e) {
                loadState.postValue(StateConstants.ERROR_STATE);
            }
        });
    }
}
