package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/8.
 */

public class AdressDataRes {

    /**
     * status : 1
     * msg : [{"aid":13755,"isdfadd":1,"aname":"韦祖泽","aphone":"13521319845","address":"北京市 市辖区 丰台区国投财富广场4号楼1506室"}]
     */

    private int status;
    private List<MsgBean> msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * aid : 13755
         * isdfadd : 1
         * aname : 韦祖泽
         * aphone : 13521319845
         * address : 北京市 市辖区 丰台区国投财富广场4号楼1506室
         */

        private int aid;
        private int isdfadd;
        private String aname;
        private String aphone;
        private String address;

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public int getIsdfadd() {
            return isdfadd;
        }

        public void setIsdfadd(int isdfadd) {
            this.isdfadd = isdfadd;
        }

        public String getAname() {
            return aname;
        }

        public void setAname(String aname) {
            this.aname = aname;
        }

        public String getAphone() {
            return aphone;
        }

        public void setAphone(String aphone) {
            this.aphone = aphone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
