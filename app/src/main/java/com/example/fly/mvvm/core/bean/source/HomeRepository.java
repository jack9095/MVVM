package com.example.fly.mvvm.core.bean.source;


import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.core.bean.BaseRepository;
import com.example.fly.mvvm.core.bean.pojo.banner.BannerListVo;
import com.example.fly.mvvm.core.bean.pojo.home.HomeListVo;
import com.example.fly.mvvm.network.rx.RxSubscriber;
import com.example.fly.mvvm_library.http.rx.RxSchedulers;

import rx.Observable;

/**
 *
 */
public class HomeRepository extends BaseRepository {

    private Observable<HomeListVo> mHomeListObservable;

    private Observable<BannerListVo> mBannerObservable;

    public void loadHomeData(String id) {
        mHomeListObservable = apiService.getHomeData(id);
    }

    public void loadBannerData(String posType,
                               String fCatalogId,
                               String sCatalogId,
                               String tCatalogId,
                               String province) {
        mBannerObservable = apiService.getBannerData(posType, fCatalogId, sCatalogId, tCatalogId, province);
    }


    public void loadRequestMerge(final CallBack listener) {
        addSubscribe(Observable.concatDelayError(mBannerObservable, mHomeListObservable)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<Object>() {

                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(Object o) {
                        listener.onNext(o);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));
    }

}
