package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */

public class EnterpriseCommodityDataRes {
    /**
     * Status : 1
     * Msg : [{"pid":36412,"pname":"普洱生茶 攸乐古茶 2007年生茶 古树纯料普洱茶 357克普洱茶 七子饼茶 传统名山普洱茶","picurl":"http://img1.tealg.com/Product/Main/220X220/201412/bb6fe8b2-2bd4-4120-8a29-5b910521c1b5.jpg","shopprice":26000,"iviews":2230,"ibuys":10,"buymax":0,"usintegral":7000,"istock":0,"pno":"90005987","iwxts":"","baddress":"北京市西城区马连道路14号大德盛茶城1-","ibid":416,"icommentcnt":0,"iscore":0},{"pid":36411,"pname":"普洱生茶 南糯半坡 2007年生茶 古树纯料 头拨春茶 七子饼普洱茶 357克普洱茶 正统名山普洱茶","picurl":"http://img1.tealg.com/Product/Main/220X220/201412/f6948e0f-7fd9-44c4-9b42-815fcb7b1919.jpg","shopprice":8800,"iviews":635,"ibuys":6,"buymax":0,"usintegral":1800,"istock":4,"pno":"90007345","iwxts":"","baddress":"北京市西城区马连道路14号大德盛茶城1-","ibid":416,"icommentcnt":0,"iscore":0},{"pid":36410,"pname":"普洱生茶 易武麻黑 2007年生茶 普洱 纯料古树 春茶头拨 357克普洱茶 七子饼 名山普洱茶","picurl":"http://img1.tealg.com/Product/Main/220X220/201412/137c8976-d5b9-4900-a0dc-e015587bc708.jpg","shopprice":8800,"iviews":1456,"ibuys":10,"buymax":0,"usintegral":1000,"istock":10,"pno":"90007685","iwxts":"","baddress":"北京市西城区马连道路14号大德盛茶城1-","ibid":416,"icommentcnt":0,"iscore":0},{"pid":36409,"pname":"普洱生茶 勐海班章单株茶 2009年生茶 班章春茶 古树普洱茶 357克普洱茶 七子饼普洱茶","picurl":"http://img1.tealg.com/Product/Main/220X220/201412/98a5f285-fcfb-4559-a5fd-0289e2a174a3.jpg","shopprice":38000,"iviews":1471,"ibuys":33,"buymax":0,"usintegral":0,"istock":10,"pno":"90005873","iwxts":"","baddress":"北京市西城区马连道路14号大德盛茶城1-","ibid":416,"icommentcnt":0,"iscore":0}]
     */

    private int Status;
    private List<MsgBean> Msg;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public List<MsgBean> getMsg() {
        return Msg;
    }

    public void setMsg(List<MsgBean> Msg) {
        this.Msg = Msg;
    }

    public static class MsgBean {
        /**
         * pid : 36412
         * pname : 普洱生茶 攸乐古茶 2007年生茶 古树纯料普洱茶 357克普洱茶 七子饼茶 传统名山普洱茶
         * picurl : http://img1.tealg.com/Product/Main/220X220/201412/bb6fe8b2-2bd4-4120-8a29-5b910521c1b5.jpg
         * shopprice : 26000.0
         * iviews : 2230
         * ibuys : 10
         * buymax : 0
         * usintegral : 7000
         * istock : 0
         * pno : 90005987
         * iwxts :
         * baddress : 北京市西城区马连道路14号大德盛茶城1-
         * ibid : 416
         * icommentcnt : 0
         * iscore : 0
         */

        private int pid;
        private String pname;
        private String picurl;
        private float shopprice;
        private int iviews;
        private int ibuys;
        private int buymax;
        private int usintegral;
        private int istock;
        private String pno;
        private String iwxts;
        private String baddress;
        private int ibid;
        private int icommentcnt;
        private int iscore;

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
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

        public float getShopprice() {
            return shopprice;
        }

        public void setShopprice(float shopprice) {
            this.shopprice = shopprice;
        }

        public int getIviews() {
            return iviews;
        }

        public void setIviews(int iviews) {
            this.iviews = iviews;
        }

        public int getIbuys() {
            return ibuys;
        }

        public void setIbuys(int ibuys) {
            this.ibuys = ibuys;
        }

        public int getBuymax() {
            return buymax;
        }

        public void setBuymax(int buymax) {
            this.buymax = buymax;
        }

        public int getUsintegral() {
            return usintegral;
        }

        public void setUsintegral(int usintegral) {
            this.usintegral = usintegral;
        }

        public int getIstock() {
            return istock;
        }

        public void setIstock(int istock) {
            this.istock = istock;
        }

        public String getPno() {
            return pno;
        }

        public void setPno(String pno) {
            this.pno = pno;
        }

        public String getIwxts() {
            return iwxts;
        }

        public void setIwxts(String iwxts) {
            this.iwxts = iwxts;
        }

        public String getBaddress() {
            return baddress;
        }

        public void setBaddress(String baddress) {
            this.baddress = baddress;
        }

        public int getIbid() {
            return ibid;
        }

        public void setIbid(int ibid) {
            this.ibid = ibid;
        }

        public int getIcommentcnt() {
            return icommentcnt;
        }

        public void setIcommentcnt(int icommentcnt) {
            this.icommentcnt = icommentcnt;
        }

        public int getIscore() {
            return iscore;
        }

        public void setIscore(int iscore) {
            this.iscore = iscore;
        }
    }
}
