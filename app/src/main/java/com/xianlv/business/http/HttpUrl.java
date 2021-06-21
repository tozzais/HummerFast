package com.xianlv.business.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {

    String weather_url = "http://api.k780.com/";


    String base_url = "https://shanghaixianlv.com/";
//    String base_url = "https://test.shanghaixianlv.com/";
    String server_url = base_url+"hotel-client/";
    String image_url = base_url;


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

    String give_way_list = "goods/queryGoodsOrderStatus";//送物列表
    String give_way_confirm = "goods/updateStatus";//确认送达
    String clean_list = "lostArticle/querySweep";//打扫列表
    String clean_complete = "lostArticle/updateSweep";//打扫完成
    String main_number = "lostArticle/querySweepNum";//打扫数量

    String cash_list = "cash/queryCashOrderList";//押金管理
    String cash_detail = "cash/queryCashOrder";//押金页面
    String cash_update = "cash/updateCashOrder";
    String cash_return = "cash/refundOrderCash";
    String check_deduction = "verification/selectWithhold";//查询扣款
    String coupon_verification = "mycoupon/verification";
    String ver_history = "verification/getVerificationRecordsList";
    String write_breakfast_park = "voucher/breakfastVoucher/use";
    String card_reduce = "card/withhold";//储值卡扣款
    String card_reduce_detail = "card/withholdBalance";//储值卡扣款明细
    String goods_verification = "verification/productVerification";//商品核销




}
