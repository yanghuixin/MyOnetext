package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/1.
 */

public class SearchSPBean {

    /**
     * Status : 1
     * Msg :
     * sjmsg : [{"bid":416,"bname":"大德盛普洱茶官方旗舰店","blogopic":"http://img1.tealg.com/Brand/201510/6ef3e825-2fad-45fc-a7d3-1f065871fa2a.jpg","pingfen":9.71,"plcnt":9,"baddress":"北京市西城区马连道路14号大德盛茶城1-","bmap":"116.333478,39.8859","bts":"普洱茶"},{"bid":913,"bname":"茶语清心店","blogopic":"http://app.tealg.com/wap/resource/images/--2_03.png","pingfen":10,"plcnt":2,"baddress":"马连道路大德盛茶城二楼","bmap":"116.333554,39.886096","bts":"普洱茶"},{"bid":11653,"bname":"大德盛名烟名酒旗舰店","blogopic":"http://img1.tealg.com/Brand/201801/f32db3a6-2a97-4475-b12d-1ae87fc00e54.jpg","pingfen":10,"plcnt":0,"baddress":"马连道路14号","bmap":"116.333379,39.886885","bts":"烟酒"}]
     * cpmsg : [{"pid":51451,"pname":"普洱茶 冰岛之味2014年大德盛款 文道云内飞 纯料乔木老树茶饼 春茶 357g七子饼茶 冰岛原料","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/4761a7eb-bcd1-4917-a287-a22ec47428dd.jpg","shopprice":180,"iviews":50,"ibuys":0,"buymax":0,"usintegral":77,"istock":5,"pno":"90029770","baddress":"北京市西城区马连道路14号大德盛茶城1-","ibid":416,"icommentcnt":0,"iscore":0},{"pid":51441,"pname":"普洱生茶 大德盛云南勐海普洱茶砖南糯山古树、春茶、2003年纯料片末砖","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/f1e479ef-d6f9-420e-a6a2-adac437e5895.jpg","shopprice":300,"iviews":39,"ibuys":0,"buymax":0,"usintegral":116,"istock":268,"pno":"90029762","baddress":"北京市西城区马连道路14号大德盛茶城1-","ibid":416,"icommentcnt":0,"iscore":0},{"pid":51440,"pname":"普洱生茶砖 冰岛古树、茶片（大德盛2014款）春茶纯料片茶砖、每年增值30%","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/c772cc1e-3594-444e-bd9f-b1e8090bb131.jpg","shopprice":380,"iviews":55,"ibuys":3,"buymax":0,"usintegral":95,"istock":115,"pno":"90029761","baddress":"北京市西城区马连道路14号大德盛茶城1-","ibid":416,"icommentcnt":0,"iscore":0},{"pid":51439,"pname":"普洱生茶 易武正山普洱生茶（大德盛2009年款）古树纯料、春茶、普洱茶","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/0ea5ffe1-e39c-4ffd-ad74-f6e975cdbe21.jpg","shopprice":680,"iviews":291,"ibuys":0,"buymax":0,"usintegral":192,"istock":94,"pno":"90029760","baddress":"北京市西城区马连道路14号大德盛茶城1-","ibid":416,"icommentcnt":0,"iscore":0}]
     */

    private String Status;
    private String Msg;
    private List<SjmsgBean> sjmsg;
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

    public List<SjmsgBean> getSjmsg() {
        return sjmsg;
    }

    public void setSjmsg(List<SjmsgBean> sjmsg) {
        this.sjmsg = sjmsg;
    }

    public List<CpmsgBean> getCpmsg() {
        return cpmsg;
    }

    public void setCpmsg(List<CpmsgBean> cpmsg) {
        this.cpmsg = cpmsg;
    }

    public static class SjmsgBean {
        /**
         * bid : 416
         * bname : 大德盛普洱茶官方旗舰店
         * blogopic : http://img1.tealg.com/Brand/201510/6ef3e825-2fad-45fc-a7d3-1f065871fa2a.jpg
         * pingfen : 9.71
         * plcnt : 9
         * baddress : 北京市西城区马连道路14号大德盛茶城1-
         * bmap : 116.333478,39.8859
         * bts : 普洱茶
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

    public static class CpmsgBean {
        /**
         * pid : 51451
         * pname : 普洱茶 冰岛之味2014年大德盛款 文道云内飞 纯料乔木老树茶饼 春茶 357g七子饼茶 冰岛原料
         * picurl : http://img1.tealg.com/Product/Main/220X220/201707/4761a7eb-bcd1-4917-a287-a22ec47428dd.jpg
         * shopprice : 180.0
         * iviews : 50
         * ibuys : 0
         * buymax : 0
         * usintegral : 77
         * istock : 5
         * pno : 90029770
         * baddress : 北京市西城区马连道路14号大德盛茶城1-
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
