package com.example.fly.mvvm.core.bean.pojo.home;


public class CatagoryInfoVo {
    public String title;
    public int resId;

    public CatagoryInfoVo(String tvName, int tvIcon) {
        this.title = tvName;
        this.resId = tvIcon;
    }
}
