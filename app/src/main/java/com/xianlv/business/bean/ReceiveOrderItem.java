package com.xianlv.business.bean;

/**
 * 收款订单
 */
public class ReceiveOrderItem {

    public String scanId;
    public String orderNo;
    public String refundMoney;
    public String money;
    public String purposeStatus;
    public String nickname;
    public String qrcodeName;
    public String operuser;
    public String verPhone;
    public String payTime;
    public String createtime;
    public String remark;
    public String status;
    public String checktime;
    public String operuserRefund;
    public String checkInfo;
    public String reason;
    public String statusRefundParam;

    public String getReturnStatus(){
        return  statusRefundParam;
//        switch (status){
//            case "1":
//                return "待付款";
//            case "2":
//                return "已付款";
//            case "3":
//                return "退款中";
//            case "4":
//                return "已退款";
//        }
//        return "";
    }

}
