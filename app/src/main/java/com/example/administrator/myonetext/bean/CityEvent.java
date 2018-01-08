package com.example.administrator.myonetext.bean;

/**
 * Created by Administrator on 2018/1/5.
 */

public class CityEvent {
    private boolean flag;//true表示出发城市，false表示到达城市
    private String cities;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getCities() {
        return cities;
    }

    public void setCities(String cities) {
        this.cities = cities;
    }

    public CityEvent(boolean flag, String cities) {
        this.flag = flag;
        this.cities = cities;
    }
}
