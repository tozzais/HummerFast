package com.xianlv.business.pay;

import android.app.Activity;

import com.tozzais.baselibrary.http.RxHttp;
import com.xianlv.business.bean.recharge.RechargeQuestResult;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;

import java.util.TreeMap;

public class PayUtil {

    public static void pay(Activity context, double price, String payType, String source,String openid, PayListener listener){
        TreeMap<String, String> hashMap = new TreeMap<>();
        hashMap.put("price", price + "");
        hashMap.put("payType", payType);
        hashMap.put("source", source);
        hashMap.put("openid", openid);
//        if ("3".equals(payway)){
//            new RxHttp<BaseResult<RechargeQuestResult>>().send(ApiManager.getService().recharge(hashMap),
//                    new Response<BaseResult<RechargeQuestResult>>(context) {
//                        @Override
//                        public void onSuccess(BaseResult<RechargeQuestResult> result) {
//                            WeChatUtils.getInstance(context).wechatPay(result.data);
//                        }
//                    });
//
//        }else {
            new RxHttp<BaseResult<RechargeQuestResult>>().send(ApiManager.getService().recharge(hashMap),
                    new Response<BaseResult<RechargeQuestResult>>(context) {
                        @Override
                        public void onSuccess(BaseResult<RechargeQuestResult> result) {
                            switch (payType) {
                                case "1":
                                    WeChatUtils.getInstance(context).wechatPay(result.data);
                                    break;
                                case "2":
//                                    aliPay(context,result.data.getPaySign(),listener);
                                    break;
                            }

                        }
                    });
//        }


    }

    private static  void aliPay(Activity context,String pay, PayListener listener) {
        AlipayUtils.getInstance().alipay(context, pay, new AlipayUtils.OnPayListener() {
            @Override
            public void onPaySuccess() {
                if (listener != null){
                    listener.onResult(true);
                }
//                PayResultActivity.launch(context,order_id,true);
            }

            @Override
            public void onPayWait() {
                if (listener != null){
                    listener.onResult(false);
                }
            }

            @Override
            public void onPayFail() {
                if (listener != null){
                    listener.onResult(false);
                }
//                PayResultActivity.launch(context,order_id,false);
            }
        });

    }
}
