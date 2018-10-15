package com.example.fly.mvvm.event;


import android.arch.lifecycle.MutableLiveData;

import com.example.fly.mvvm.utils.Preconditions;

import java.util.HashMap;
import java.util.Map;


/**
 * 事件总线
 *
 */
public class LiveBus {

    private static volatile LiveBus instance;

    private final Map<Object, MutableLiveData<Object>> mLiveBus;

    private LiveBus() {
        mLiveBus = new HashMap<>();
    }

    public static LiveBus getDefault() {
        if (instance == null) {
            synchronized (LiveBus.class) {
                if (instance == null) {
                    instance = new LiveBus();
                }
            }
        }
        return instance;
    }

    public <T> MutableLiveData<T> subscribe(Object subscriber, Class<T> tMutableLiveData) {
        Preconditions.checkNotNull(subscriber);
        Preconditions.checkNotNull(tMutableLiveData);
        if (!mLiveBus.containsKey(subscriber)) {
            mLiveBus.put(subscriber, new MutableLiveData<>());
        }
        return (MutableLiveData<T>) mLiveBus.get(subscriber);
    }
}
