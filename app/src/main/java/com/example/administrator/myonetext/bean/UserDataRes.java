package com.example.administrator.myonetext.bean;

/**
 * Created by Administrator on 2018/1/16.
 */

public class UserDataRes {

    /**
     * Status : 1
     * Msg : 登录成功
     * uid : 24423
     * uname : %e7%8e%89
     * usafe : 43B13862D683FE6071DC849A6EA29EEF
     * uflag : 28221915-fbc2-428a-8981-f9429d674eb1
     */

    private int Status;
    private String Msg;
    private String uid;
    private String uname;
    private String usafe;
    private String uflag;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUsafe() {
        return usafe;
    }

    public void setUsafe(String usafe) {
        this.usafe = usafe;
    }

    public String getUflag() {
        return uflag;
    }

    public void setUflag(String uflag) {
        this.uflag = uflag;
    }
}
