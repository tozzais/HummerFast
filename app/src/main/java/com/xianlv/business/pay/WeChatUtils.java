package com.xianlv.business.pay;

import android.content.Context;
import android.util.Log;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tozzais.baselibrary.util.toast.ToastCommom;
import com.xianlv.business.MyApp;
import com.xianlv.business.bean.recharge.RechargeQuestResult;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jumpbox on 16/6/2.
 */
public class WeChatUtils {

    //微信相关
    public static String SMALL_APPLICATION_ID = "gh_269043eeeee4";
    public static String WX_APPID = "wxa47d5e47b775478e";
    public static String WX_APP_SECRET = "0338afaaaacfdcde4e778cc2df841df4";


    private static WeChatUtils weChatUtils;
    private static IWXAPI api;

//    public static final String APP_ID = "wx36f2ac3b90001625";
//    public static final String MCH_ID = "1563066941";
//    public static final String API_KEY = "527e88e9740da08028e0b34226ae5ba8";//商户平台秘钥

    public static WeChatUtils getInstance(Context context) {
        if (weChatUtils == null) {
            weChatUtils = new WeChatUtils();
            // 通过WXAPIFactory工厂，获取IWXAPI的实例
            api = WXAPIFactory.createWXAPI(context, null);
            api.registerApp(WX_APPID);
        }
        return weChatUtils;
    }

    public void wechatPay(RechargeQuestResult info) {
        if (!api.isWXAppInstalled()) {
            ToastCommom.createToastConfig().ToastShow(MyApp.mContext, "您还没有安装微信");
            return;
        }
                    try {
                        PayReq req = new PayReq();
//                        req.appId = info.APPID;
//                        req.partnerId = info.PARTNERID;
//                        req.prepayId = info.PREPAYID;
//                        req.nonceStr = info.NONCESTR;
//                        req.timeStamp = info.TIMESTAMP;
//                        req.packageValue = info.PACKAGE;
//                        req.sign = info.SIGN;
                        req.appId = info.getAppId();
//                        req.partnerId = info.PARTNERID;
//                        req.prepayId = info.PREPAYID;
                        req.nonceStr = info.getNonceStr();
                        req.timeStamp = info.getTimeStamp();
//                        req.packageValue = info.PACKAGE;
                        req.sign = info.getPaySign();


                        api.sendReq(req);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


    }

    private String getNonceStr(){
        return MD5.getMessageDigest((Math.random()+"").getBytes());
    }


    private void genPayReq(PayReq req) {
        List<NameValuePair> signParams = new LinkedList<>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

        req.sign = genAppSign(signParams);
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(WX_APP_SECRET);

        String appSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        Log.v("orion", appSign);
        return appSign;
    }

    public void release() {
        api = null;
        weChatUtils = null;
    }

}
