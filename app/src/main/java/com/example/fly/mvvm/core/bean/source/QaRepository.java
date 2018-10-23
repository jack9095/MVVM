package com.example.fly.mvvm.core.bean.source;

import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.core.bean.BaseRepository;
import com.example.fly.mvvm.core.bean.pojo.qa.QaListVo;
import com.example.fly.mvvm.network.rx.RxSubscriber;
import com.example.fly.mvvm_library.http.rx.RxSchedulers;

/**
 */
public class QaRepository extends BaseRepository {

    public void loadQAList(String lastId, String rn, final CallBack<QaListVo> listener) {
        addSubscribe(apiService.getQAList(lastId, rn)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<QaListVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(QaListVo qaListObject) {
                        listener.onNext(qaListObject);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));
    }
}
