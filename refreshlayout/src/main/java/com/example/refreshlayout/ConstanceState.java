package com.example.refreshlayout;

/**
 * 封装刷新加载的状态常量
 */
public interface ConstanceState {

    int PULL_NORMAL          = 0;  //普通状态
    int PULL_DOWN            = 1;  //下拉中
    int PULL_DOWN_RELEASABLE = 2;  //下拉可刷新
    int PULL_DOWN_RELEASE    = 3;  //下拉正在刷新
    int PULL_DOWN_RESET      = 4;  //下拉恢复正常
    int PULL_DOWN_FINISH     = 5;  //下拉完成
    int PULL_UP              = 6;  //上拉中
    int PULL_UP_RELEASABLE   = 7;  //上拉可刷新
    int PULL_UP_RELEASE      = 8;  //上拉正在刷新
    int PULL_UP_RESET        = 9;  //上拉恢复正常
    int PULL_UP_FINISH       = 10; //上拉完成
    int BOTTOM               = 11; //无更多

}
