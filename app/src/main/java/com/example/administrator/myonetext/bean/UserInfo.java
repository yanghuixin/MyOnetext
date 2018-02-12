package com.example.administrator.myonetext.bean;

/**
 * Created by Administrator on 2018/1/19.
 */

public class UserInfo {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;
    private String uid;
    private String usafe;
    private String uflag;
    private String productNum;
    private String upicurl;//会员头像图片url
    private String dlno;//会员推广号

    public String getUpicurl() {
        return upicurl;
    }

    public void setUpicurl(String upicurl) {
        this.upicurl = upicurl;
    }

    public String getDlno() {
        return dlno;
    }

    public void setDlno(String dlno) {
        this.dlno = dlno;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
