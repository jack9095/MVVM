package com.example.fly.mvvm.callback;

public interface CallBack<T> {

    /**
     * no network
     */
    void onNoNetWork();

    /**
     * @param t
     */
    void onNext(T t);

    /**
     * @param e
     */
    void onError(String e);
}
