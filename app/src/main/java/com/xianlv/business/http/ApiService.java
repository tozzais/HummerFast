package com.xianlv.business.http;


import com.xianlv.business.bean.CallMorningItem;
import com.xianlv.business.bean.CardReduceDetail;
import com.xianlv.business.bean.CashDetail;
import com.xianlv.business.bean.CashItem;
import com.xianlv.business.bean.CheckDeductionCardItem;
import com.xianlv.business.bean.CheckDeductionGoodsItem;
import com.xianlv.business.bean.CheckDeductionItem;
import com.xianlv.business.bean.CheckInItem;
import com.xianlv.business.bean.CheckOutItem;
import com.xianlv.business.bean.CleanItem;
import com.xianlv.business.bean.CodeBean;
import com.xianlv.business.bean.CollectionHistoryItem;
import com.xianlv.business.bean.CouponHistoryItem;
import com.xianlv.business.bean.CouponItem;
import com.xianlv.business.bean.DepositItem;
import com.xianlv.business.bean.GiveWayItem;
import com.xianlv.business.bean.HouseResult;
import com.xianlv.business.bean.LoginBean;
import com.xianlv.business.bean.MainNumberBean;
import com.xianlv.business.bean.MessageItem;
import com.xianlv.business.bean.MineInfo;
import com.xianlv.business.bean.RankItem;
import com.xianlv.business.bean.RoomOrderDetail;
import com.xianlv.business.bean.RoomOrderItem;
import com.xianlv.business.bean.ShopResult;
import com.xianlv.business.bean.VideoDetail;
import com.xianlv.business.bean.VideoItem;
import com.xianlv.business.bean.VisitorUserItem;
import com.xianlv.business.bean.WriteOffHistoryItem;
import com.xianlv.business.bean.request.BaseRequest;
import com.xianlv.business.bean.request.RequestBreakfastId;
import com.xianlv.business.bean.request.RequestCardReduce;
import com.xianlv.business.bean.request.RequestCardReduceDetail;
import com.xianlv.business.bean.request.RequestCashId;
import com.xianlv.business.bean.request.RequestCashUpdate;
import com.xianlv.business.bean.request.RequestCategory;
import com.xianlv.business.bean.request.RequestCheckDeduction;
import com.xianlv.business.bean.request.RequestCode;
import com.xianlv.business.bean.request.RequestEditMessage;
import com.xianlv.business.bean.request.RequestGiveWay;
import com.xianlv.business.bean.request.RequestGoodsOrderId;
import com.xianlv.business.bean.request.RequestGoodsVerify;
import com.xianlv.business.bean.request.RequestList;
import com.xianlv.business.bean.request.RequestLogin;
import com.xianlv.business.bean.request.RequestMyCouponId;
import com.xianlv.business.bean.request.RequestPhone;
import com.xianlv.business.bean.request.RequestRank;
import com.xianlv.business.bean.request.RequestRegister;
import com.xianlv.business.bean.request.RequestShopId;
import com.xianlv.business.bean.request.RequestShopInfo;
import com.xianlv.business.bean.request.RequestStaffHousingId;
import com.xianlv.business.bean.request.RequestSweepId;
import com.xianlv.business.bean.request.RequestVoucher;
import com.xianlv.business.bean.request.RequestVoucherId;
import com.xianlv.business.bean.weather.WeatherResult;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by jumpbox on 16/5/2.
 */
public interface ApiService {

    @POST(HttpUrl.get_code)
    Observable<BaseResult>
    getCode(@Body RequestCode requestCode);



    @POST(HttpUrl.login)
    Observable<BaseResult<LoginBean>>
    getLogin(@Body RequestLogin bean);
    @POST(HttpUrl.shop_info)
    Observable<BaseResult<ShopResult>>
    shopInfo(@Body RequestShopInfo bean);
    @POST(HttpUrl.register)
    Observable<BaseResult>
    register(@Body RequestRegister requestCode);
    @POST(HttpUrl.mine_info)
    Observable<BaseResult<MineInfo>>
    mineInfo(@Body BaseRequest bean);
    //小程序二维码
    @POST(HttpUrl.code_applets)
    Observable<BaseResult<CodeBean>>
    code_applets(@Body BaseRequest bean);
    //收款码
    @POST(HttpUrl.code_receive)
    Observable<BaseResult<CodeBean>>
    code_receive(@Body RequestShopId bean);
    //收款历史
    @POST(HttpUrl.history_collection_history)
    Observable<BaseListResult<CollectionHistoryItem>>
    history_collection_history(@Body RequestList bean);
    //收款历史
    @POST(HttpUrl.rank)
    Observable<BaseListResult<RankItem>>
    getRank(@Body RequestRank bean);
    //寄存列表
    @POST(HttpUrl.workerDepositItems)
    Observable<BaseListResult<DepositItem>>
    getDeposit(@Body RequestList bean);
    //叫早服务
    @POST(HttpUrl.call_morning)
    Observable<BaseListResult<CallMorningItem>>
    callMorning(@Body RequestList bean);
    //访客记录
    @POST(HttpUrl.visitorUser)
    Observable<BaseListResult<VisitorUserItem>>
    visitorList(@Body RequestList bean);
    @POST(HttpUrl.check_in_list)
    Observable<BaseListResult<CheckInItem>>
    checkInList(@Body RequestList bean);
    @POST(HttpUrl.check_in_pass)
    Observable<BaseResult>
    checkInPass(@Body RequestStaffHousingId bean);
    @POST(HttpUrl.check_in_refuse)
    Observable<BaseResult>
    checkInRefuse(@Body RequestStaffHousingId bean);
    @POST(HttpUrl.check_out_history)
    Observable<BaseListResult<CheckOutItem>>
    checkOutList(@Body RequestList bean);
    @POST(HttpUrl.coupon_list)
    Observable<BaseListResult<CouponItem>>
    coupon_list(@Body RequestVoucher bean);
    @POST(HttpUrl.coupon_deal_with)
    Observable<BaseResult>
    couponDeal(@Body RequestVoucherId bean);
    @POST(HttpUrl.coupon_history)
    Observable<BaseListResult<CouponHistoryItem>>
    coupon_history(@Body RequestVoucher bean);
    @POST(HttpUrl.give_way_list)
    Observable<BaseListResult<GiveWayItem>>
    give_way_list(@Body RequestGiveWay bean);
    @POST(HttpUrl.give_way_confirm)
    Observable<BaseResult>
    give_way_confirm(@Body RequestGoodsOrderId bean);
    @POST(HttpUrl.clean_list)
    Observable<BaseListResult<CleanItem>>
    clean_list(@Body RequestGiveWay bean);
    @POST(HttpUrl.clean_complete)
    Observable<BaseResult>
    clean_complete(@Body RequestSweepId bean);
    @POST(HttpUrl.main_number)
    Observable<BaseResult<MainNumberBean>>
    main_number(@Body BaseRequest bean);
    @POST(HttpUrl.cash_list)
    Observable<BaseListResult<CashItem>>
    cash_list(@Body RequestShopId bean);
    @POST(HttpUrl.cash_detail)
    Observable<BaseResult<CashDetail>>
    cash_detail(@Body RequestCashId bean);
    @POST(HttpUrl.cash_update)
    Observable<BaseResult<CashDetail>>
    cash_update(@Body RequestCashUpdate bean);
    @POST(HttpUrl.cash_return)
    Observable<BaseResult>
    cash_return(@Body RequestCashUpdate bean);
    //查询扣款
    @POST(HttpUrl.check_deduction)
    Observable<BaseListResult<CheckDeductionItem>>
    check_deduction(@Body RequestCheckDeduction bean);
    //查询扣款
    @POST(HttpUrl.check_deduction)
    Observable<BaseListResult<CheckDeductionGoodsItem>>
    check_deduction1(@Body RequestCheckDeduction bean);
    //查询扣款
    @POST(HttpUrl.check_deduction)
    Observable<BaseListResult<CheckDeductionCardItem>>
    check_deduction2(@Body RequestCheckDeduction bean);
    //优惠券核销
    @POST(HttpUrl.coupon_verification)
    Observable<BaseResult>
    coupon_verification(@Body RequestMyCouponId bean);
    //核销记录
    @POST(HttpUrl.ver_history)
    Observable<BaseListResult<WriteOffHistoryItem>>
    ver_history(@Body RequestCategory bean);
    @POST(HttpUrl.write_breakfast_park)
    Observable<BaseResult>
    write_breakfast_park(@Body RequestBreakfastId bean);
    @POST(HttpUrl.card_reduce)
    Observable<BaseResult<String>>
    card_reduce(@Body RequestCardReduce bean);
    @POST(HttpUrl.card_reduce_detail)
    Observable<BaseListResult<CardReduceDetail>>
    card_reduce_detail(@Body RequestCardReduceDetail bean);
    //商品核销
    @POST(HttpUrl.goods_verification)
    Observable<BaseResult>
    goods_verification(@Body RequestGoodsVerify bean);
    //商品核销
    @POST("?app=weather.today&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json")
    Observable<WeatherResult>
    getWeather(@Query("cityNm") String bean);
    //消息列表
    @POST("app/jiguang/list")
    Observable<BaseListResult<MessageItem>>
    messageList(@Body RequestList bean);
    //消息已读S
    @POST("app/jiguang/edits")
    Observable<BaseResult>
    messageEdit(@Body RequestEditMessage bean);
    //酒店列表
    @POST("worker_login/getInTenantList")
    Observable<BaseResult<HouseResult>>
    houseList(@Body RequestPhone bean);
    //切换酒店
    @POST("worker_login/workerChangeTenant")
    Observable<BaseResult<LoginBean>>
    changeHouse(@Body Map<String,String> bean);
    //订房订单
    @POST("roomOrder/getRoomOrderListOfStaff")
    Observable<BaseListResult<RoomOrderItem>>
    roomOrder(@Body Map<String,String> bean);
    //订房订单详情
    @POST("roomOrder/getOrderDetail")
    Observable<BaseResult<RoomOrderDetail>>
    roomOrderDetail(@Body Map<String,String> bean);
    //订房订单确认
    @POST("roomOrder/confirm")
    Observable<BaseResult>
    roomOrderSure(@Body Map<String,String> bean);
    //订房订单取消
    @POST("roomOrder/cancelAndRefundOrder")
    Observable<BaseResult>
    roomOrderCancel(@Body Map<String,String> bean);
    //送餐订单
    @POST("restaurant/order/staffOrders")
    Observable<BaseResult>
    mealOrder(@Body Map<String,String> bean);
    //视频列表
    @POST("university/video")
    Observable<BaseListResult<VideoItem>>
    videoList(@Body Map<String,String> bean);
    //视频详情
    @POST("university/videoById")
    Observable<BaseResult<VideoDetail>>
    videoDetail(@Body Map<String,String> bean);
    //更新视频播放量
    @POST("university/updateViews")
    Observable<BaseResult>
    updateVideoViews(@Body Map<String,String> bean);
    //常见问题列表
    @POST("university/problem")
    Observable<BaseListResult<VideoItem>>
    problemList(@Body Map<String,String> bean);



}
