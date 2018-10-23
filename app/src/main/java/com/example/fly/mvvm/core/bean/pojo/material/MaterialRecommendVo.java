package com.example.fly.mvvm.core.bean.pojo.material;

import com.example.fly.mvvm.core.bean.pojo.BaseVo;

import java.io.Serializable;
import java.util.ArrayList;

public class MaterialRecommendVo extends BaseVo implements Serializable{

    public MaterialReData data;

    public static class MaterialReData implements Serializable{
        public ArrayList<MatreialSubjectVo> content;
    }

}
