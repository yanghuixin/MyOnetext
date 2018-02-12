package com.example.administrator.myonetext.bean;

/**
 * Created by Administrator on 2018/1/10.
 */

public class RedPackageDataRes {

    /**
     * state : 2
     * message : 今日已签到！
     * num : 0
     */

    private int state;
    private String message;
    private int num;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
