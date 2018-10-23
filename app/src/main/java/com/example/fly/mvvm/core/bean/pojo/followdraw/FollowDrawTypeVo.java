package com.example.fly.mvvm.core.bean.pojo.followdraw;

import com.example.fly.mvvm.core.bean.pojo.BaseVo;

import java.util.List;

public class FollowDrawTypeVo extends BaseVo {


    public List<DataBean> data;

    public static class DataBean {
        public int maintypeid;
        public String maintypename;
    }
}
