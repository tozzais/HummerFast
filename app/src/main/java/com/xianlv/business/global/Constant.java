package com.xianlv.business.global;

import android.os.Environment;

/**
 * 全部的常量类。
 * Created by Administrator on 2017/4/15.
 */

public class Constant {

    //图片地址
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/cinderellavip/image";
    public static final String ROOT_PATH = Environment.getExternalStorageDirectory() + "/cinderellavip";
    public static final String cacheDirPath = Environment
            .getExternalStorageDirectory() + "/cinderellavip";


    public static String user_login = "cinder_user_login";
    public static String user_id = "cinder_user_id";
    public static String user_token = "cinder_user_token";
    public static String user_first_use = "cinder_user_first_use";
    public static String user_login_finish = "cinder_user_login_finish";
    public static String user_recommend_code = "cinder_user_recommend_code";
    public static String user_is_vip = "cinder_is_vip";

    public static String user_bean_string = "cinder_user_login_bean";
    public static String user_bean_phone = "cinder_phone_bean";

    public static String search = "cinder_user_search";
    public static String search_post = "cinder_user_search_post";
    public static String search_topic = "cinder_user_search_topic";
    public static String search_life = "cinder_user_search_life";






    //微信相关
    public static String SMALL_APPLICATION_ID = "gh_659404972ef7";
    public static String WX_APPID = "wx85d0dcba9bb8a9dc";
    public static String WX_APP_SECRET = "4c5cee09aad0705dcf43e9dc1805c515";

    /**
     * h5相关
     *
     */


    public static final int MENTION = 0; //自提
    public static final int LOGISTICS = 1; //物流

    public static final String KEY_SERVICE = "f100241716204bfa86c916e94a4e737e"; //客服appkey




}
