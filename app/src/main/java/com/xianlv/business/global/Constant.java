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



    /*
        蜂鸟速充安卓对接
        一、Html传值给安卓调用方法：window.htmlMessage.message(JSON.stringify(message));
        message = {
         type: 1, //类型
         value: ‘’//值为泛型
        }
        type:
        1.登录
        2.退出登录
        3.扫码充电

        二、安卓调取h5方法
        登录：userLogin(token)
        退出登录：userLogout()

        三、加载页面 https://fn.hanyu365.com.cn/park/h5/index.html#/pages/index/index?
        route=&
        longitude=&
        latitude&
        city=&
        atOpenStatus=&
        value=
        1.非必填
        longitude：当前城市纬度
        latitude：当前城市经度
        city：当前城市名称
        atOpenStatus：当前城市是否开通
        2必填
        route：页面名称
        value：页面需要参数 为字典形式，需转成json字符串，如页面需要id参数，则value 为 {id:0} 的json字符串
        route值及value如下：
        topup：底部菜单余额充值
        scan：底部菜单扫码充电
        activity：底部菜单优惠活动
        chargeInfo：充电桩详情，value中id为充电桩id
        mineInfo：个人资料
        car：我的车辆
        order：我的订单
        invoice：我的发票
        wallet：我的钱包
        coupon：我的优惠券
        collect：我的收藏
        message：我的消息
        html：关于我们，value中的 value为3、text为3、title为 关于我们
        start：开始充电，value中的code为终端编号
     */
    public static final String url = "https://fn.hanyu365.com.cn/park/h5/index.html#/pages/index/index?route=";
    //底部扫码充电
    public static final String scan_charge_url = url+"scan";
    //底部福利活动
    public static final String welfare_activity_url = url+"activity";
    //底部余额充值
    public static final String balance_charge_url = url + "topup";
    //个人资料
    public static final String mine_info_url = url + "mineInfo";
    //我的消息
    public static final String mine_message_url = url + "message";
    //我的车辆
    public static final String mine_car_url = url + "car";
    //我的订单
    public static final String mine_order_url = url + "order";
    //我的发票
    public static final String mine_invoice_url = url + "invoice";
    //我的钱包
    public static final String mine_wallet_url = url + "wallet";
    //我的优惠券
    public static final String mine_coupon_url = url + "coupon";
    //我的收藏
    public static final String mine_collect_url = url + "collect";
    //我的消息
    public static final String message_url = url + "message";
    //关于我们
    public static final String about_us_url = url + "html&value=";
    public static final String about_us_url2 = url + "html&value=2";
    public static final String about_us_url1 = url + "html&value=1";
    //充电
    public static final String setting_url = url + "setting&value=";

    //充电桩详情
    public static final String charge_info_url = url + "chargeInfo&value=";
    //充电
    public static final String charge_url = url + "start&value=";






}
