package com.fly.pojo;

/**
 */
public class FootVo {
    public String desc;

    public int state;

    public FootVo(int state) {
        this.state = state;
    }

    public FootVo(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }
}
