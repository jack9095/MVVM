package com.example.fly.imageloagutil.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fly.imageloagutil.imageload.moudle.GlideApp;

public class ImageManager {
    public static void initImageWithFileCache(Context context, String url, ImageView imageView){
        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)  // 磁盘缓存
                .dontAnimate()
                .centerCrop()
                .into(imageView);
    }

    // 没有缓存
    public static void initImageNoCache(Context context, String url, ImageView imageView){
        GlideApp.with(context)
                .load(url)
                .skipMemoryCache(true)  // 跳过内存缓存
                .dontAnimate()
                .centerCrop()
                .into(imageView);
    }

    /**
     * 清除内存缓存
     * @param context
     */
    public static void clearMemoryCache(Context context){
        GlideApp.get(context).clearMemory();
    }

    /**
     * 清楚磁盘缓存
     * @param context
     */
    public static void clearFileCache(final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                GlideApp.get(context).clearDiskCache();
            }
        }).start();
    }
}
