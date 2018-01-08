package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */
//今日秒杀
public class TodayDataRes {


    /**
     * Status : 1
     * Msg : [{"pid":49365,"pname":"(内部测试,不发货)统一小茗同学冷泡茶茉莉萃茶480mlx15瓶整箱装","pPromotionName":"(内部测试,不发货)统一小茗同学冷泡茶茉莉萃茶480mlx15瓶整箱装","pPromotionTime":"2017/12/31 23:59:59","picurl":"http://img1.tealg.com/Product/Main/220X220/201610/d77e1f1b-e0d3-4270-9b23-7aea7c1ecb99.jpg","shopprice":60,"PromotionPrice":60,"iviews":349,"ibuys":0,"buymax":0,"usintegral":60,"istock":500,"pno":"JS000511","ibid":11480,"icommentcnt":0,"iscore":0},{"pid":49198,"pname":"(内部测试,不发货)紫林白醋500mlx12整箱装","pPromotionName":"(内部测试,不发货)紫林白醋500mlx12整箱装","pPromotionTime":"2017/12/31 23:59:59","picurl":"http://img1.tealg.com/Product/Main/220X220/201610/a0af00fe-dca2-4695-87e3-ff26ea9b0ada.jpg","shopprice":43,"PromotionPrice":43,"iviews":428,"ibuys":0,"buymax":0,"usintegral":43,"istock":500,"pno":"SP002350","ibid":11526,"icommentcnt":0,"iscore":0},{"pid":49232,"pname":"(内部测试,不发货)海天味极鲜380gx12整箱装","pPromotionName":"(内部测试,不发货)海天味极鲜380gx12整箱装","pPromotionTime":"2017/12/31 23:59:59","picurl":"http://img1.tealg.com/Product/Main/220X220/201610/ee40f873-af9c-4e55-8f6d-c35fad5738cb.png","shopprice":68,"PromotionPrice":68,"iviews":59,"ibuys":0,"buymax":0,"usintegral":68,"istock":500,"pno":"SP002384","ibid":11526,"icommentcnt":0,"iscore":0},{"pid":49538,"pname":"(内部测试,不发货)1.25L椰子汁 1x6瓶","pPromotionName":"(内部测试,不发货)1.25L椰子汁 1x6瓶","pPromotionTime":"2017/12/31 23:59:59","picurl":"http://img1.tealg.com/Product/Main/220X220/201611/87ba1c67-6a97-4ffb-b2cc-6942e0df16e9.jpg","shopprice":63,"PromotionPrice":63,"iviews":507,"ibuys":1,"buymax":0,"usintegral":63,"istock":100,"pno":"JS000550","ibid":11531,"icommentcnt":0,"iscore":0},{"pid":49537,"pname":"500ml椰子汁(内部测试,不发货)","pPromotionName":"500ml椰子汁(内部测试,不发货)","pPromotionTime":"2017/12/31 23:59:59","picurl":"http://img1.tealg.com/Product/Main/220X220/201611/d7a7615e-f4b7-484b-8b76-722fb3f3f265.jpg","shopprice":60,"PromotionPrice":60,"iviews":455,"ibuys":1,"buymax":0,"usintegral":60,"istock":100,"pno":"JS000549","ibid":11531,"icommentcnt":0,"iscore":0},{"pid":49536,"pname":"(内部测试,不发货)310ml椰子汁1x24","pPromotionName":"(内部测试,不发货)310ml椰子汁1x24","pPromotionTime":"2017/12/31 23:59:59","picurl":"http://img1.tealg.com/Product/Main/220X220/201611/fa3a3347-8bb7-479f-b8a4-49a0b3af8f5d.jpg","shopprice":72,"PromotionPrice":72,"iviews":488,"ibuys":0,"buymax":0,"usintegral":72,"istock":100,"pno":"JS000548","ibid":11531,"icommentcnt":0,"iscore":0}]
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
         * pid : 49365
         * pname : (内部测试,不发货)统一小茗同学冷泡茶茉莉萃茶480mlx15瓶整箱装
         * pPromotionName : (内部测试,不发货)统一小茗同学冷泡茶茉莉萃茶480mlx15瓶整箱装
         * pPromotionTime : 2017/12/31 23:59:59
         * picurl : http://img1.tealg.com/Product/Main/220X220/201610/d77e1f1b-e0d3-4270-9b23-7aea7c1ecb99.jpg
         * shopprice : 60.0
         * PromotionPrice : 60
         * iviews : 349
         * ibuys : 0
         * buymax : 0
         * usintegral : 60
         * istock : 500
         * pno : JS000511
         * ibid : 11480
         * icommentcnt : 0
         * iscore : 0
         */

        private int pid;
        private String pname;
        private String pPromotionName;
        private String pPromotionTime;
        private String picurl;
        private double shopprice;
        private int PromotionPrice;
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

        public String getPPromotionName() {
            return pPromotionName;
        }

        public void setPPromotionName(String pPromotionName) {
            this.pPromotionName = pPromotionName;
        }

        public String getPPromotionTime() {
            return pPromotionTime;
        }

        public void setPPromotionTime(String pPromotionTime) {
            this.pPromotionTime = pPromotionTime;
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

        public int getPromotionPrice() {
            return PromotionPrice;
        }

        public void setPromotionPrice(int PromotionPrice) {
            this.PromotionPrice = PromotionPrice;
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
