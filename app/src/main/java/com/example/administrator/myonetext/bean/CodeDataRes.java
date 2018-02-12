package com.example.administrator.myonetext.bean;

/**
 * Created by Administrator on 2018/1/16.
 */

public class CodeDataRes {
    /**
     * access_token : 5_ZkvkAknuO6ev8oXqlvdQxEpSKBaQE4wqKQEHpbjXM_L-vPLx998S-xCq2IhUF04Ix-zoVYNH_MCRYGbFvi1_Di1ZuydxBVbxZ-9WR4CKxqk
     * expires_in : 7200
     * refresh_token : 5_E4Con6vN-9NyZZbdRLZKh2Usxp5-EbLLVN7xi4jz7qRB5Yyt55YfdGkBRkuxDJSb-j1LZz0E5eaIienI-jYjH9BWi2Vp6LOHVWEy_m5idXA
     * openid : o3MFb0eynIVjI3S5KGtG_jKzAL20
     * scope : snsapi_userinfo
     * unionid : o15Fi0_M0hm-TIvHUJcLlBnTFbAg
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
