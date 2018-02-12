package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */
//stor
public class OneDataRes {

    /**
     * Status : 1
     * Msg : [{"bid":11808,"bname":"钦州海鸭蛋","blogopic":"http://img1.tealg.com/Brand/201801/9474cfdb-f002-437f-b505-2372f649c581.jpg","pingfen":10,"plcnt":1,"baddress":" 广西省钦州市北部湾","bmap":"109.096258,21.489808","bts":"海鸭蛋"},{"bid":416,"bname":"大德盛","blogopic":"http://img1.tealg.com/Brand/201510/6ef3e825-2fad-45fc-a7d3-1f065871fa2a.jpg","pingfen":9.71,"plcnt":9,"baddress":"北京市西城区马连道路14号大德盛茶城1-","bmap":"116.333478,39.8859","bts":"普洱茶"},{"bid":11609,"bname":"源味祥北京","blogopic":"http://img1.tealg.com/Brand/201801/93c94d57-baa2-4465-b949-2f31401b7823.jpg","pingfen":10,"plcnt":0,"baddress":"同上","bmap":"116.333487,39.886117","bts":"普洱茶"},{"bid":11802,"bname":"海南水果","blogopic":"http://img1.tealg.com/Brand/201801/9d58c65a-f557-46ed-b137-bd7572c164be.jpg","pingfen":10,"plcnt":0,"baddress":"河南省南阳市","bmap":"116.059216,40.372698","bts":"火龙果    百香果"}]
     */

    private String  Status;
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
         * bid : 11808
         * bname : 钦州海鸭蛋
         * blogopic : http://img1.tealg.com/Brand/201801/9474cfdb-f002-437f-b505-2372f649c581.jpg
         * pingfen : 10
         * plcnt : 1
         * baddress :  广西省钦州市北部湾
         * bmap : 109.096258,21.489808
         * bts : 海鸭蛋
         */

        private int bid;
        private String bname;
        private String blogopic;
        private double pingfen;
        private int plcnt;
        private String baddress;
        private String bmap;
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

        public double getPingfen() {
            return pingfen;
        }

        public void setPingfen(double pingfen) {
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

        public String getBts() {
            return bts;
        }

        public void setBts(String bts) {
            this.bts = bts;
        }
    }
}
