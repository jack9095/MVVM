package com.example.fly.mvvm.core.bean;


import com.example.fly.mvvm.network.ApiService;
import com.example.fly.mvvm_library.base.AbsRepository;
import com.example.fly.mvvm_library.http.HttpHelper;

/**
 *
 */
public abstract class BaseRepository extends AbsRepository {

    protected ApiService apiService;

    public BaseRepository() {
        if (null == apiService) {
            apiService = HttpHelper.getInstance().create(ApiService.class);
        }
    }

}
