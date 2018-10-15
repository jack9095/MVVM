package com.example.fly.mvvm.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;



public class NestedViewPager extends ViewPager {

    private boolean isLocked;

    public NestedViewPager(Context context) {
        super(context);
        isLocked = false;
    }

    public NestedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        isLocked = false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isLocked) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public void toggleLock() {
        isLocked = !isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean isLocked() {
        return isLocked;
    }
}
