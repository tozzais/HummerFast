package com.xianlv.business.bean;

import java.util.List;

public class GoodsOrderItem {

    public String orderId;
    public String orderNo;
    public String amountMoney;//商品总价
    public String money;//应付
    public String couponMoney;
    public String scoreMoney;

    public String createtime;
    public String statusName;
    public String payTypeDesc;
    public String userName;
    public String userPhone;
    public String descs;




    public List<Goods> orderDetailTitleList;
    public class Goods{
        public String img;
        public String productName;
        public String quantity;
        public String propertyName;


    }
}
