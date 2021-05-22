package com.xianlv.business.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {

    String server_url = "https://test.shanghaixianlv.com/hotel-client/";
    String get_code = "code/send";  //获取验证码
    String login = "worker_login/login";  //用户名登录
    String shop_info = "worker_login/toBeWorker";//扫描二维码 获取门店信息
    String register = "worker_login/workerRegister";//注册
    String mine_info = "employee/related/information";//个人信息
    String code_applets = "app/applet/code";//小程序二维码
    String code_receive = "shop/getQrCode";//收款码
    String history_collection_history = "scan/queryScan";//收款历史
    String rank = "ranking/getRankingList";//排行榜
    String workerDepositItems = "workerDepositItems/list";//寄存列表
    String call_morning = "app/call/morningWork";//叫早服务
    String visitorUser = "app/registration/visitorUser";//访客记录

    String check_in_pass = "staffHousing/processing/pass";//入住通过
    String check_in_refuse = "staffHousing/processing/refuse";//入住拒绝
    String check_in_list = "staffHousing/processing/list";//入住申请
    String check_out_history = "checkOut/history";//离店历史
    String coupon_list = "workerVoucher/application/list";//券的申请列表
    String coupon_deal_with = "workerVoucher/application/dealWith";//券的处理
    String coupon_history = "workerVoucher/history/list";//券的申请历史




}
