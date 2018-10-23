package com.example.fly.mvvm_library.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.fly.mvvm_library.util.TUtil;

/**
 * jetPack新组件使用 ViewModel  LiveData
 * @param <T>
 */
public class AbsViewModel<T extends AbsRepository> extends AndroidViewModel {

    public MutableLiveData<String> loadState;

    public T mRepository;

    public AbsViewModel(@NonNull Application application) {
        super(application);
        loadState = new MutableLiveData<>();
        mRepository = TUtil.getNewInstance(this, 0);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mRepository != null) {
            mRepository.unSubscribe();  // 清除所有订阅 释放内存 （Rx)）
        }

    }
}
