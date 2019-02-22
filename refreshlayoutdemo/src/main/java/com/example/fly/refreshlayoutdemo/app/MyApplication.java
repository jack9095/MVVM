package com.example.fly.refreshlayoutdemo.app;

import android.app.Application;

import com.example.fly.refreshlayoutdemo.blockcanary.AppBlockCanaryContext;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;


public class MyApplication extends Application {

    private static MyApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
//        LeakCanary.install(this);
        // 在主进程初始化调用
//        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }

    public static MyApplication getAppContext(){
        return INSTANCE;
    }

}
