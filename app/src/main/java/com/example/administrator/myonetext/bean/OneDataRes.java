package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */
//stor
public class OneDataRes {


    /**
     * Status : 1
     * Msg : [{"bid":1044,"bname":"宏辉茶业","blogopic":"http://img1.tealg.com/Brand/201509/6ab77d1d-23de-43a3-9d1e-e03365098363.jpg","pingfen":10,"plcnt":57,"baddress":"北京西城区马连道14号京华茶叶大世界2楼2-48号","bmap":"100.199556,25.91351"},{"bid":609,"bname":"京华","blogopic":"http://img1.tealg.com/Brand/201510/f67276ad-e236-4776-ae87-f09b57c091d5.jpg","pingfen":10,"plcnt":0,"baddress":"北京市西城区广安门外马连道14号","bmap":"116.333425,39.886665"},{"bid":1045,"bname":"老北京茶园","blogopic":"http://img1.tealg.com/Brand/201509/72e97818-b9db-41b4-8e93-0b9b3fe16f47.jpg","pingfen":10,"plcnt":55,"baddress":"北京市西城区马连道路12号（北京青溪茶叶市场中心B5号）","bmap":"100.259325,25.610889"},{"bid":604,"bname":"中茶世界茶园","blogopic":"http://img1.tealg.com/Brand/201501/f4046cda-30fd-4168-b6ee-afbe303136c7.jpg","pingfen":10,"plcnt":0,"baddress":"朝阳区望京六佰本商业街北区A13a","bmap":"116.473251,40.01515"}]
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
         * bid : 1044
         * bname : 宏辉茶业
         * blogopic : http://img1.tealg.com/Brand/201509/6ab77d1d-23de-43a3-9d1e-e03365098363.jpg
         * pingfen : 10
         * plcnt : 57
         * baddress : 北京西城区马连道14号京华茶叶大世界2楼2-48号
         * bmap : 100.199556,25.91351
         */

        private int bid;
        private String bname;
        private String blogopic;
        private int pingfen;
        private int plcnt;
        private String baddress;
        private String bmap;

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
    }
}
