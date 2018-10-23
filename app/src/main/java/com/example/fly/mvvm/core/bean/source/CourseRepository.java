package com.example.fly.mvvm.core.bean.source;


import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.core.bean.BaseRepository;
import com.example.fly.mvvm.core.bean.pojo.course.CourseDetailRemVideoVo;
import com.example.fly.mvvm.core.bean.pojo.course.CourseDetailVo;
import com.example.fly.mvvm.core.bean.pojo.course.CourseListVo;
import com.example.fly.mvvm.core.bean.pojo.course.CourseRemVo;
import com.example.fly.mvvm.core.bean.pojo.course.CourseTypeVo;
import com.example.fly.mvvm.network.rx.RxSubscriber;
import com.example.fly.mvvm_library.http.rx.RxSchedulers;

import rx.Observable;

/**
 *
 */
public class CourseRepository extends BaseRepository {


    private Observable<CourseDetailVo> mCourseDetailDataObservable;

    private Observable<CourseDetailRemVideoVo> mCourseDetailRemVideoObservable;

    public void loadCourseList(String fCatalogId,String lastId, String rn, final CallBack<CourseListVo> onResultCallBack) {
        addSubscribe(apiService.getCourseList(fCatalogId,lastId,rn)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<CourseListVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        onResultCallBack.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(CourseListVo courseListVo) {
                        onResultCallBack.onNext(courseListVo);
                    }

                    @Override
                    public void onFailure(String msg) {
                        onResultCallBack.onError(msg);
                    }
                }));
    }

    public void loadCourseRemList(final CallBack<CourseRemVo> onResultCallBack) {
        addSubscribe(apiService.getCourseRemList()
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<CourseRemVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        onResultCallBack.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(CourseRemVo courseRemVo) {
                        onResultCallBack.onNext(courseRemVo);
                    }

                    @Override
                    public void onFailure(String msg) {
                        onResultCallBack.onError(msg);
                    }
                }));
    }

    public void loadCourseType(final CallBack<CourseTypeVo> listener) {
        addSubscribe(apiService.getCourseType()
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<CourseTypeVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(CourseTypeVo courseTypeVo) {
                        listener.onNext(courseTypeVo);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));
    }

    public void loadCourseDetailData(String id) {
        mCourseDetailDataObservable=apiService.getVideoDetailsData(id,"");

    }

    public void loadCourseDetailRemData(String id, String fCatalogId, String sCatalogId, String teacherId, String rn) {
        mCourseDetailRemVideoObservable=apiService.getVideoAboutData(id,fCatalogId,sCatalogId,teacherId,rn);
    }


    public void loadCourseDetailMerge() {

    }

}
