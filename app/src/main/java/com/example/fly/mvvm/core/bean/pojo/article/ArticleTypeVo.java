package com.example.fly.mvvm.core.bean.pojo.article;


import com.example.fly.mvvm.core.bean.pojo.BaseVo;

import java.util.List;


public class ArticleTypeVo extends BaseVo {


    public List<DataBean> data;

    public static class DataBean {
        public Integer maintypeid;
        public String maintypename;
    }
}
