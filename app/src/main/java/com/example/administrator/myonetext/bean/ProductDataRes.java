package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/20.
 */

public class ProductDataRes {
    /**
     * Status : 1
     * Msg : [{"pid":36401,"pname":"普洱生茶 古树茶 2014年普洱茶 357g生茶 七子饼茶 云南普洱茶 冰岛古树茶","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/bc2231e8-7bcf-4718-a234-26891e954ef8.jpg","shopprice":1200,"iviews":2961,"ibuys":14,"buymax":0,"usintegral":188,"istock":1403,"pno":"90005607","iwxts":",,","baddress":"北京市西城区马连道路14号大德盛茶城1-","ibid":416,"icommentcnt":0,"iscore":0},{"pid":51410,"pname":"普洱茶 源味祥普洱茶 烟条熟茶 YWX2013年熟茶-YWX","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/00239336-e063-457d-9a8f-037c212cf235.jpg","shopprice":260,"iviews":162,"ibuys":0,"buymax":0,"usintegral":80,"istock":860,"pno":"90029738","iwxts":"","baddress":"同上","ibid":11609,"icommentcnt":0,"iscore":0},{"pid":52932,"pname":"海鸭蛋 3000g小号 开袋即食 广西钦州 蛋黄晶红、立体感强（每盒50枚每枚60g）","picurl":"http://img1.tealg.com/Product/Main/220X220/201801/73ad1609-3fe6-4926-aa4f-9b63b908934d.jpg","shopprice":88,"iviews":166,"ibuys":7,"buymax":0,"usintegral":8,"istock":998,"pno":"TC000032","iwxts":"全国包邮，除偏远5省外","baddress":" 广西省钦州市北部湾","ibid":11808,"icommentcnt":0,"iscore":0},{"pid":51028,"pname":"康师茉莉清茶1Lx8","picurl":"http://img1.tealg.com/Product/Main/220X220/201705/acea25e0-2ed7-47bf-8718-80799445bcc5.jpg","shopprice":26,"iviews":93,"ibuys":0,"buymax":0,"usintegral":26,"istock":50000,"pno":"JS001335","iwxts":"","baddress":"河北省石家庄市长安区南三条建国食品城一层D区152号","ibid":11590,"icommentcnt":0,"iscore":0},{"pid":52677,"pname":"风骚泥鳅 （图片仅供参考，以实物为准）","picurl":"http://img1.tealg.com/Product/Main/220X220/201711/77795177-2aec-4a68-99af-a056da726bb5.jpg","shopprice":28,"iviews":62,"ibuys":0,"buymax":0,"usintegral":2,"istock":10000,"pno":"MS000413","iwxts":"","baddress":"北京市西城区红莲北里20号楼","ibid":11679,"icommentcnt":0,"iscore":0},{"pid":52872,"pname":"柿饼 1000g特级礼盒装 正宗陕西富平特产 明秀堡牌（约20-25枚）","picurl":"http://img1.tealg.com/Product/Main/220X220/201801/9b2dfbdb-7cf2-4f67-b727-9810f82cb08f.jpg","shopprice":68,"iviews":114,"ibuys":1,"buymax":0,"usintegral":8,"istock":5000,"pno":"TC000005","iwxts":"北京另加3元邮费","baddress":"陕西省渭南市富平县","ibid":11792,"icommentcnt":0,"iscore":0}]
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
         * pid : 36401
         * pname : 普洱生茶 古树茶 2014年普洱茶 357g生茶 七子饼茶 云南普洱茶 冰岛古树茶
         * picurl : http://img1.tealg.com/Product/Main/220X220/201707/bc2231e8-7bcf-4718-a234-26891e954ef8.jpg
         * shopprice : 1200.0
         * iviews : 2961
         * ibuys : 14
         * buymax : 0
         * usintegral : 188
         * istock : 1403
         * pno : 90005607
         * iwxts : ,,
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


//    /**
//     * Status : 1
//     * Msg : [{"pid":51452,"pname":"黄片落水洞","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/8fb9afd5-b712-44c7-816e-5aa524ae6077.jpg","shopprice":1600,"iviews":18,"ibuys":0,"buymax":0,"usintegral":700,"istock":7,"pno":"90029771","ibid":416,"icommentcnt":0,"iscore":0},{"pid":51451,"pname":"冰岛之味2014年   大德盛款 文道云内飞","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/4761a7eb-bcd1-4917-a287-a22ec47428dd.jpg","shopprice":180,"iviews":24,"ibuys":0,"buymax":0,"usintegral":10,"istock":5,"pno":"90029770","ibid":416,"icommentcnt":0,"iscore":0},{"pid":51444,"pname":"中茶贡饼500g一盒（2011款5饼装）熟茶100g/饼","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/9126a881-003e-44be-9ea4-b23a1413024e.jpg","shopprice":200,"iviews":32,"ibuys":0,"buymax":0,"usintegral":10,"istock":17,"pno":"90029765","ibid":416,"icommentcnt":0,"iscore":0},{"pid":43668,"pname":"赛冰岛普洱茶 特价包邮 7饼一提 4提28饼 纯料古树春茶值得收藏有升值空间（20%每年）","picurl":"http://img1.tealg.com/Product/Main/220X220/201707/09777c25-c27a-46d1-9579-e4e2e341ff3a.jpg","shopprice":8000,"iviews":1402,"ibuys":38,"buymax":0,"usintegral":1600,"istock":463,"pno":"90029621","ibid":416,"icommentcnt":0,"iscore":0}]
//     */
//
//    private String Status;
//    private List<MsgBean> Msg;
//
//    public String getStatus() {
//        return Status;
//    }
//
//    public void setStatus(String Status) {
//        this.Status = Status;
//    }
//
//    public List<MsgBean> getMsg() {
//        return Msg;
//    }
//
//    public void setMsg(List<MsgBean> Msg) {
//        this.Msg = Msg;
//    }
//
//    public static class MsgBean {
//        /**
//         * pid : 51452
//         * pname : 黄片落水洞
//         * picurl : http://img1.tealg.com/Product/Main/220X220/201707/8fb9afd5-b712-44c7-816e-5aa524ae6077.jpg
//         * shopprice : 1600.0
//         * iviews : 18
//         * ibuys : 0
//         * buymax : 0
//         * usintegral : 700
//         * istock : 7
//         * pno : 90029771
//         * ibid : 416
//         * icommentcnt : 0
//         * iscore : 0
//         */
//
//        private int pid;
//        private String pname;
//        private String picurl;
//        private double shopprice;
//        private int iviews;
//        private int ibuys;
//        private int buymax;
//        private int usintegral;
//        private int istock;
//        private String pno;
//        private int ibid;
//        private int icommentcnt;
//        private int iscore;
//
//        public int getPid() {
//            return pid;
//        }
//
//        public void setPid(int pid) {
//            this.pid = pid;
//        }
//
//        public String getPname() {
//            return pname;
//        }
//
//        public void setPname(String pname) {
//            this.pname = pname;
//        }
//
//        public String getPicurl() {
//            return picurl;
//        }
//
//        public void setPicurl(String picurl) {
//            this.picurl = picurl;
//        }
//
//        public double getShopprice() {
//            return shopprice;
//        }
//
//        public void setShopprice(double shopprice) {
//            this.shopprice = shopprice;
//        }
//
//        public int getIviews() {
//            return iviews;
//        }
//
//        public void setIviews(int iviews) {
//            this.iviews = iviews;
//        }
//
//        public int getIbuys() {
//            return ibuys;
//        }
//
//        public void setIbuys(int ibuys) {
//            this.ibuys = ibuys;
//        }
//
//        public int getBuymax() {
//            return buymax;
//        }
//
//        public void setBuymax(int buymax) {
//            this.buymax = buymax;
//        }
//
//        public int getUsintegral() {
//            return usintegral;
//        }
//
//        public void setUsintegral(int usintegral) {
//            this.usintegral = usintegral;
//        }
//
//        public int getIstock() {
//            return istock;
//        }
//
//        public void setIstock(int istock) {
//            this.istock = istock;
//        }
//
//        public String getPno() {
//            return pno;
//        }
//
//        public void setPno(String pno) {
//            this.pno = pno;
//        }
//
//        public int getIbid() {
//            return ibid;
//        }
//
//        public void setIbid(int ibid) {
//            this.ibid = ibid;
//        }
//
//        public int getIcommentcnt() {
//            return icommentcnt;
//        }
//
//        public void setIcommentcnt(int icommentcnt) {
//            this.icommentcnt = icommentcnt;
//        }
//
//        public int getIscore() {
//            return iscore;
//        }
//
//        public void setIscore(int iscore) {
//            this.iscore = iscore;
//        }
//    }
}
