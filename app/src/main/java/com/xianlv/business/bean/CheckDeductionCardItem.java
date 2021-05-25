package com.xianlv.business.bean;

import java.util.List;

public class CheckDeductionCardItem {
    public String myCouponId;
    public String couponName;
    public String viewVo;
    public String employVo;
    public String dayTimeVo;
    //支持人数（早餐券）
    public String breakfastId;
    public String breakfastType;
    public String roomNumber;
    public String effectiveTime;
    //储值卡核销
    public String cardUserId;//卡名称
    public String cardName;//卡名称
    public String nickname;//姓名
    public String phone;//电话
    public String levelName;//会员级别
    public String balance;//可用余额
    public List<String> shopNames;//营业点
    //储值卡列表
    public String logo;//卡名称
    public String cardUserNo;//卡名称
    //商城券
    public boolean isSelect;
    public String img;//图片
    public String productName;//名称
    public String property;//
    public int status;//0 未核销  1已核销




}
