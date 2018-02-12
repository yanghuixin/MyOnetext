package com.example.administrator.myonetext.bean;

/**
 * Created by Administrator on 2018/2/6.
 */

public class MyWallentRes {
    /**
     * state : 1
     * message :
     * istx : 1
     * i_status : 1
     * gcqAmt : 0
     * ktxAmt : 8.00
     * ytxAmt : 0
     * ytkAmt : 90.00
     * rsKey : DEEABF8D18452AFF84F70ED2DD1649DA
     */

    private int state;
    private String message;
    private int istx;
    private int i_status;
    private String gcqAmt;
    private String ktxAmt;
    private String ytxAmt;
    private String ytkAmt;
    private String rsKey;
    private String allAmt;

    public String getAllAmt() {
        return allAmt;
    }

    public void setAllAmt(String allAmt) {
        this.allAmt = allAmt;
    }

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

    public int getIstx() {
        return istx;
    }

    public void setIstx(int istx) {
        this.istx = istx;
    }

    public int getI_status() {
        return i_status;
    }

    public void setI_status(int i_status) {
        this.i_status = i_status;
    }

    public String getGcqAmt() {
        return gcqAmt;
    }

    public void setGcqAmt(String gcqAmt) {
        this.gcqAmt = gcqAmt;
    }

    public String getKtxAmt() {
        return ktxAmt;
    }

    public void setKtxAmt(String ktxAmt) {
        this.ktxAmt = ktxAmt;
    }

    public String getYtxAmt() {
        return ytxAmt;
    }

    public void setYtxAmt(String ytxAmt) {
        this.ytxAmt = ytxAmt;
    }

    public String getYtkAmt() {
        return ytkAmt;
    }

    public void setYtkAmt(String ytkAmt) {
        this.ytkAmt = ytkAmt;
    }

    public String getRsKey() {
        return rsKey;
    }

    public void setRsKey(String rsKey) {
        this.rsKey = rsKey;
    }
}
