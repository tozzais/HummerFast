package com.xianlv.business.bean.switchroom;


import java.util.List;

/**
 * 修改价格页面的item
 */
public class RoomPriceItem {

    //修改价格传递给后端用的
    public String nonce_str;
    public String workDate;
    public List<RoomPriceDetail> levelParam;


    public String date;

    public String week;
    public String promotionName;

    public String roomName;
    public String promotionId;

    public List<RoomPriceDetail> leveList;


    public String getDate(){
        if (date != null && date.length()>5){
            return date.substring(5)+week;
        }
        return date+week;
    }
}
