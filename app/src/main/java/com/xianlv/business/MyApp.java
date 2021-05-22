package com.xianlv.business;

import android.app.Application;
import android.content.Context;

import com.ycbjie.webviewlib.X5WebUtils;

/**
     1：接口URL没有。需要提供url。IOS目前打开应用闪退（看一下原因 这边需要对一下）
     2：提供应用图标
     3：04用户扫码跳转页面 怎么进入的。
     4：“13储值卡核销 查询扣款页面”怎么进入的。12也是储值卡核销 查询扣款。还是说13 是卡号查询？
     5：63 商品核销页面怎么进入的
     6：首页 订房订单 点击是否跳转到 02 订单处理页面？
     7 首页 送餐订单是否到 57送餐提醒页面？
     8：排行榜 奖金说明 我看IOS好像没有。然后奖金说明的文字和UI是否一致？不一致的话需要提供
     9：学习成就目前UI没有切图，IOs好像没有。怎么处理？
     10：电话会议和叫早服务暂时没有（需要影藏掉吗）

 需要完成的:
 1:订房订单UI
 2：订餐订单UI
 3：商品管理UI
 4：学习成就UI
 5：奖金说明UI

 隐私条款和和用户协议的连接
 小程序二维码和门店收款码地址一个是全地址一个是相对地址
 销量排行榜没有年月日筛选条件及分页条件
 常见问题接口
 押金管理列表没有分页吗？
 */
public class MyApp extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        X5WebUtils.init(this);
    }
}
