package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/29.
 */

public class NearbyProductDataRes {

    /**
     * Status : 1
     * Msg : [{"pid":52599,"pname":"珍珠麻球（图片仅供参考，以实物为准）","picurl":"http://img1.tealg.com/Product/Main/220X220/201710/47107db5-b4c1-4161-b1f8-42e832bdf3c1.jpg","shopprice":13,"iviews":0,"ibuys":0,"buymax":0,"usintegral":0,"istock":10000,"pno":"MS000335","iwxts":"","baddress":"北京市丰台区玉泉营112号果菜中心1区35号","ibid":11675,"icommentcnt":0,"iscore":0},{"pid":52598,"pname":"手撕面筋","picurl":"http://img1.tealg.com/Product/Main/220X220/201710/a403f220-832e-4ce7-9202-ef38ca12148a.jpg","shopprice":75,"iviews":1,"ibuys":0,"buymax":0,"usintegral":0,"istock":10000,"pno":"MS000334","iwxts":"","baddress":"北京市丰台区玉泉营112号果菜中心1区35号","ibid":11675,"icommentcnt":0,"iscore":0},{"pid":52597,"pname":"鲜牛肝菌（图片仅供参考，以实物为准）","picurl":"http://img1.tealg.com/Product/Main/220X220/201710/d0938540-8481-41be-bc1f-5c0f7bbeb547.jpg","shopprice":22,"iviews":0,"ibuys":0,"buymax":0,"usintegral":0,"istock":10000,"pno":"MS000333","iwxts":"","baddress":"北京市丰台区玉泉营112号果菜中心1区35号","ibid":11675,"icommentcnt":0,"iscore":0},{"pid":52596,"pname":"国宴小油条（图片仅供参考，以实物为准）","picurl":"http://img1.tealg.com/Product/Main/220X220/201710/9158f22e-7563-41b1-84ca-91b59e5ccbfc.jpg","shopprice":17,"iviews":0,"ibuys":0,"buymax":0,"usintegral":0,"istock":10000,"pno":"MS000332","iwxts":"","baddress":"北京市丰台区玉泉营112号果菜中心1区35号","ibid":11675,"icommentcnt":0,"iscore":0}]
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
         * iwxts :
         * baddress : 北京市丰台区玉泉营112号果菜中心1区35号
         * ibid : 11675
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
