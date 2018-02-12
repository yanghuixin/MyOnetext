package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/9.
 */

public class CollectionStoreDataRes {

    /**
     * status : 1
     * message : [{"bid":11569,"blogopic":"http://img1.tealg.com/Brand/201801/9c352dba-54ac-4d28-b302-0f9925cf0c5e.jpg","bname":"隆昌兴盛旗舰店","pingfen":5,"plcnt":0,"baddress":"","bts":""},{"bid":416,"blogopic":"http://img1.tealg.com/Brand/201510/6ef3e825-2fad-45fc-a7d3-1f065871fa2a.jpg","bname":"大德盛","pingfen":3,"plcnt":9,"baddress":"北京市西城区马连道路14号大德盛茶城1-","bts":"普洱茶"},{"bid":913,"blogopic":"http://img1.tealg.com/Brand/201802/638124a4-f048-4a4a-9342-88d1cb195840.jpg","bname":"茶语清心","pingfen":5,"plcnt":2,"baddress":"马连道路大德盛茶城二楼","bts":"普洱茶"},{"bid":8993,"blogopic":"http://img1.tealg.com/Brand/201802/0103df2c-58a2-4afd-a556-0151b0befd1f.jpg","bname":"茶禅一味","pingfen":5,"plcnt":1,"baddress":"北京市房山区良乡地区北潞华家园19号-5底商","bts":"礼品茶"}]
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
         * bid : 11569
         * blogopic : http://img1.tealg.com/Brand/201801/9c352dba-54ac-4d28-b302-0f9925cf0c5e.jpg
         * bname : 隆昌兴盛旗舰店
         * pingfen : 5
         * plcnt : 0
         * baddress :
         * bts :
         */

        private int bid;
        private String blogopic;
        private String bname;
        private int pingfen;
        private int plcnt;
        private String baddress;
        private String bts;

        public int getBid() {
            return bid;
        }

        public void setBid(int bid) {
            this.bid = bid;
        }

        public String getBlogopic() {
            return blogopic;
        }

        public void setBlogopic(String blogopic) {
            this.blogopic = blogopic;
        }

        public String getBname() {
            return bname;
        }

        public void setBname(String bname) {
            this.bname = bname;
        }

        public int getPingfen() {
            return pingfen;
        }

        public void setPingfen(int pingfen) {
            this.pingfen = pingfen;
        }

        public int getPlcnt() {
            return plcnt;
        }

        public void setPlcnt(int plcnt) {
            this.plcnt = plcnt;
        }

        public String getBaddress() {
            return baddress;
        }

        public void setBaddress(String baddress) {
            this.baddress = baddress;
        }

        public String getBts() {
            return bts;
        }

        public void setBts(String bts) {
            this.bts = bts;
        }
    }
}
