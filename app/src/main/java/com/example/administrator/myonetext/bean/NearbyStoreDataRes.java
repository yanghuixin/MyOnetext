package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 */

public class NearbyStoreDataRes {


    /**
     * Status : 1
     * Msg : [{"bid":11675,"bname":"金腾玉硕","blogopic":"http://app.tealg.com/wap/resource/images/--2_03.png","pingfen":10,"plcnt":0,"baddress":"北京市丰台区玉泉营112号果菜中心1区35号","bmap":"116.341288,39.851045","bjuli":"4.8307","bts":"美食"},{"bid":11680,"bname":"龙慧喜来","blogopic":"http://app.tealg.com/wap/resource/images/--2_03.png","pingfen":10,"plcnt":0,"baddress":"涞水县东大街145号","bmap":"115.724091,39.391419","bjuli":"75.7898","bts":"餐饮"}]
     */

    private String Status;
    private List<MsgBean> Msg;
    private String rsKey;

    public String getRsKey() {
        return rsKey;
    }

    public void setRsKey(String rsKey) {
        this.rsKey = rsKey;
    }

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
         * bid : 11675
         * bname : 金腾玉硕
         * blogopic : http://app.tealg.com/wap/resource/images/--2_03.png
         * pingfen : 10
         * plcnt : 0
         * baddress : 北京市丰台区玉泉营112号果菜中心1区35号
         * bmap : 116.341288,39.851045
         * bjuli : 4.8307
         * bts : 美食
         */

        private int bid;
        private String bname;
        private String blogopic;
        private int pingfen;
        private int plcnt;
        private String baddress;
        private String bmap;
        private String bjuli;
        private String bts;

        public int getBid() {
            return bid;
        }

        public void setBid(int bid) {
            this.bid = bid;
        }

        public String getBname() {
            return bname;
        }

        public void setBname(String bname) {
            this.bname = bname;
        }

        public String getBlogopic() {
            return blogopic;
        }

        public void setBlogopic(String blogopic) {
            this.blogopic = blogopic;
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

        public String getBmap() {
            return bmap;
        }

        public void setBmap(String bmap) {
            this.bmap = bmap;
        }

        public String getBjuli() {
            return bjuli;
        }

        public void setBjuli(String bjuli) {
            this.bjuli = bjuli;
        }

        public String getBts() {
            return bts;
        }

        public void setBts(String bts) {
            this.bts = bts;
        }
    }
}
