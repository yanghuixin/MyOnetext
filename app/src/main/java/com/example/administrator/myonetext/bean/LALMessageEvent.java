package com.example.administrator.myonetext.bean;

/**
 * Created by Administrator on 2017/12/28.
 */

public class LALMessageEvent {
//    “详细地址”+address+"纬度"+wdpt+"经度"+jdpt
    public String address;
    public String wdpt;
    public String jdpt;

    public LALMessageEvent(String address, String wdpt, String jdpt) {
        this.address = address;
        this.wdpt = wdpt;
        this.jdpt = jdpt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWdpt() {
        return wdpt;
    }

    public void setWdpt(String wdpt) {
        this.wdpt = wdpt;
    }

    public String getJdpt() {
        return jdpt;
    }

    public void setJdpt(String jdpt) {
        this.jdpt = jdpt;
    }
}
