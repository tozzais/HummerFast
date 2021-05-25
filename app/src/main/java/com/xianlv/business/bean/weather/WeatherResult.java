package com.xianlv.business.bean.weather;


/**
 * Created by jumpbox on 16/8/23.
 */
public class WeatherResult{

    public String success;
    public ResultDTO result;

    public static class ResultDTO {
        public String weather_curr;
        public String weather_icon;
        public String temp_curr;
        public String temp_low;
    }
}
