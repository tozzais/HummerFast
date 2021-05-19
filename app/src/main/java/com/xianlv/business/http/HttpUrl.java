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




}
