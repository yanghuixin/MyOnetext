package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 */

public class MoreproductDataRes {


    /**
     * state : 1
     * Msg : [{"pid":36397,"shopprice":"26000.00","pname":"普洱生茶 攸乐古茶 2007年生茶 古树纯料普洱茶 357克普洱茶 七子饼茶 传统名山普洱茶","picurl":"http://img1.tealg.com/Product/Main/220X220/201412/bb6fe8b2-2bd4-4120-8a29-5b910521c1b5.jpg"},{"pid":36398,"shopprice":"26000.00","pname":"普洱生茶 攸乐古茶 2007年生茶 古树纯料普洱茶 357克普洱茶 七子饼茶 传统名山普洱茶","picurl":"http://img1.tealg.com/Product/Main/220X220/201412/bb6fe8b2-2bd4-4120-8a29-5b910521c1b5.jpg"},{"pid":36399,"shopprice":"26000.00","pname":"普洱生茶 攸乐古茶 2007年生茶 古树纯料普洱茶 357克普洱茶 七子饼茶 传统名山普洱茶","picurl":"http://img1.tealg.com/Product/Main/220X220/201412/bb6fe8b2-2bd4-4120-8a29-5b910521c1b5.jpg"},{"pid":36400,"shopprice":"26000.00","pname":"普洱生茶 攸乐古茶 2007年生茶 古树纯料普洱茶 357克普洱茶 七子饼茶 传统名山普洱茶","picurl":"http://img1.tealg.com/Product/Main/220X220/201412/bb6fe8b2-2bd4-4120-8a29-5b910521c1b5.jpg"}]
     */

    private int state;
    private List<MsgBean> Msg;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<MsgBean> getMsg() {
        return Msg;
    }

    public void setMsg(List<MsgBean> Msg) {
        this.Msg = Msg;
    }

    public static class MsgBean {
        /**
         * pid : 36397
         * shopprice : 26000.00
         * pname : 普洱生茶 攸乐古茶 2007年生茶 古树纯料普洱茶 357克普洱茶 七子饼茶 传统名山普洱茶
         * picurl : http://img1.tealg.com/Product/Main/220X220/201412/bb6fe8b2-2bd4-4120-8a29-5b910521c1b5.jpg
         */

        private int pid;
        private String shopprice;
        private String pname;
        private String picurl;

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getShopprice() {
            return shopprice;
        }

        public void setShopprice(String shopprice) {
            this.shopprice = shopprice;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }
    }
}
