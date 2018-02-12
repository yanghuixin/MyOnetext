package com.example.administrator.myonetext.bean;

/**
 * Created by Administrator on 2018/1/25.
 */

public class StoreCollectionDataRes {

    /**
     * status : 0
     * message : 该店铺已经收藏过了
     */

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
