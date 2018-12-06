package com.example.fly.imageloagutil.imageload;

import android.graphics.Bitmap;

/**
 * 设置此皆是为了业务需要，一般不需要关心网络请求回来的drawable，但是业务需要切换的地方的话，需要拿到网络请求回来的drawable
 */
public interface IGetBitmapListener {
    void onBitmap(Bitmap bitmap);
}
