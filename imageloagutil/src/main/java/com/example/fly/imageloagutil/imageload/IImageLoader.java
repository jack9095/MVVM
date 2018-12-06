package com.example.fly.imageloagutil.imageload;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by fly on 2018/12/6.
 *
 */
public interface IImageLoader {


    /**
     * gif图
     */
    void gifImage(Context context,int gifRes,ImageView imageView);

    void gifImage(Context context,String gifUrl,ImageView imageView);

    /**
     * 加载本地图片
     */
    void localImage(Context context,int imageRes,ImageView imageView);

    /**
     * 加载网络图片
     */
    void localImage(Context context,String imageUrl,ImageView imageView);

    /**
     * 圆形图片
     */
    void displayCircleImage(Context context, String url, ImageView imageView, int defRes);

    void displayCircleImage(Fragment fragment, String url, ImageView imageView, int defRes);

    /**
     * 圆角图片
     */
    void displayRoundImage(Context context, String url, ImageView imageView, int defRes, int radius);

    void displayRoundImage(Fragment fragment, String url, ImageView imageView, int defRes, int radius);

    /**
     * 模糊
     */
    void displayBlurImage(Context context, String url, int blurRadius, IGetDrawableListener listener);

    void displayBlurImage(Context context, String url, ImageView imageView, int defRes, int blurRadius);

    void displayBlurImage(Context context, int resId, ImageView imageView, int blurRadius);

    void displayBlurImage(Fragment fragment, String url, ImageView imageView, int defRes, int blurRadius);


    void displayImageInResource(Context context, int resId, ImageView imageView, BitmapTransformation transformations);

    void displayImageInResource(Fragment fragment, int resId, ImageView imageView, BitmapTransformation transformations);


    // 高级扩展 transformation 需要装换的那种图像的风格，错误图片，或者是，正在加载中的错误图
    void displayImageInResourceTransform(Activity activity, int resId, ImageView imageView, Transformation transformation, int errorResId);
    void displayImageInResourceTransform(Context context, int resId, ImageView imageView, Transformation transformation, int errorResId);
    void displayImageInResourceTransform(Fragment fragment, int resId, ImageView imageView, Transformation transformation, int errorResId);

    //这是对网络图片，进行的图片操作，使用的glide中的方法
    void displayImageByNet(Context context, String url, ImageView imageView, int defRes,Transformation transformation);
    void displayImageByNet(Fragment fragment, String url, ImageView imageView, int defRes,Transformation transformation);
    void displayImageByNet(Activity activity, String url, ImageView imageView, int defRes,Transformation transformation);

}
