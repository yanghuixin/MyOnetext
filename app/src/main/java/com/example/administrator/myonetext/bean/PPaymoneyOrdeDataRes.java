package com.example.administrator.myonetext.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */

public class PPaymoneyOrdeDataRes {

    /**
     * Status : 1
     * Msg : [{"orderId":31507,"orderNumber":"ZXB1801030001","orderPirce":420,"orderPayInt":0,"orderState":3,"orderPay":"http://app.tealg.com/wap/NewPayStap1.aspx?order_no=ZXB1801030001","orderReturn":"","ordersMsg":[{"ordersId":35556,"ordersNumber":"ZXS1801030001","ordersState":3,"ordersBusinessId":11363,"ordersBusinessName":"云南张顺翔茶叶","ordersWuLiu":"","orderReturnStatus":"","ordersProducts":[{"productId":43693,"detailsId":53363,"productComment":0,"productName":"贺开山古茶树 普洱生茶 黄片","productPic":"http://img1.tealg.com/Product/Main/220X220/201604/43d4212d-bf7b-47ff-817f-b897ba1642f0.jpg","productPrice":420,"productNumber":1,"ordersCommentAdd":""}]}]}]
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
         * orderId : 31507
         * orderNumber : ZXB1801030001
         * orderPirce : 420.0
         * orderPayInt : 0
         * orderState : 3
         * orderPay : http://app.tealg.com/wap/NewPayStap1.aspx?order_no=ZXB1801030001
         * orderReturn :
         * ordersMsg : [{"ordersId":35556,"ordersNumber":"ZXS1801030001","ordersState":3,"ordersBusinessId":11363,"ordersBusinessName":"云南张顺翔茶叶","ordersWuLiu":"","orderReturnStatus":"","ordersProducts":[{"productId":43693,"detailsId":53363,"productComment":0,"productName":"贺开山古茶树 普洱生茶 黄片","productPic":"http://img1.tealg.com/Product/Main/220X220/201604/43d4212d-bf7b-47ff-817f-b897ba1642f0.jpg","productPrice":420,"productNumber":1,"ordersCommentAdd":""}]}]
         */

        private int orderId;
        private String orderNumber;
        private double orderPirce;
        private int orderPayInt;
        private int orderState;
        private String orderPay;
        private String orderReturn;
        private List<OrdersMsgBean> ordersMsg;

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public double getOrderPirce() {
            return orderPirce;
        }

        public void setOrderPirce(double orderPirce) {
            this.orderPirce = orderPirce;
        }

        public int getOrderPayInt() {
            return orderPayInt;
        }

        public void setOrderPayInt(int orderPayInt) {
            this.orderPayInt = orderPayInt;
        }

        public int getOrderState() {
            return orderState;
        }

        public void setOrderState(int orderState) {
            this.orderState = orderState;
        }

        public String getOrderPay() {
            return orderPay;
        }

        public void setOrderPay(String orderPay) {
            this.orderPay = orderPay;
        }

        public String getOrderReturn() {
            return orderReturn;
        }

        public void setOrderReturn(String orderReturn) {
            this.orderReturn = orderReturn;
        }

        public List<OrdersMsgBean> getOrdersMsg() {
            return ordersMsg;
        }

        public void setOrdersMsg(List<OrdersMsgBean> ordersMsg) {
            this.ordersMsg = ordersMsg;
        }

        public static class OrdersMsgBean {
            /**
             * ordersId : 35556
             * ordersNumber : ZXS1801030001
             * ordersState : 3
             * ordersBusinessId : 11363
             * ordersBusinessName : 云南张顺翔茶叶
             * ordersWuLiu :
             * orderReturnStatus :
             * ordersProducts : [{"productId":43693,"detailsId":53363,"productComment":0,"productName":"贺开山古茶树 普洱生茶 黄片","productPic":"http://img1.tealg.com/Product/Main/220X220/201604/43d4212d-bf7b-47ff-817f-b897ba1642f0.jpg","productPrice":420,"productNumber":1,"ordersCommentAdd":""}]
             */

            private int ordersId;
            private String ordersNumber;
            private int ordersState;
            private int ordersBusinessId;
            private String ordersBusinessName;
            private String ordersWuLiu;
            private String orderReturnStatus;
            private List<OrdersProductsBean> ordersProducts;

            public int getOrdersId() {
                return ordersId;
            }

            public void setOrdersId(int ordersId) {
                this.ordersId = ordersId;
            }

            public String getOrdersNumber() {
                return ordersNumber;
            }

            public void setOrdersNumber(String ordersNumber) {
                this.ordersNumber = ordersNumber;
            }

            public int getOrdersState() {
                return ordersState;
            }

            public void setOrdersState(int ordersState) {
                this.ordersState = ordersState;
            }

            public int getOrdersBusinessId() {
                return ordersBusinessId;
            }

            public void setOrdersBusinessId(int ordersBusinessId) {
                this.ordersBusinessId = ordersBusinessId;
            }

            public String getOrdersBusinessName() {
                return ordersBusinessName;
            }

            public void setOrdersBusinessName(String ordersBusinessName) {
                this.ordersBusinessName = ordersBusinessName;
            }

            public String getOrdersWuLiu() {
                return ordersWuLiu;
            }

            public void setOrdersWuLiu(String ordersWuLiu) {
                this.ordersWuLiu = ordersWuLiu;
            }

            public String getOrderReturnStatus() {
                return orderReturnStatus;
            }

            public void setOrderReturnStatus(String orderReturnStatus) {
                this.orderReturnStatus = orderReturnStatus;
            }

            public List<OrdersProductsBean> getOrdersProducts() {
                return ordersProducts;
            }

            public void setOrdersProducts(List<OrdersProductsBean> ordersProducts) {
                this.ordersProducts = ordersProducts;
            }

            public static class OrdersProductsBean {
                /**
                 * productId : 43693
                 * detailsId : 53363
                 * productComment : 0
                 * productName : 贺开山古茶树 普洱生茶 黄片
                 * productPic : http://img1.tealg.com/Product/Main/220X220/201604/43d4212d-bf7b-47ff-817f-b897ba1642f0.jpg
                 * productPrice : 420.0
                 * productNumber : 1
                 * ordersCommentAdd :
                 */

                private int productId;
                private int detailsId;
                private int productComment;
                private String productName;
                private String productPic;
                private double productPrice;
                private int productNumber;
                private String ordersCommentAdd;

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public int getDetailsId() {
                    return detailsId;
                }

                public void setDetailsId(int detailsId) {
                    this.detailsId = detailsId;
                }

                public int getProductComment() {
                    return productComment;
                }

                public void setProductComment(int productComment) {
                    this.productComment = productComment;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getProductPic() {
                    return productPic;
                }

                public void setProductPic(String productPic) {
                    this.productPic = productPic;
                }

                public double getProductPrice() {
                    return productPrice;
                }

                public void setProductPrice(double productPrice) {
                    this.productPrice = productPrice;
                }

                public int getProductNumber() {
                    return productNumber;
                }

                public void setProductNumber(int productNumber) {
                    this.productNumber = productNumber;
                }

                public String getOrdersCommentAdd() {
                    return ordersCommentAdd;
                }

                public void setOrdersCommentAdd(String ordersCommentAdd) {
                    this.ordersCommentAdd = ordersCommentAdd;
                }
            }
        }
    }
}
