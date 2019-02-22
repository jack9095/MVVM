package com.example.fly.refreshlayoutdemo.blockcanary;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.fly.refreshlayoutdemo.BuildConfig;
import com.example.fly.refreshlayoutdemo.app.MyApplication;
import com.github.moduth.blockcanary.BlockCanaryContext;

public class AppBlockCanaryContext extends BlockCanaryContext {
    private static final String TAG = AppBlockCanaryContext.class.getSimpleName();

    @Override
    public String provideQualifier() {
        String qualifier = "";
        try {
            PackageInfo info = MyApplication.getAppContext().getPackageManager()
                    .getPackageInfo(MyApplication.getAppContext().getPackageName(), 0);
            qualifier += info.versionCode + "_" + info.versionName + "_YYB";
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "provideQualifier exception", e);
        }
        return qualifier;
    }

    @Override
    public int provideBlockThreshold() {
        return 500;
    }

    @Override
    public boolean displayNotification() {
        return BuildConfig.DEBUG;
    }

    @Override
    public boolean stopWhenDebugging() {
        return false;
    }
}
