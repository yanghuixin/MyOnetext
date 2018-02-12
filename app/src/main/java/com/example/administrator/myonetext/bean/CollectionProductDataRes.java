package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/9.
 */

public class CollectionProductDataRes {


    /**
     * status : 1
     * message : [{"pid":36412,"picurl":"http://img1.tealg.com/Product/Main/220X220/201412/bb6fe8b2-2bd4-4120-8a29-5b910521c1b5.jpg","pname":"普洱生茶 攸乐古茶 2007年生茶 古树纯料普洱茶 357克普洱茶 七子饼茶 传统名山普洱茶","usintegral":7000,"iscore":3,"icommentcnt":0,"shopprice":"26000.00","baddress":"北京市西城区马连道路14号大德盛茶城1-","iwxts":""},{"pid":47162,"picurl":"http://img1.tealg.com/Product/Main/220X220/201608/cf635b1a-4d1d-4f47-837e-76786a3dba67.png","pname":"康师傅矿泉水550mlx24瓶","usintegral":12,"iscore":1,"icommentcnt":1,"shopprice":"13.00","baddress":"天津市","iwxts":""},{"pid":48626,"picurl":"http://img1.tealg.com/Product/Main/220X220/201609/2fbb854b-b52c-44d6-9f6b-5c28eefefd95.jpg","pname":"康师傅西红柿打卤面袋装101gx24袋 整箱","usintegral":48,"iscore":3,"icommentcnt":0,"shopprice":"53.00","baddress":"北京市西城区","iwxts":""},{"pid":48199,"picurl":"http://img1.tealg.com/Product/Main/220X220/201609/089f720b-3813-4f59-a241-9c0cef9a22d3.jpg","pname":"可口可乐500ml  1x24瓶","usintegral":47,"iscore":3,"icommentcnt":0,"shopprice":"49.00","baddress":"河南郑州","iwxts":""}]
     */

    private int status;
    private List<MessageBean> message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
        /**
         * pid : 36412
         * picurl : http://img1.tealg.com/Product/Main/220X220/201412/bb6fe8b2-2bd4-4120-8a29-5b910521c1b5.jpg
         * pname : 普洱生茶 攸乐古茶 2007年生茶 古树纯料普洱茶 357克普洱茶 七子饼茶 传统名山普洱茶
         * usintegral : 7000
         * iscore : 3
         * icommentcnt : 0
         * shopprice : 26000.00
         * baddress : 北京市西城区马连道路14号大德盛茶城1-
         * iwxts :
         */

        private int pid;
        private String picurl;
        private String pname;
        private int usintegral;
        private int iscore;
        private int icommentcnt;
        private String shopprice;
        private String baddress;
        private String iwxts;

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public int getUsintegral() {
            return usintegral;
        }

        public void setUsintegral(int usintegral) {
            this.usintegral = usintegral;
        }

        public int getIscore() {
            return iscore;
        }

        public void setIscore(int iscore) {
            this.iscore = iscore;
        }

        public int getIcommentcnt() {
            return icommentcnt;
        }

        public void setIcommentcnt(int icommentcnt) {
            this.icommentcnt = icommentcnt;
        }

        public String getShopprice() {
            return shopprice;
        }

        public void setShopprice(String shopprice) {
            this.shopprice = shopprice;
        }

        public String getBaddress() {
            return baddress;
        }

        public void setBaddress(String baddress) {
            this.baddress = baddress;
        }

        public String getIwxts() {
            return iwxts;
        }

        public void setIwxts(String iwxts) {
            this.iwxts = iwxts;
        }
    }
}
