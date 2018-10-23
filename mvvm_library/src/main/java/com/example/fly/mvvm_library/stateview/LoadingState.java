package com.example.fly.mvvm_library.stateview;

import com.example.fly.mvvm_library.R;
import com.tqzhang.stateview.stateview.BaseStateControl;



public class LoadingState extends BaseStateControl {

    /**
     * 创建布局
     * @return
     */
    @Override
    protected int onCreateView() {
        return R.layout.loading_view;
    }

    /**
     * 提示状态是否显示
     * @return
     */
    @Override
    public boolean isVisible() {
        return super.isVisible();
    }

}
