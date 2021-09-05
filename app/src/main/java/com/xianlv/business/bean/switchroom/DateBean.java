package com.xianlv.business.bean.switchroom;


/**
 * 数据日期的item
 */
public class DateBean {

    public String date;
    public String week;

    public String getDate(){
        if (date != null && date.length()>5){
            return date.substring(5)+week;
        }
        return date+week;
    }
}
