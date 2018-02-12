package com.example.administrator.myonetext.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ShoppingCartBean extends DataSupport implements Serializable {
    private int id;
    private int productPid;
    private String imageUrl;
    private String storName;
    private String shoppingName;

    private int dressSize;
    private String attribute;
    private String SubtotalPrice;
    private String price;
    public boolean isChoosed;
    public boolean isCheck = false;
    private String count;


    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public ShoppingCartBean() {
    }

    public ShoppingCartBean(int productPid, String imageUrl,String storName, String shoppingName, String price, String count) {
        this.productPid=productPid;
        this.storName=storName;
        this.imageUrl = imageUrl;
        this.shoppingName = shoppingName;
        this.price = price;
        this.count = count;
    }

    public String getStorName() {
        return storName;
    }

    public void setStorName(String storName) {
        this.storName = storName;
    }

    public String getSubtotalPrice() {
        return SubtotalPrice;
    }

    public void setSubtotalPrice(String subtotalPrice) {
        SubtotalPrice = subtotalPrice;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShoppingName() {
        return shoppingName;
    }

    public void setShoppingName(String shoppingName) {
        this.shoppingName = shoppingName;
    }


    public int getDressSize() {
        return dressSize;
    }

    public void setDressSize(int dressSize) {
        this.dressSize = dressSize;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
