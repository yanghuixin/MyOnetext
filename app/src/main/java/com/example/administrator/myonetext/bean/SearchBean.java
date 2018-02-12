package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/9.
 */

public class SearchBean {
    /**
     * Status : 1
     * Msg :
     * sjmsg :
     * cpmsg : [{"pid":52599,"pname":"珍珠麻球（图片仅供参考，以实物为准）","picurl":"http://img1.tealg.com/Product/Main/220X220/201710/47107db5-b4c1-4161-b1f8-42e832bdf3c1.jpg","shopprice":13,"iviews":0,"ibuys":0,"buymax":0,"usintegral":0,"istock":10000,"pno":"MS000335","baddress":"北京市丰台区玉泉营112号果菜中心1区35号","ibid":11675,"icommentcnt":0,"iscore":0}]
     */

    private String Status;
    private String Msg;
    private String sjmsg;
    private List<CpmsgBean> cpmsg;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getSjmsg() {
        return sjmsg;
    }

    public void setSjmsg(String sjmsg) {
        this.sjmsg = sjmsg;
    }

    public List<CpmsgBean> getCpmsg() {
        return cpmsg;
    }

    public void setCpmsg(List<CpmsgBean> cpmsg) {
        this.cpmsg = cpmsg;
    }

    public static class CpmsgBean {
        /**
         * pid : 52599
         * pname : 珍珠麻球（图片仅供参考，以实物为准）
         * picurl : http://img1.tealg.com/Product/Main/220X220/201710/47107db5-b4c1-4161-b1f8-42e832bdf3c1.jpg
         * shopprice : 13.0
         * iviews : 0
         * ibuys : 0
         * buymax : 0
         * usintegral : 0
         * istock : 10000
         * pno : MS000335
         * baddress : 北京市丰台区玉泉营112号果菜中心1区35号
         * ibid : 11675
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
