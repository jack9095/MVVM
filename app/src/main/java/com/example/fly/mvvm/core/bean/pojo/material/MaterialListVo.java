package com.example.fly.mvvm.core.bean.pojo.material;


import java.util.List;

/**
 */
public class MaterialListVo {
    public List<MatreialSubjectVo> matreialsubject;

    public MaterialListVo(List<MatreialSubjectVo> matreialsubject) {
        this.matreialsubject = matreialsubject;
    }

    public void setMatreialsubject(List<MatreialSubjectVo> matreialsubject) {
        this.matreialsubject = matreialsubject;
    }
}
