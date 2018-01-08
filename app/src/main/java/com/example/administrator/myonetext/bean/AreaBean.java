package com.example.administrator.myonetext.bean;

/**
 * Created by Administrator on 2018/1/5.
 */

public class AreaBean {
    private int areaId;
    private int areaParentId;//上一层位置的id
    private String path;//从第一层到本层的id
    private int areaDeep;//地区深度
    private String areaName;//地区名
    private String nameEn;//地区英文名
    private String namePinyin;//地区拼音
    private String code;
    private boolean isSelected;//是否选中

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public int getAreaParentId() {
        return areaParentId;
    }

    public void setAreaParentId(int areaParentId) {
        this.areaParentId = areaParentId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getAreaDeep() {
        return areaDeep;
    }

    public void setAreaDeep(int areaDeep) {
        this.areaDeep = areaDeep;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNamePinyin() {
        return namePinyin;
    }

    public void setNamePinyin(String namePinyin) {
        this.namePinyin = namePinyin;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "AreaBean{" +
                "areaId=" + areaId +
                ", areaParentId=" + areaParentId +
                ", path='" + path + '\'' +
                ", areaDeep=" + areaDeep +
                ", areaName='" + areaName + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", namePinyin='" + namePinyin + '\'' +
                ", code='" + code + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
