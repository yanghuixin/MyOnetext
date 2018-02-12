package com.example.administrator.myonetext.bean;

/**
 * Created by Administrator on 2018/1/23.
 */

public class ProductCollectionDataRes {

    /**
     * state : 1
     * message : 收藏成功
     * coll : 2
     */

    private int state;
    private String message;
    private String coll;

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

    public String getColl() {
        return coll;
    }

    public void setColl(String coll) {
        this.coll = coll;
    }
}
