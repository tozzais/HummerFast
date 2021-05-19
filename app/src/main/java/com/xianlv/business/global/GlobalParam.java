package com.xianlv.business.global;


import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.tozzais.baselibrary.util.SharedPreferencesUtil;
import com.xianlv.business.MyApp;
import com.xianlv.business.bean.LoginBean;

import java.util.Objects;


/**
 * Created by jumpbox on 16/4/19.
 */
public class GlobalParam {


    private static void setAlias( String alias) {
        // 调用 JPush 接口来设置别名。
//        JPushInterface.setAliasAndTags(CinderellaApplication.mContext,
//                alias,
//                null, (i, s, set) -> {
//                    if (i == 0){
//                        LogUtil.e("设置成功");
//                    }else if (i==6002){
//                        LogUtil.e("设置失败"+i);
//                    }
//                });



    }



    //是否使用
    public static void setFirstUse(boolean firstUse) {
        SharedPreferencesUtil.saveBooleanData(MyApp.mContext, Constant.user_first_use, firstUse);
    }
    public static boolean getFirstUse() {
        return SharedPreferencesUtil.getBooleanData(MyApp.mContext, Constant.user_first_use,false);
    }


    //存 用户的token
    public static void setUserToken(String userid) {
        SharedPreferencesUtil.saveStringData(MyApp.mContext, Constant.user_token, userid);
    }
    //取 用户的用户的token
    public static String getUserToken() {
        return Objects.requireNonNull(getLoginBean()).token;
    }

    //是否登录
    public static void setUserLogin(boolean userid) {
        SharedPreferencesUtil.saveBooleanData(MyApp.mContext, Constant.user_login, userid);
    }
    public static boolean getUserLogin() {
        return getLoginBean() != null;
    }

    public static boolean getUserLogin(Context context) {
        boolean isLogin = SharedPreferencesUtil.getBooleanData(MyApp.mContext, Constant.user_login, false);
        if (!isLogin){
//            LoginActivity.launch((Activity) context,true);
        }
        return isLogin;
    }

    //存 用户的token
    public static void setUserId(String userid) {
        if (TextUtils.isEmpty(userid)){
            setAlias("");
        }else {
            setAlias("huiguniang_"+userid);
        }
        SharedPreferencesUtil.saveStringData(MyApp.mContext, Constant.user_id, userid);
    }
    //取 用户的用户的token
    public static String getUserId() {
        return SharedPreferencesUtil.getStringData(MyApp.mContext, Constant.user_id,"");
    }





    //存 用户 信息
    public static void exitLogin() {
        setUserLogin(false);
        setUserToken("");
        setUserId("");
    }




    //存储用户登录信息
    public static void setLoginBean(LoginBean userInfo) {
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


    //存 帖子搜索记录
    public static void setSearchPost(String userid) {
        SharedPreferencesUtil.saveStringData(MyApp.mContext, Constant.search_post, userid);
    }
    //取 帖子搜索记录
    public static String getSearchPost() {
        return SharedPreferencesUtil.getStringData(MyApp.mContext, Constant.search_post,"");
    }

    //存 话题搜索记录
    public static void setSearchTopic(String userid) {
        SharedPreferencesUtil.saveStringData(MyApp.mContext, Constant.search_topic, userid);
    }
    //取 话题搜索记录
    public static String getSearchTopic() {
        return SharedPreferencesUtil.getStringData(MyApp.mContext, Constant.search_topic,"");
    }



    //登录成功是否直接返回 用户没登录的情况 操作之后 返回当前界面
    public static void setLoginFinish(boolean loginFinish) {
        SharedPreferencesUtil.saveBooleanData(MyApp.mContext, Constant.user_login_finish, loginFinish);
    }
    public static boolean getLoginFinish() {
        return SharedPreferencesUtil.getBooleanData(MyApp.mContext, Constant.user_login_finish,false);
    }



}
