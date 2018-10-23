package com.example.fly.mvvm.core.bean.source;

import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.core.bean.BaseRepository;
import com.example.fly.mvvm.core.bean.pojo.dynamic.DynamicListVo;
import com.example.fly.mvvm.network.rx.RxSubscriber;
import com.example.fly.mvvm_library.http.rx.RxSchedulers;

/**
 *
 */
public class DynamicRepository extends BaseRepository {

    public void loadDynamicList(String rn, String token, String lastId, final CallBack<DynamicListVo> listener) {
        addSubscribe(apiService.getDynamicList(rn, token, lastId)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<DynamicListVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(DynamicListVo dynamicListVo) {
                        listener.onNext(dynamicListVo);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));

    }
}
