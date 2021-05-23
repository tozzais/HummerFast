package com.xianlv.business;

import android.app.Application;
import android.content.Context;

import com.ycbjie.webviewlib.X5WebUtils;

/**
bug:
 1:储值卡核销的时候填写备注的时候 返回的remark不对。
 2:储值卡在小程序里查看有两张，但是我储值卡扣款 手机号查询的时候 只有一条数据（手机号 13166015579）
还没有写完的东西：
 1：隐私条款和和用户协议的链接
 2：小程序二维码图片地址是相对地址，图片的前缀是多少？
 3：奖金说明接口
 4: 常见问题接口
5：押金管理列表没有分页吗？

 未接：
 送物历史 打扫历史 押金管理 历史去掉
 核销记录 叫早服务


 常见问题接口
 押金管理列表没有分页吗？
 储值卡核销提示没有权限
 商品核销我下单三个商品的订单支付了 显示未支付。无法测试多商品核销
 核销记录接口出错

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
