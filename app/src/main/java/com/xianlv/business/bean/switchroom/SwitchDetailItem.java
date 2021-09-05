package com.xianlv.business.bean.switchroom;

/**
 * 每隔促销房的详情
 */
public class SwitchDetailItem  {



    //手动添加 为了弹窗使用
    public String roomName;
//
//            public int roomSkuId;
            public String promotionId;
//            public long workDate;
            public String workDateStr;
//            public int level;
            public String price; //价格
            public int status; //房态 0 正常 1关闭
//            public int shopId;
//            public Object workDateParam;
//            public Object levelIdParam;
            public String store; //库存
//            public Object week;
            public String roomStoreId;
//            public int promotionIdStore;
//            public String workDateStore;

    public boolean isOpen(){
        return 0 == status;
    }
}
