package com.xianlv.business.bean;

import java.util.List;

public class WriteOffHistoryItem {
    //优惠券
    public String money;
    public String createtime;
    public String nickname;
    public String levelName;
    public String vername;
    public String viewVo;
    public String couponName;
    //储值卡核销
    public String recordsMoney;
    public String remark;
    //商品核销
    public String num;
    public List<Product> productNameVos;
    public class Product{

        public String id;
        public String name;
        public int num;
    }

}
