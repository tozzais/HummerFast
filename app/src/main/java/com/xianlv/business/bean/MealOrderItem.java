package com.xianlv.business.bean;

import java.util.List;

public class MealOrderItem {

    public String orderId;
    public String orderNo;
    public String createTime;
    public String payTime;
    public String payMoney;
    public String statusName;
    public String payTypeDesc;
    public String expressTime;



    public Consumer consumer;
    public class Consumer{
        public String roomNo;
        public String consumerName;
        public String consumerPhone;
        public String expect;
    }

    public List<Goods> detailVOS;
    public class Goods{
        public String pics;
        public String foodName;
        public String skuName;
        public String skuPrice;
        public String num;


    }
}
