package com.example.fly.mvvm.core.bean.pojo.book;

import com.example.fly.mvvm.core.bean.pojo.BaseVo;

import java.util.ArrayList;

public class BookListVo extends BaseVo {
    public Data data;

    public static class Data {
        public ArrayList<BookVo> content;
    }

}
