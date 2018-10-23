package com.example.fly.mvvm.core.bean.source;

import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.core.bean.BaseRepository;
import com.example.fly.mvvm.core.bean.pojo.live.LiveListVo;
import com.example.fly.mvvm.core.bean.pojo.live.LiveTypeVo;
import com.example.fly.mvvm.network.rx.RxSubscriber;
import com.example.fly.mvvm_library.http.rx.RxSchedulers;

/**
 *
 */
public class LiveRepository extends BaseRepository {

    public void loadLiveList(String mCatalogId, String id, String rn, final CallBack<LiveListVo> onResultCallBack) {
        addSubscribe(apiService.getLiveList(mCatalogId, id, rn)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<LiveListVo>() {

                    @Override
                    public void onSuccess(LiveListVo liveListVo) {
                        onResultCallBack.onNext(liveListVo);

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }


    public void loadLiveRemList(String id, String rn, final CallBack<LiveListVo> onResultCallBack) {
        addSubscribe(apiService.getLiveRem(id, rn)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<LiveListVo>() {

                    @Override
                    public void onSuccess(LiveListVo liveListVo) {
                        onResultCallBack.onNext(liveListVo);
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                }));
    }

    public void loadLiveTypeData(final CallBack<LiveTypeVo> listener) {
        addSubscribe(apiService.getLiveType()
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<LiveTypeVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(LiveTypeVo liveTypeVo) {
                        listener.onNext(liveTypeVo);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));

    }
}
