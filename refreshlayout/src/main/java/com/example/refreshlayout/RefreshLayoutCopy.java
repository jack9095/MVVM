package com.example.refreshlayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.Size;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.Scroller;

/**
 * 1.支持刷新，无更多布局的ViewGroup;
 * 2.支持coordinatorLayout，可适配继承NestedScrollParent的父组件;
 * 3.支持listView等其他控件;
 * 4.支持加载布局自定义;
 * 默认下拉有效的高度为80dp
 * https://www.linuxidc.com/Linux/2016-01/127276.htm  Scroller 详解
 */
public class RefreshLayoutCopy extends ViewGroup implements NestedScrollingParent, NestedScrollingChild {

    private static final int MSG_PULL_UP = 100;     // 上拉完成 handler发送消息的 what
    private static final int MSG_DOWN_RESET = 101;  // 刷新完成 handler发送消息的 what
    private static final int SCROLL_NONE = -1; //无滚动
    private static final int SCROLL_UP = 0;  // 上拉(currY>lastY)
    private static final int SCROLL_DOWN = 1;  // 下拉(currY<lastY)
    private static final float DECELERATE_INTERPOLATION_FACTOR = 2F; //滑动阻尼因子
    private static int ANIMATION_EXTEND_DURATION = 200; // 刷新动画的时长
    private int childHeaderHeight = 150;

    // NestedScrolling 系列的系统帮助类
    private NestedScrollingParentHelper mNestedScrollingParentHelper;
    private NestedScrollingChildHelper mNestedScrollingChildHelper;

    private final int[] mParentScrollConsumed = new int[2];
    private final int[] mParentOffsetInWindow = new int[2];

    private boolean pullDownEnable = true;  // 是否允许下拉刷新
    private boolean enable = true;  // 是否允许视图滑动
    private boolean isLastScrollComplete;   // 是否上一次滑动已结束
    private int direction;

    private View mTarget;  // 就是这个自定义ViewGroup刷新加载控件，内部加载的滑动控件（非滑动控件也可以的)）
    private Scroller mScroller; // Scroller只是个计算器，提供插值计算，让滚动过程具有动画属性，但它并不是UI，也不是辅助UI滑动，反而是单纯地为滑动提供计算

    private int effectivePullDownRange;  // 默认下拉的有效高度为80
    private int effectivePullUpRange;    // 默认上拉的有效高度为45
    private int ignorePullRange;   // 无用范围 不触发刷新加载的范围

    private IHeaderRefreshView mHeaderWrapper; // 刷新头部实现的接口

    private View mHeaderView;  // 刷新头控件

    private int currentState;  // 当前状态
    private float mLastY;     // 最后的垂直位移
    private float mLastX;     // 最后的水平位移

    private OnRefreshListener mRefreshListener; // 刷新和加载的接口监听

    // java代码创建视图的时候被调用
    public RefreshLayoutCopy(Context context) {
        this(context, null);
    }

    // xml创建视图并且没有指定StyleAttr是调用 至于attrs，是默认指定了的
    public RefreshLayoutCopy(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    //xml创建视图并且指定StyleAttr是调用,一般不需需要
    public RefreshLayoutCopy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        currentState = ConstanceState.PULL_DOWN_RESET;  // 默认下拉恢复正常状态
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        mScroller = new Scroller(getContext(), new LinearInterpolator());  // 匀速滑动
        effectivePullDownRange = (int) (getContext().getResources().getDisplayMetrics().density * 80); // 默认下拉的有效高度为80
        effectivePullUpRange = (int) (getContext().getResources().getDisplayMetrics().density * 45);   // 默认上拉的有效高度为45
        ignorePullRange = (int) (getContext().getResources().getDisplayMetrics().density * 8);

        setNestedScrollingEnabled(true);
    }

    /**
     * 在Xml写的布局文件最终会在通过Pull解析的方式转成代码的
     * onFinishInflate的作用，就是在xml加载组件完成后调用的。这个方法一般在自制ViewGroup的时候调用
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        mViewText = getChildAt(0);
//        myDiyView = getChildAt(1);
    }

    //设置刷新布局
    public void setHeaderView(IHeaderRefreshView header) {
        if (header == null) return;
        this.mHeaderWrapper = header;
        this.mHeaderView = header.getHeaderView();
        addView(mHeaderView); // 把自定义的头部布局添加到ViewGroup中
    }

    public void setHeaderView(IHeaderRefreshView header, int height) {
        if (header == null) return;
        this.mHeaderWrapper = header;
        this.mHeaderView = header.getHeaderView();
        this.childHeaderHeight = height;
        addView(mHeaderWrapper.getHeaderView());
    }

    public void removeHeaderView() {
        if (mHeaderWrapper == null) return;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == mHeaderView) {
                removeView(mHeaderView);
                mHeaderView = null;
                mHeaderWrapper = null;
                break;
            }
        }
    }

    public IHeaderRefreshView getHeaderView() {
        return mHeaderWrapper;
    }

    /**
     * 刷新动画的时长
     *
     * @param duration 时长 毫秒
     */
    public void setRefreshAnimationDuration(int duration) {
        ANIMATION_EXTEND_DURATION = duration;
    }

    /**
     * 是否允许视图滑动
     *
     * @param enable true 可以滑动
     */
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    // 设置下拉刷新事件的有效高度
    public void setEffectivePullDownRange(int effectivePullDownRange) {
        this.effectivePullDownRange = effectivePullDownRange;
    }

    // 设置刷新头的高度 一般不用设置
    public void setChildHeaderHeight(int childHeaderHeight) {
        this.childHeaderHeight = childHeaderHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    // getResources().getDisplayMetrics().density 得到屏幕的相对密度
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) { // 就是这个自定义ViewGroup刷新加载控件，内部加载的滑动控件（非滑动控件也可以的)） 内部控件为空就去寻找
            ensureTarget();
        }
        if (mTarget == null) return; // 寻找不到内部控件，就停止测量方法
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == mHeaderView) {
                child.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec((int) (getResources().getDisplayMetrics().density * childHeaderHeight), MeasureSpec.EXACTLY));
            } else {
                child.measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY),
                        MeasureSpec.makeMeasureSpec(getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == mHeaderView) {
                child.layout(0, -child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
            } else {
                child.layout(getPaddingLeft(), getPaddingTop(), getPaddingLeft() + child.getMeasuredWidth(), getMeasuredHeight() - getPaddingBottom());
            }
        }
    }

    /**
     * 判断这个控件是否加载的滑动控件 （RecyclerView、ListView、ScrollerView）
     */
    private void ensureTarget() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child != mHeaderView) {
                mTarget = child; // 就是这个自定义ViewGroup刷新加载控件，内部加载的滑动控件（非滑动控件也可以的)）
                break;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        float y = ev.getY();
        float x = ev.getX();
        // direction
        direction = (ev.getAction() == MotionEvent.ACTION_UP || y == mLastY ? SCROLL_NONE : y > mLastY ? SCROLL_UP : SCROLL_DOWN);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mTarget != null && !ViewCompat.isNestedScrollingEnabled(mTarget)) {
                    if (y > mLastY) {   // 上滑
                        intercept = !canChildScrollUp();
                    } else if (y < mLastY) {
                        intercept = !canChildScrollDown();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                direction = SCROLL_NONE; // 停止滑动
                break;
        }
        boolean vertical = Math.abs(y - mLastY) - Math.abs(x - mLastX) > 0; // Y轴滑动的距离大于X轴滑动的距离为 true
        mLastY = y;
        mLastX = x;
        return intercept && vertical;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(getScrollY()) > ignorePullRange) {
                    requestDisallowInterceptTouchEvent(true);
                }
                if (enable) {
                    doScroll((int) (mLastY - y));
                }
                break;
            case MotionEvent.ACTION_UP:
                onStopScroll();
                requestDisallowInterceptTouchEvent(false);
                break;
        }
        mLastY = y;
        return true;
    }

    /**
     * @param disallowIntercept
     */
    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if ((Build.VERSION.SDK_INT < 21 && mTarget instanceof AbsListView)
                || (mTarget != null && !ViewCompat.isNestedScrollingEnabled(mTarget))) {
        } else {
            super.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private boolean canChildScrollDown() {
        if (Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0 && (absListView.getLastVisiblePosition() != absListView.getChildCount() - 1
                        || absListView.getChildAt(absListView.getChildCount() - 1).getBottom() > absListView.getMeasuredHeight());
            } else
                return ViewCompat.canScrollVertically(mTarget, 1) || mTarget.getScrollY() < mTarget.getMeasuredHeight() - getMeasuredHeight();
        } else
            return ViewCompat.canScrollVertically(mTarget, 1);
    }

    @SuppressLint("ObsoleteSdkInt")
    private boolean canChildScrollUp() {
        if (Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0 && (absListView.getChildAt(0).getTop() < absListView.getPaddingTop()
                        || absListView.getFirstVisiblePosition() > 0);
            } else
                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    /**
     * 拉伸状态判断
     * <p>
     * 上拉中或上拉抬起手指，并且在加载更多，无效
     * 下拉中或下拉抬起手指，并且在下拉刷新，无效
     */
    private boolean isRefreshingOrLoading() {
        return (direction != SCROLL_UP && currentState >= ConstanceState.PULL_UP_RELEASE && currentState < ConstanceState.PULL_UP_FINISH)
                || (direction != SCROLL_DOWN && currentState >= ConstanceState.PULL_DOWN_RELEASE && currentState < ConstanceState.PULL_DOWN_FINISH);
    }

    private void doScroll(int dy) {
        if (!isLastScrollComplete) return;
        if (isRefreshingOrLoading()) return;

        if (dy > 0) {

            //下拉刷新
            if (getScrollY() > 0) {   //说明不是到达顶部的下拉，无效下拉
                if (Math.abs(getScrollY()) < effectivePullUpRange) {
                    if (currentState != ConstanceState.PULL_UP)
                        updateStatus(ConstanceState.PULL_UP);
                }
            } else {
                if (!pullDownEnable) return;
                if (Math.abs(getScrollY()) >= effectivePullDownRange) {
                    //到达下拉最大距离，增加阻尼因子
                    dy /= computeInterpolationFactor(getScrollY());
                    if (currentState != ConstanceState.PULL_DOWN_RELEASABLE)
                        updateStatus(ConstanceState.PULL_DOWN_RELEASABLE);
                } else {
                    if (currentState != ConstanceState.PULL_DOWN)
                        updateStatus(ConstanceState.PULL_DOWN);
                }
            }
        }

        dy /= DECELERATE_INTERPOLATION_FACTOR;
        scrollBy(0, dy);
    }

    private void onStopScroll() {

        if (isRefreshingOrLoading()) return;
        if ((Math.abs(getScrollY()) >= effectivePullDownRange) && getScrollY() < 0) {
            //有效下拉
            updateStatus(ConstanceState.PULL_DOWN_RELEASE);
            mScroller.startScroll(0, getScrollY(), 0, -(getScrollY() + effectivePullDownRange));
            mScroller.extendDuration(ANIMATION_EXTEND_DURATION);
            invalidate();
//        } else if ((Math.abs(getScrollY()) >= effectivePullUpRange) && getScrollY() > 0) {
//            //有效上拉
//            updateStatus(ConstanceState.PULL_UP_RELEASE);
//            mScroller.startScroll(0, getScrollY(), 0, -(getScrollY() - effectivePullUpRange));
//            mScroller.extendDuration(ANIMATION_EXTEND_DURATION);
//            invalidate();
        } else {
            //无效距离，还原
            updateStatus(ConstanceState.PULL_NORMAL);
        }
    }

    /**
     * computeScroll也不是来让ViewGroup滑动的，真正让ViewGroup滑动的是scrollTo,scrollBy。
     * computeScroll的作用是计算ViewGroup如何滑动。而computeScroll是通过draw来调用的。
     */
    @Override
    public void computeScroll() {
        // 计算 CurrX 和 CurrY ，并检测是否已完成滚动
        if (mScroller.computeScrollOffset()) {
            isLastScrollComplete = false; // 上一次滑动未结束
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY()); // 会重复调用 invalidate();
            invalidate();
        } else {
            isLastScrollComplete = true; // 上一次滑动已结束
            if (currentState == ConstanceState.PULL_DOWN_RESET) { // 下拉恢复正常
                currentState = ConstanceState.PULL_DOWN_FINISH;   // 下拉完成
            }
            if (currentState == ConstanceState.PULL_UP_RESET) { // 上拉恢复正常
                currentState = ConstanceState.PULL_UP_FINISH;   // 上拉完成
            }
        }
    }

    private void updateStatus(int state) {
        switch (state) {
            case ConstanceState.PULL_NORMAL:  // 普通状态
                pullDownReset();
                break;
            case ConstanceState.PULL_DOWN:     // 下拉中
                if (mHeaderWrapper != null) {
                    mHeaderWrapper.pullDown();
                }
                break;
            case ConstanceState.PULL_DOWN_RELEASABLE: // 下拉可刷新
                if (mHeaderWrapper != null) {
                    mHeaderWrapper.pullDownReleasable();
                }
                break;
            case ConstanceState.PULL_DOWN_RELEASE:     // 下拉正在刷新
                if (mHeaderWrapper != null) {
                    mHeaderWrapper.pullDownRelease();
                }
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                }
                setEnable(false);
                break;
            case ConstanceState.PULL_DOWN_RESET:   // 下拉恢复正常
                pullDownReset();
                break;
            case ConstanceState.PULL_UP_RESET:    // 上拉恢复正常
                pullUpReset();
                break;
        }

        currentState = state;
    }

    // 上拉恢复正常
    private void pullUpReset() {
        setEnable(true);
        if (Math.abs(getScrollY()) != 0) {
            mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
//            mScroller.extendDuration(ANIMATION_EXTEND_DURATION);
            mScroller.extendDuration(0);
            invalidate();  //触发onDraw()
        }
        mHandler.sendEmptyMessageDelayed(MSG_PULL_UP, 0);
//        mHandler.sendEmptyMessageDelayed(MSG_PULL_UP, 5);
    }

    @SuppressWarnings("Handlerleak")
    private Handler mHandler = new Handler() {
        private int pullCount = 0;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_PULL_UP:  // 上拉完成
                    pullCount++;

                    if (canChildScrollDown()) {
                        pullCount = 0;
                        mHandler.removeMessages(MSG_PULL_UP);
                        mTarget.scrollBy(0, (int) (getResources().getDisplayMetrics().density * 6));
                    } else {
                        if (pullCount >= 20) {
                            pullCount = 0;
                            mHandler.removeMessages(MSG_PULL_UP);
                        } else {
                            mHandler.sendEmptyMessageDelayed(MSG_PULL_UP, 5);
                        }
                    }
                    break;
                case MSG_DOWN_RESET:  // 下拉刷新完成
                    if (!isLastScrollComplete) {
                        mHandler.sendEmptyMessageDelayed(MSG_DOWN_RESET, 5);
                    } else
                        onRefreshComplete();
                    break;
            }
        }
    };

    // 下拉恢复正常
    private void pullDownReset() {
        setEnable(true);
        if (Math.abs(getScrollY()) != 0) {
            mScroller.startScroll(0, getScrollY(), 0, -getScrollY());
            mScroller.extendDuration(ANIMATION_EXTEND_DURATION);
            invalidate();
        }
    }

    // 计算阻尼插值因子
    private float computeInterpolationFactor(int dy) {
        int absY = Math.abs(dy);
        int delta;
        if (dy > 0) {
            if (absY <= effectivePullUpRange) { // 默认上拉的有效高度为45
                return DECELERATE_INTERPOLATION_FACTOR;  //滑动阻尼因子 默认为2f
            }
            delta = (absY - effectivePullUpRange) / 50;  //增加50，阻尼系数+1
        } else {
            if (absY <= effectivePullDownRange) {
                return DECELERATE_INTERPOLATION_FACTOR;
            }
            delta = (absY - effectivePullDownRange) / 50;  //增加50，阻尼系数+1
        }

        return DECELERATE_INTERPOLATION_FACTOR + delta;
    }

    // 刷新完成
    public void onRefreshComplete() {
        if (!isLastScrollComplete) {
            mHandler.sendEmptyMessageDelayed(MSG_DOWN_RESET, 5);
            return;
        }
        updateStatus(ConstanceState.PULL_DOWN_RESET);
    }

    // https://www.jianshu.com/p/e7b6fa788ae6
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacksAndMessages(null);
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mRefreshListener = listener;
    }

    // 是否允许下拉刷新 true 允许
    public void setPullDownEnable(boolean pullDownEnable) {
        this.pullDownEnable = pullDownEnable;
    }

    //是否允许视图滑动
    public void setScrollEnable(boolean enable) {
        this.enable = enable;
    }

    //--------------------  NestedScrollParent  -------------------------------//

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return isEnabled() && (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);

        //告诉父类开始滑动
        startNestedScroll(axes & ViewCompat.SCROLL_AXIS_VERTICAL);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        //只有在自己滑动的情形下才进行预消耗
        if (getScrollY() != 0) {

            //这里相当于做了一个边界条件
            if (getScrollY() > 0 && dy < 0 && Math.abs(dy) >= Math.abs(getScrollY())) {  //上拉过程中下拉
                consumed[1] = getScrollY();
                scrollTo(0, 0);
                return;
            }

            if (getScrollY() < 0 && dy > 0 && Math.abs(dy) >= Math.abs(getScrollY())) {
                consumed[1] = getScrollY();
                scrollTo(0, 0);
                return;
            }

            int yConsumed = Math.abs(dy) >= Math.abs(getScrollY()) ? getScrollY() : dy;
            doScroll(yConsumed);
            consumed[1] = yConsumed;
        }

        //父类消耗剩余距离
        final int[] parentConsumed = mParentScrollConsumed;
        if (dispatchNestedPreScroll(dx - consumed[0], dy - consumed[1], parentConsumed, null)) {
            consumed[0] += parentConsumed[0];
            consumed[1] += parentConsumed[1];
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

        dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, mParentOffsetInWindow);

        int dy = dyUnconsumed + mParentOffsetInWindow[1];

        if (enable) {
            if (direction == SCROLL_DOWN) return;                  //用户不开启加载
            if (direction == SCROLL_UP && !pullDownEnable) return;                  //用户不开启下拉
            doScroll(dy);
        }
    }

    @Override
    public void onStopNestedScroll(View child) {
        onStopScroll();
        mNestedScrollingParentHelper.onStopNestedScroll(child);

        stopNestedScroll();
    }

    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    //------------------------------ NestedScrollChild ---------------------//


    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable @Size(value = 2) int[] consumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }
}