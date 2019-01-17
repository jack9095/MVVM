package com.example.fly.mvvm.app;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.bumptech.glide.Glide;
import com.codemonkeylabs.fpslibrary.FrameDataCallback;
import com.codemonkeylabs.fpslibrary.TinyDancer;
import com.example.fly.mvvm.config.URL;
import com.example.fly.mvvm_library.http.HttpHelper;
import com.example.fly.mvvm_library.stateview.ErrorState;
import com.example.fly.mvvm_library.stateview.LoadingState;
import com.squareup.leakcanary.LeakCanary;
import com.tqzhang.stateview.core.LoadState;

/**
 * Created by fei.wang on 2018/10/15.
 *
 */
public class App extends Application implements ComponentCallbacks2{

    private static App instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LeakCanary.install(instance);
        new HttpHelper.Builder()
                .initOkHttp()
                .createRetrofit(URL.BASE_URL)
                .build();
        new LoadState.Builder()
                .register(new ErrorState())
                .register(new LoadingState())
                .setDefaultCallback(LoadingState.class)
                .build();

        TinyDancer.create()
                .show(this);

        //alternatively
        TinyDancer.create()
                .redFlagPercentage(.1f)
                .startingXPosition(200)
                .startingYPosition(600)
                .show(this);

        TinyDancer.create()
                .addFrameDataCallback(new FrameDataCallback() {
                    @Override
                    public void doFrame(long previousFrameNS, long currentFrameNS, int droppedFrames) {
                    }
                })
                .show(this);

    }

    public static App getInstance(){
        return instance;
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }
}
