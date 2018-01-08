package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */

public class BannerDataRes {

    /**
     * Status : 1
     * Msg : [{"aid":1165,"aname":"","alinkurl":"http://app.tealg.com/wap/Show-36404-0.html","apicurl":"http://img1.tealg.com/Adv/201711/5645456b-b114-4fb0-b245-7f641c6bbacf.jpg"},{"aid":1166,"aname":"","alinkurl":"http://app.tealg.com/wap/Show-36399-0.html","apicurl":"http://img1.tealg.com/Adv/201711/1e1ef0ff-2444-4c81-a494-2374c2b04fb8.jpg"}]
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
         * aid : 1165
         * aname :
         * alinkurl : http://app.tealg.com/wap/Show-36404-0.html
         * apicurl : http://img1.tealg.com/Adv/201711/5645456b-b114-4fb0-b245-7f641c6bbacf.jpg
         */

        private int aid;
        private String aname;
        private String alinkurl;
        private String apicurl;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public String getAname() {
            return aname;
        }

        public void setAname(String aname) {
            this.aname = aname;
        }

        public String getAlinkurl() {
            return alinkurl;
        }

        public void setAlinkurl(String alinkurl) {
            this.alinkurl = alinkurl;
        }

        public String getApicurl() {
            return apicurl;
        }

        public void setApicurl(String apicurl) {
            this.apicurl = apicurl;
        }
    }
}
