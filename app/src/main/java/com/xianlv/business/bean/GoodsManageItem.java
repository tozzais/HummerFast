package com.xianlv.business.bean;

import java.util.ArrayList;

public class GoodsManageItem {
    public String productId;
    public String productName;
    public String logo;
    public String typeName;
    public int status;
    public ArrayList<GoodsManageItemSku> appSkuList;

    public String getStatus() {
        if (status == 0){
            return "已上架";
        }else if (status == 1){
            return "已下架";
        }else {
            return "已删除";
        }
    }
}
