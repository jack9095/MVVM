package com.example.fly.mvvm.core.bean.pojo.correct;


import com.example.fly.mvvm.core.bean.pojo.BaseVo;
import com.example.fly.mvvm.core.bean.pojo.course.CourseInfoVo;
import com.example.fly.mvvm.core.bean.pojo.live.LiveRecommendVo;

import java.util.ArrayList;
import java.util.List;

public class WorkRecommentVo extends BaseVo {

    public Data data;

    public static class Data {
        public ArrayList<WorkInfoVo> content;
        public List<CourseInfoVo> course;
        public List<LiveRecommendVo> live;
    }

}
