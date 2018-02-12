package com.example.administrator.myonetext.bean;

/**
 * Created by Administrator on 2018/1/10.
 */

public class OrderCancleDataRes {

    /**
     * state : 1
     * message : 操作成功！
     */

    private int state;
    private String message;

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
}
