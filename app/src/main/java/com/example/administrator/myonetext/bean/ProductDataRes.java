package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ProductDataRes {

    /**
     * Status : 1
     * Msg : [{"pid":51452,"pname":"黄片落水洞","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/8fb9afd5-b712-44c7-816e-5aa524ae6077.jpg","shopprice":1600,"iviews":18,"ibuys":0,"buymax":0,"usintegral":700,"istock":7,"pno":"90029771","ibid":416,"icommentcnt":0,"iscore":0},{"pid":51451,"pname":"冰岛之味2014年   大德盛款 文道云内飞","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/4761a7eb-bcd1-4917-a287-a22ec47428dd.jpg","shopprice":180,"iviews":24,"ibuys":0,"buymax":0,"usintegral":10,"istock":5,"pno":"90029770","ibid":416,"icommentcnt":0,"iscore":0},{"pid":51444,"pname":"中茶贡饼500g一盒（2011款5饼装）熟茶100g/饼","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/9126a881-003e-44be-9ea4-b23a1413024e.jpg","shopprice":200,"iviews":32,"ibuys":0,"buymax":0,"usintegral":10,"istock":17,"pno":"90029765","ibid":416,"icommentcnt":0,"iscore":0},{"pid":43668,"pname":"赛冰岛普洱茶 特价包邮 7饼一提 4提28饼 纯料古树春茶值得收藏有升值空间（20%每年）","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/09777c25-c27a-46d1-9579-e4e2e341ff3a.jpg","shopprice":8000,"iviews":1402,"ibuys":38,"buymax":0,"usintegral":1600,"istock":463,"pno":"90029621","ibid":416,"icommentcnt":0,"iscore":0}]
     */

    private String Status;
    private List<MsgBean> Msg;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
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
         * pid : 51452
         * pname : 黄片落水洞
         * picurl : http://img1.tealg.com/Product/Main/220X220/201707/8fb9afd5-b712-44c7-816e-5aa524ae6077.jpg
         * shopprice : 1600.0
         * iviews : 18
         * ibuys : 0
         * buymax : 0
         * usintegral : 700
         * istock : 7
         * pno : 90029771
         * ibid : 416
         * icommentcnt : 0
         * iscore : 0
         */

        private int pid;
        private String pname;
        private String picurl;
        private double shopprice;
        private int iviews;
        private int ibuys;
        private int buymax;
        private int usintegral;
        private int istock;
        private String pno;
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

        public double getShopprice() {
            return shopprice;
        }

        public void setShopprice(double shopprice) {
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
