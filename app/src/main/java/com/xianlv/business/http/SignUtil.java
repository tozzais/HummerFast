package com.xianlv.business.http;


import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.MessageDigest;

public class SignUtil {

    //静态方法，便于作为工具类
    public static String getMd5(Object object) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(("80085089236c773446cae2df4fdb4b46"+getObjectJson(object)).getBytes("UTF-8"));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取指定对象的json（去除该对象的sign字段）
     * @param object
     * @return
     */
    public static String getObjectJson(Object object) {
        GsonBuilder builder = new GsonBuilder();
        builder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getName().equals("sign");
            }
            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        });
        Gson gson = builder.create();
        return gson.toJson(object);
    }









}
