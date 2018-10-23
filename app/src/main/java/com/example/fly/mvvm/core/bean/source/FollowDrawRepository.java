package com.example.fly.mvvm.core.bean.source;

import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.core.bean.BaseRepository;
import com.example.fly.mvvm.core.bean.pojo.followdraw.FollowDrawRecommendVo;
import com.example.fly.mvvm.core.bean.pojo.followdraw.FollowDrawTypeVo;
import com.example.fly.mvvm.network.rx.RxSubscriber;
import com.example.fly.mvvm_library.http.rx.RxSchedulers;

/**
 *
 */
public class FollowDrawRepository extends BaseRepository {

    public void loadFollowDrawList(String mainTypeId, String lastId, String rn, final CallBack<FollowDrawRecommendVo> listener) {
        addSubscribe(apiService.getollowDrawList(mainTypeId, lastId, rn)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<FollowDrawRecommendVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(FollowDrawRecommendVo followDrawRecommendObject) {
                        listener.onNext(followDrawRecommendObject);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));
    }

    public void loadFollowDrawRemList(String lastId, String rn, final CallBack<FollowDrawRecommendVo> listener) {
        addSubscribe(apiService.getFollowDrawRemList(lastId, rn)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<FollowDrawRecommendVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(FollowDrawRecommendVo followDrawRecommendObject) {
                        listener.onNext(followDrawRecommendObject);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));
    }

    public void loadFollowDrawType(final CallBack<FollowDrawTypeVo> listener) {
        addSubscribe(apiService.getFollowDrawType()
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<FollowDrawTypeVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(FollowDrawTypeVo followDrawTypeVo) {
                        listener.onNext(followDrawTypeVo);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));
    }
}
