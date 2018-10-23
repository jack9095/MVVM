package com.example.fly.mvvm.core.bean.source;


import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.core.bean.BaseRepository;
import com.example.fly.mvvm.core.bean.pojo.activity.ActivityListVo;
import com.example.fly.mvvm.network.rx.RxSubscriber;
import com.example.fly.mvvm_library.http.rx.RxSchedulers;

/**
 *
 */
public class ActivityRepository extends BaseRepository {

    public void loadActivityList(String lastId, String rn, final CallBack listener) {
        addSubscribe(apiService.getActivityList(lastId, rn)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<ActivityListVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(ActivityListVo activityListObject) {
                        listener.onNext(activityListObject);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));


    }

}
