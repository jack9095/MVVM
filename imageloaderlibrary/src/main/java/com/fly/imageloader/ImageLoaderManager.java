package com.fly.imageloader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.fly.imageloader.listener.IGetBitmapListener;
import com.fly.imageloader.okhttp.OnProgressListener;
import java.io.File;

public class ImageLoaderManager implements IImageLoaderClient {

    private static ImageLoaderManager mImageLoaderManager;
    private IImageLoaderClient mIImageLoaderClient;
    public String fileName;
    public int size;

    private ImageLoaderManager() {
        mIImageLoaderClient = new ImageLoaderClientImpl();
    }

    /**
     * 扩展
     */
    public void setImageLoaderClient(Context context, IImageLoaderClient client) {
        if (this.mIImageLoaderClient != null) {
            this.mIImageLoaderClient.clearMemoryCache(context);
        }

        if (this.mIImageLoaderClient != client) {
            this.mIImageLoaderClient = client;
            if (this.mIImageLoaderClient != null) {
                this.mIImageLoaderClient.init(context);
            }
        }
    }

    public static ImageLoaderManager getInstance() {
        if (mImageLoaderManager == null) {
            synchronized (ImageLoaderManager.class) {
                if (mImageLoaderManager == null) {
                    mImageLoaderManager = new ImageLoaderManager();
                }
            }
        }
        return mImageLoaderManager;
    }

    @Override
    public void init(Context context) {

    }

    @Override
    public File getCacheDir(Context context) {
        if (mIImageLoaderClient != null) {
            return mIImageLoaderClient.getCacheDir(context);
        }
        return null;
    }

    @Override
    public void clearMemoryCache(Context context) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.clearMemoryCache(context);
        }
    }

    @Override
    public void clearDiskCache(Context context) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.clearDiskCache(context);
        }
    }

    @Override
    public void getBitmapFromCache(Context context, String url, IGetBitmapListener listener) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.getBitmapFromCache(context, url, listener);
        }
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, boolean isCache) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayImage(context, url, imageView,isCache);
        }
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, int defRes,int time) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayImage(context, url, imageView, defRes,time);
        }
    }

    @Override
    public void displayImage(Context context, String url, ImageView imageView, int defRes, boolean cacheInMemory) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayImage(context, url, imageView, defRes, cacheInMemory);
        }
    }

    @Override
    public void displayCircleImage(Context context, String url, ImageView imageView, int defRes) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayCircleImage(context, url, imageView, defRes);
        }
    }

    @Override
    public void displayRoundImage(Context context, String url, ImageView imageView, int defRes, int radius) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayRoundImage(context, url, imageView, defRes, radius);
        }
    }

    @Override
    public void displayImageInResource(Context context, int resId,  ImageView imageView) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayImageInResource(context, resId,  imageView);
        }
    }

    @Override
    public void displayImageInResource(Context context, int resId, ImageView imageView, BitmapTransformation transformations) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayImageInResource(context, resId, imageView, transformations);
        }
    }

    @Override
    public void displayImageInResourceTransform(Context context, int resId, ImageView imageView, Transformation transformation, int errorResId) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayImageInResourceTransform(context, resId,  imageView, transformation, errorResId);
        }
    }

    @Override
    public void displayImageByNet(Context context, String url, ImageView imageView, int defRes, Transformation transformation) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayImageByNet(context, url,  imageView, defRes, transformation);
        }
    }

    @Override
    public void disPlayImageProgressByOnProgressListener(Context context, String url, ImageView imageView, int placeholderResId, int errorResId, OnProgressListener onProgressListener) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.disPlayImageProgressByOnProgressListener(context, url,imageView,placeholderResId,errorResId,onProgressListener);
        }
    }

    @Override
    public void displayImageThumbnail(Context context, String url, String backUrl, int thumbnailSize, ImageView imageView) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayImageThumbnail(context,url,backUrl,thumbnailSize,imageView);
        }
    }

    @Override
    public void displayImageThumbnail(Context context, String url, float thumbnailSize, ImageView imageView) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayImageThumbnail(context,url,thumbnailSize,imageView);
        }
    }


    @Override
    public void displayImageInResourceGif(Context context, int resId, ImageView imageView, BitmapTransformation transformations) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayImageInResourceGif(context,resId,imageView,transformations);
        }
    }

    @Override
    public void displayImageNetUrl(Context context, String resId, ImageView imageView, BitmapTransformation transformations) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayImageNetUrl(context,resId,imageView,transformations);
        }
    }

    @Override
    public void displayImageNetUrl(Context context, String resId, int defRes, ImageView imageView) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayImageNetUrl(context,resId,defRes,imageView);
        }
    }

    @Override
    public void displayImageNetUrlGif(Context context, String resId, ImageView imageView, BitmapTransformation transformations) {
        if (mIImageLoaderClient != null) {
            mIImageLoaderClient.displayImageNetUrlGif(context,resId,imageView,transformations);
        }
    }

}