package com.fly.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.fly.imageloader.listener.IGetBitmapListener;
import com.fly.imageloader.okhttp.OnGlideImageViewListener;
import com.fly.imageloader.okhttp.OnProgressListener;

import java.io.File;

public class ImageLoaderManager implements IImageLoaderClient {
    /**
     * volatile 关键字：我个人理解的是：使用volatile关键字的程序在并发时能够正确执行。
     * 但是它不能够代替synchronized关键字。在网上找到这么一句话：
     * 观察加入volatile关键字和没有加入volatile关键字时所生成的汇编代码发现，
     * 加入volatile关键字时，会多出一个lock前缀指令lock前缀指令实际上相当于一个内存屏障（也成内存栅栏），
     * 内存屏障会提供3个功能：1）它确保指令重排序时不会把其后面的指令排到内存屏障之前的位置，
     * 也不会把前面的指令排到内存屏障的后面；即在执行到内存屏障这句指令时，在它前面的操作已经全部完成；
     * 2）它会强制将对缓存的修改操作立即写入主存；3）如果是写操作，它会导致其他CPU中对应的缓存行无效。
     */
    private volatile static ImageLoaderManager instance;
    private IImageLoaderClient client;

    private ImageLoaderManager() {
        client = new GlideImageLoaderClient();
    }

    /**
     * 设置 图片加载库客户端
     */
    public void setImageLoaderClient(Context context, IImageLoaderClient client) {
        if (this.client != null) {
            this.client.clearMemoryCache(context);
        }

        if (this.client != client) {
            this.client = client;
            if (this.client != null) {
                this.client.init(context);
            }
        }
    }

    public static ImageLoaderManager getInstance() {
        if (instance == null) {
            synchronized (ImageLoaderManager.class) {
                if (instance == null) {
                    instance = new ImageLoaderManager();
                }
            }
        }
        return instance;
    }

    @Override
    public void init(Context context) {

    }

    @Override
    public void destroy(Context context) {
        if (client != null) {
            client.destroy(context);
            client = null;
        }
        instance = null;
    }

    @Override
    public File getCacheDir(Context context) {
        if (client != null) {
            return client.getCacheDir(context);
        }
        return null;
    }

    @Override
    public void clearMemoryCache(Context context) {
        if (client != null) {
            client.clearMemoryCache(context);
        }
    }

    @Override
    public void clearDiskCache(Context context) {
        if (client != null) {
            client.clearDiskCache(context);
        }
    }

    /**
     * 不是
     */
    @Override
    public void getBitmapFromCache(Context context, String url, IGetBitmapListener listener) {
        if (client != null) {
            client.getBitmapFromCache(context, url, listener);
        }
    }


    @Override
    public void displayImage(Context context, String url, ImageView imageView, boolean isCache) {
        if (client != null) {
            client.displayImage(context, url, imageView,isCache);
        }
    }


    @Override
    public void displayImage(Context context, String url, ImageView imageView, int defRes,int time) {
        if (client != null) {
            client.displayImage(context, url, imageView, defRes,time);
        }
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, int defRes, boolean cacheInMemory) {
        if (client != null) {
            client.displayImage(context, url, imageView, defRes, cacheInMemory);
        }
    }

    @Override
    public void displayCircleImage(Context context, String url, ImageView imageView, int defRes) {
        if (client != null) {
            client.displayCircleImage(context, url, imageView, defRes);
        }
    }

    @Override
    public void displayRoundImage(Context context, String url, ImageView imageView, int defRes, int radius) {
        if (client != null) {
            client.displayRoundImage(context, url, imageView, defRes, radius);
        }
    }

    @Override
    public void displayImageInResource(Context context, int resId,  ImageView imageView) {
        if (client != null) {
            client.displayImageInResource(context, resId,  imageView);
        }
    }

    @Override
    public void displayImageInResource(Context context, int resId, ImageView imageView, BitmapTransformation transformations) {
        if (client != null) {
            client.displayImageInResource(context, resId, imageView, transformations);
        }
    }

    @Override
    public void displayImageInResourceTransform(Context context, int resId, ImageView imageView, Transformation transformation, int errorResId) {
        if (client != null) {
            client.displayImageInResourceTransform(context, resId,  imageView, transformation, errorResId);
        }
    }

    @Override
    public void displayImageByNet(Context context, String url, ImageView imageView, int defRes, Transformation transformation) {
        if (client != null) {
            client.displayImageByNet(context, url,  imageView, defRes, transformation);
        }
    }

    @Override
    public void disPlayImageProgress(Context context, String url, ImageView imageView, int placeholderResId, int errorResId, OnGlideImageViewListener listener) {
        if (client != null) {
            client.disPlayImageProgress(context, url,imageView,placeholderResId,errorResId,listener);
        }
    }

    @Override
    public void disPlayImageProgressByOnProgressListener(Context context, String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener) {
        if (client != null) {
            client.disPlayImageProgressByOnProgressListener(context, url,imageView,placeholderResId,errorResId,onProgressListener);
        }
    }

    @Override
    public void displayImageThumbnail(Context context, String url, String backUrl, int thumbnailSize, ImageView imageView) {
        if (client != null) {
            client.displayImageThumbnail(context,url,backUrl,thumbnailSize,imageView);
        }
    }

    @Override
    public void displayImageThumbnail(Context context, String url, float thumbnailSize, ImageView imageView) {
        if (client != null) {
            client.displayImageThumbnail(context,url,thumbnailSize,imageView);
        }
    }


    @Override
    public void displayImageInResourceGif(Context context, int resId, ImageView imageView, BitmapTransformation transformations) {
        if (client != null) {
            client.displayImageInResourceGif(context,resId,imageView,transformations);
        }
    }

    @Override
    public void displayImageNetUrl(Context context, String resId, ImageView imageView, BitmapTransformation transformations) {
        if (client != null) {
            client.displayImageNetUrl(context,resId,imageView,transformations);
        }
    }

    @Override
    public void displayImageNetUrlGif(Context context, String resId, ImageView imageView, BitmapTransformation transformations) {
        if (client != null) {
            client.displayImageNetUrlGif(context,resId,imageView,transformations);
        }
    }

}