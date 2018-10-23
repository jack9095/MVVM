package com.example.fly.mvvm.core.bean.pojo.dynamic;


import com.example.fly.mvvm.core.bean.pojo.article.ArticleInfoVo;
import com.example.fly.mvvm.core.bean.pojo.correct.WorkInfoVo;
import com.example.fly.mvvm.core.bean.pojo.correct.WorksListVo;
import com.example.fly.mvvm.core.bean.pojo.course.CourseInfoVo;
import com.example.fly.mvvm.core.bean.pojo.followdraw.FollowDrawInfoVo;
import com.example.fly.mvvm.core.bean.pojo.live.LiveRecommendVo;
import com.example.fly.mvvm.core.bean.pojo.material.MatreialSubjectVo;
import com.example.fly.mvvm.core.bean.pojo.user.UserInfoVo;

public class DynamicInfoVo {
    public String feedid;
    public String uid;
    public String action_type;
    public String subjecttype;
    public String subjectid;
    public String title;
    public String ctime;
    public UserInfoVo userinfo;
    public WorksListVo.Works tweet_info;
    public WorkInfoVo correct_info;
    public MatreialSubjectVo material_info;
    public ArticleInfoVo lecture_info;
    public LiveRecommendVo live_info;
    public CourseInfoVo course_info;
    public FollowDrawInfoVo lesson_info;
}
