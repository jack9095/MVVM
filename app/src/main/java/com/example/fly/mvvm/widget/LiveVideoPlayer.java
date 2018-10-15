package com.example.fly.mvvm.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;



public class LiveVideoPlayer extends StandardGSYVideoPlayer {

    public LiveVideoPlayer(Context context, Boolean fullFlag) {
        super(context, true);
    }

    public LiveVideoPlayer(Context context) {
        super(context);
    }

    public LiveVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void init(Context context) {
        super.init(context);
    }

    @Override
    protected void hideAllWidget() {
        super.hideAllWidget();
    }
}
