package code.shiming.com.imageloader471;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import code.shiming.com.imageloader471.okhttp.ProgressManager;


@GlideModule
public class AppGlideModuleProgress extends AppGlideModule {

    /**
     * 磁盘缓存
     Glide 使用 DiskLruCacheWrapper 作为默认的 磁盘缓存 。
     DiskLruCacheWrapper 是一个使用 LRU 算法的固定大小的磁盘缓存。默认磁盘大小为 250 MB ，
     位置是在应用的 缓存文件夹 中的一个 特定目录 。

     * @param context
     * @param builder
     */
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        //假如应用程序展示的媒体内容是公开的（从无授权机制的网站上加载，或搜索引擎等），
        //那么应用可以将这个缓存位置改到外部存储：
//        builder.setDiskCache(new ExternalDiskCacheFactory(context));


         //2、无论使用内部或外部磁盘缓存，应用程序都可以改变磁盘缓存的大小：
//        int diskCacheSizeBytes = 1024 *1024 *100;  //100 MB
//        builder.setDiskCache(new InternalDiskCacheFactory(context, diskCacheSizeBytes));


        //3、应用程序还可以改变缓存文件夹在外存或内存上的名字：
//        int diskCacheSizeBytes = 1024 * 1024 *100; // 100 MB
//        builder.setDiskCache(new InternalDiskCacheFactory(context, cacheFolderName, diskCacheSizeBytes));


        //4、应用程序还可以自行选择 DiskCache 接口的实现，并提供自己的 DiskCache.Factory 来创建缓存。
        // Glide 使用一个工厂接口来在后台线程中打开 磁盘缓存 ，
        // 这样方便缓存做诸如检查路径存在性等的IO操作而不用触发 严格模式 。
//        builder.setDiskCache(new DiskCache.Factory() {
//            @Override
//            public DiskCache build() {
//                return new YourAppCustomDiskCache();
//            }
//        });

    }

    /**
     * 为了维持对 Glide v3 的 GlideModules 的向后兼容性，
     * Glide 仍然会解析应用程序和所有被包含的库中的 AndroidManifest.xml 文件，
     * 并包含在这些清单中列出的旧 GlideModules 模块类。
     如果你已经迁移到 Glide v4 的 AppGlideModule 和 LibraryGlideModule ，你可以完全禁用清单解析。
     这样可以改善 Glide 的初始启动时间，并避免尝试解析元数据时的一些潜在问题。要禁用清单解析，
     请在你的 AppGlideModule 实现中复写 isManifestParsingEnabled() 方法：
     * @return
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
