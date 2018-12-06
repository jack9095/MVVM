package com.example.fly.imageloagutil.imageload.moudle;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

@GlideModule
public class DiskCacheMoudle extends AppGlideModule {

    /**
     * 默认情况下，Glide使用 LruResourceCache ，
     * 这是 MemoryCache 接口的一个缺省实现，使用固定大小的内存和 LRU 算法。
     * LruResourceCache 的大小由 Glide 的 MemorySizeCalculator 类来决定，这个类主要关注设备的内存类型，
     * 设备 RAM 大小，以及屏幕分辨率。应用程序可以自定义 MemoryCache 的大小，
     * 具体是在它们的 AppGlideModule 中使用applyOptions(Context, GlideBuilder) 方法配置 MemorySizeCalculator
     *
     * 建议使用glide 默认的情况
     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
//        //        设置缓存大小为80mb
//        int memoryCacheSizeBytes = 1024 * 1024 * 80; // 80mb
//        //        设置内存缓存大小
//        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
//
//        //        根据SD卡是否可用选择是在内部缓存还是SD卡缓存
////        if(SDCardUtils.isSDCardEnable()){
////            builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, "gaoDunImages", memoryCacheSizeBytes));
////        }else {
//            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "gaoDunImages", memoryCacheSizeBytes));
////        }
    }
    //    针对V4用户可以提升速度
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        super.registerComponents(context, glide, registry);
//        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }

}