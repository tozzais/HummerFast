package com.tozzais.baselibrary.util.sign;


import android.util.Log;

import com.google.gson.Gson;
import com.tozzais.baselibrary.util.log.LogUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class SignUtil {
    //静态方法，便于作为工具类
    public static String getMd5(String keys) {
//        Arrays.sort(keys);
//        StringBuffer sign = new StringBuffer();
//        for (String s : keys)
//            sign.append(s);

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(("80085089236c773446cae2df4fdb4b46" + keys).getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    //静态方法，便于作为工具类
    public static String getMd5(TreeMap<String, String> keys) {
//        Arrays.sort(keys);
//        keys.put("appSecret","ipd");
//        StringBuffer sign = new StringBuffer();
//        for (Map.Entry<String, String> entry : keys.entrySet()) {
//            sign.append(entry.getValue());
//        }
        String key = "80085089236c773446cae2df4fdb4b46";
        String sign = key + (new Gson().toJson(keys));
        LogUtil.e("加密前"+sign);

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(("" + sign).getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            LogUtil.e("加密后"+buf.toString());
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }


}
