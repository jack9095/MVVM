package com.example.fly.mvvm.core.bean.source;

import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.core.bean.BaseRepository;
import com.example.fly.mvvm.core.bean.pojo.banner.BannerListVo;
import com.example.fly.mvvm.core.bean.pojo.correct.WorkDetailVo;
import com.example.fly.mvvm.core.bean.pojo.correct.WorkRecommentVo;
import com.example.fly.mvvm.core.bean.pojo.correct.WorksListVo;
import com.example.fly.mvvm.network.rx.RxSubscriber;
import com.example.fly.mvvm_library.http.rx.RxSchedulers;

import rx.Observable;

/**
 */
public class WorkRepository extends BaseRepository {

    private Observable<WorksListVo> mWorkData;

    private Observable<BannerListVo> mBannerData;

    private Observable<WorkDetailVo> mWorkDetail;

    private Observable<WorkRecommentVo> mWorkRecommend;

    public void loadWorkData(String corrected, String rn) {
        mWorkData = apiService.getWorkData(corrected, rn);
    }

    public void loadWorkMoreData(String corrected, String lastId, String uTime, String rn, final CallBack<WorksListVo> listener) {
        addSubscribe(apiService.getWorkMoreData(lastId, uTime, rn)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<WorksListVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(WorksListVo worksListHotObject) {
                        listener.onNext(worksListHotObject);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));
    }

    public void loadBannerData(String posType,
                               String fCatalogId,
                               String sCatalogId,
                               String tCatalogId,
                               String province) {
        mBannerData = apiService.getBannerData(posType, fCatalogId, sCatalogId, tCatalogId, province);
    }

    public void loadRequestMerge(final CallBack<Object> listener) {
        addSubscribe(Observable.concatDelayError(mBannerData, mWorkData)
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

    public void loadWorkDetailData(String correctId, final CallBack<WorkDetailVo> listener) {
        mWorkDetail = apiService.getWorkDetailData(correctId);
    }

    public void loadWorkRecommendData(String correctId, final CallBack<WorkRecommentVo> listener) {
        mWorkRecommend = apiService.getWorkRecommendData(correctId);
    }

    public void loadWorkMergeData(final CallBack<Object> listener) {
        addSubscribe(Observable.concatDelayError(mWorkDetail, mWorkRecommend)
                .compose(RxSchedulers.<Object>io_main())
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
