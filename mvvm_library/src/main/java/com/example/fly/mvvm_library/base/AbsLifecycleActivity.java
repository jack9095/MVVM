package com.example.fly.mvvm_library.base;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.fly.mvvm_library.stateview.ErrorState;
import com.example.fly.mvvm_library.stateview.LoadingState;
import com.example.fly.mvvm_library.stateview.StateConstants;
import com.example.fly.mvvm_library.util.TUtil;
import com.tqzhang.stateview.stateview.BaseStateControl;

public abstract class AbsLifecycleActivity<T extends AbsViewModel> extends BaseActivity {

    protected T mViewModel;

    public AbsLifecycleActivity() {

    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mViewModel = VMProviders(this, (Class<T>) TUtil.getInstance(this, 0));
        mViewModel.loadState.observe(this, observer);
        dataObserver();
    }

    /**
     * https://blog.csdn.net/qq_22744433/article/details/78195155?locationNum=5&fps=1
     * 使用 ViewModel 方式：ViewModelProviders.of(宿主activity).get(A.class) 其中A extend ViewModel
     */
    protected <T extends ViewModel> T VMProviders(AppCompatActivity activity, @NonNull Class<T> modelClass) {
        return ViewModelProviders.of(activity).get(modelClass);
    }

    protected void dataObserver() {

    }

    // 加载错误的状态
    protected void showError(Class<? extends BaseStateControl> stateView, Object tag) {
        loadManager.showStateView(stateView, tag);
    }

    protected void showError(Class<? extends BaseStateControl> stateView) {
        showError(stateView, null);
    }

    protected void showSuccess() {
        loadManager.showSuccess();
    }

    protected void showLoading() {
        loadManager.showStateView(LoadingState.class);
    }


    /**
     * lifecycle 中 liveData的监听者(观察者)       加载失败   加载中   加载成功
     */
    protected Observer observer = new Observer<String>() {
        @Override
        public void onChanged(@Nullable String state) {
            if (!TextUtils.isEmpty(state)) {
                if (StateConstants.ERROR_STATE.equals(state)) {
                    showError(ErrorState.class, "2");
                } else if (StateConstants.NET_WORK_STATE.equals(state)) {
                    showError(ErrorState.class, "1");
                } else if (StateConstants.LOADING_STATE.equals(state)) {
                    showLoading();
                } else if (StateConstants.SUCCESS_STATE.equals(state)) {
                    showSuccess();
                }
            }
        }
    };
}
