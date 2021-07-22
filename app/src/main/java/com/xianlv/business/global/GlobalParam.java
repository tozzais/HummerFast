package com.xianlv.business.global;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.tozzais.baselibrary.util.SharedPreferencesUtil;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.xianlv.business.MyApp;
import com.xianlv.business.bean.LoginBean;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by jumpbox on 16/4/19.
 */
public class GlobalParam {


    private static void setAlias( String alias) {
//         调用 JPush 接口来设置别名。
        JPushInterface.setAliasAndTags(MyApp.mContext,
                alias,
                null, (i, s, set) -> {
                    if (i == 0){
                        LogUtil.e("设置成功");
                    }else if (i==6002){
                        LogUtil.e("设置失败"+i);
                    }
                });
    }



    //是否使用
    public static void setFirstUse(boolean firstUse) {
        SharedPreferencesUtil.saveBooleanData(MyApp.mContext, Constant.user_first_use, firstUse);
    }
    public static boolean getFirstUse() {
        return SharedPreferencesUtil.getBooleanData(MyApp.mContext, Constant.user_first_use,false);
    }

    //是否登录
    public static void setUserLogin(boolean userid) {
        SharedPreferencesUtil.saveBooleanData(MyApp.mContext, Constant.user_login, userid);
    }
    public static boolean getUserLogin() {
        return getLoginBean() != null;
    }


    //存 用户 信息
    public static void exitLogin() {
        setAlias("");
        SharedPreferencesUtil.saveStringData(MyApp.mContext, Constant.user_bean_string, "");
    }


    //存储用户登录信息
    public static void setLoginBean(LoginBean userInfo) {
        setAlias(userInfo.tenantId+userInfo.phone);
        Gson gson = new Gson();
        SharedPreferencesUtil.saveStringData(MyApp.mContext, Constant.user_bean_string, gson.toJson(userInfo));
    }
    //获取用户登录信息
    public static LoginBean getLoginBean() {
        String data = SharedPreferencesUtil.getStringData(MyApp.mContext, Constant.user_bean_string, "");
        if (TextUtils.isEmpty(data)){
            return null;
        }
        return new Gson().fromJson(data,LoginBean.class);
    }
    //获取用户登录信息
    public static String getShopId() {
        LoginBean loginBean = getLoginBean();
        return loginBean == null?"":loginBean.shopId;
    }

    //获取用户登录信息
    public static String getUid() {
        LoginBean loginBean = getLoginBean();
        return loginBean == null?"":loginBean.shopId;
    }






}
