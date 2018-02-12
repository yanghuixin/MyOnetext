package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/7.
 */

public class MyWalletDataRes {


    /**
     * status : 1
     * message : [{"bankname":"广发银行股份有限公司","cardtype":"信用卡","cardno":"3228","cardid":"B0A9701F97485C9EBCEF2EAA23EC0728"}]
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
         * bankname : 广发银行股份有限公司
         * cardtype : 信用卡
         * cardno : 3228
         * cardid : B0A9701F97485C9EBCEF2EAA23EC0728
         */

        private String bankname;
        private String cardtype;
        private String cardno;
        private String cardid;

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }

        public String getCardtype() {
            return cardtype;
        }

        public void setCardtype(String cardtype) {
            this.cardtype = cardtype;
        }

        public String getCardno() {
            return cardno;
        }

        public void setCardno(String cardno) {
            this.cardno = cardno;
        }

        public String getCardid() {
            return cardid;
        }

        public void setCardid(String cardid) {
            this.cardid = cardid;
        }
    }
}
