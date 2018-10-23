package com.example.fly.mvvm.core.bean.source;

import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.core.bean.BaseRepository;
import com.example.fly.mvvm.core.bean.pojo.material.MaterialRecommendVo;
import com.example.fly.mvvm.core.bean.pojo.material.MaterialTypeVo;
import com.example.fly.mvvm.core.bean.pojo.material.MaterialVo;
import com.example.fly.mvvm.network.rx.RxSubscriber;
import com.example.fly.mvvm_library.http.rx.RxSchedulers;

/**
 */
public class MaterialRepository extends BaseRepository {

    public void loadMaterialList(String mCatalogId, String mLevel, String rn, final CallBack<MaterialVo> listener) {

        addSubscribe(apiService.getMaterialList(mCatalogId, mLevel, rn)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<MaterialVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(MaterialVo materialListVo) {
                        listener.onNext(materialListVo);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));
    }

    public void loadMaterialMoreList(String mCatalogId, String mLevel, String lastId, String rn, final CallBack<MaterialVo> listener) {
        addSubscribe(apiService.getMaterialMoreList(mCatalogId, mLevel, lastId, rn)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<MaterialVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(MaterialVo materialListVo) {
                        listener.onNext(materialListVo);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));
    }

    public void loadMaterialRemList(String mCatalogId, String lastId, String rn, final CallBack<MaterialRecommendVo> listener) {
        apiService.getMaterialRemList(mCatalogId, lastId, rn)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<MaterialRecommendVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(MaterialRecommendVo materialRecommendObject) {
                        listener.onNext(materialRecommendObject);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                });
    }

    public void loadMaterialTypeData(final CallBack<MaterialTypeVo> listener) {
        apiService.getMaterialInfo()
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<MaterialTypeVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(MaterialTypeVo materialTypeVo) {
                        listener.onNext(materialTypeVo);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                });
    }
}
