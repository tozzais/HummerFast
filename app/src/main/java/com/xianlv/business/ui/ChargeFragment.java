package com.xianlv.business.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.xianlv.business.AppJs;
import com.xianlv.business.R;
import com.xianlv.business.adapter.CityListAdapter;
import com.xianlv.business.adapter.gv.CityAdapter;
import com.xianlv.business.bean.JsBean;
import com.xianlv.business.bean.home.CityResult;
import com.xianlv.business.bean.recharge.JsPayBean;
import com.xianlv.business.bean.recharge.RechargeQuestResult;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;


public class ChargeFragment extends BaseFragment {


    @BindView(R.id.web_view)
    WebView web_view;
    @BindView(R.id.fragment_toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tv_title;


    public static ChargeFragment newInstance(String url) {
        ChargeFragment balanceFragment = new ChargeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        balanceFragment.setArguments(bundle);
        return balanceFragment;
    }


    @Override
    public int setLayout() {
        return R.layout.fragment_webview;
    }


    private String url;

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        url = getArguments().getString("url");
        if (GlobalParam.getLoginBean() != null){
            url = url +"&token="+GlobalParam.getLoginBean().token;
        }
        LogUtil.e("加载的url = "+url);

        web_view.loadUrl(url);

        web_view.setWebChromeClient(new WebChrome());
        WebSettings settings = web_view.getSettings();
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);


        settings.setDomStorageEnabled(true);
        settings.setAppCacheMaxSize(1024*1024*8);
        String appCachePath = mActivity.getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);
        web_view.getSettings().setAppCacheEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setGeolocationEnabled(true);


        web_view.addJavascriptInterface(new AppJs1(), "htmlMessage");

        toolbar.setVisibility(View.VISIBLE);
        tv_title.setText("扫描充电");

        web_view.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                if (GlobalParam.getLoginBean() != null){
                    LogUtil.e("调用H5加载了"+GlobalParam.getLoginBean().token);
                    web_view.loadUrl("javascript:userLogin(\"" + GlobalParam.getLoginBean().token + "\")");
                }
            }
        });


    }

    public class WebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            if (i == 100) {

            }

        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
//            AgreementWebViewActivity.this.valueCallback = valueCallback;
//            selectImage();
            return true;
        }


    }


    @Override
    public void loadData() {


    }


    @Override
    public void initListener() {
        super.initListener();

    }


    @Override
    public void onResume() {
        super.onResume();
        if (web_view != null) {
            web_view.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (web_view != null) {
            web_view.getSettings().setJavaScriptEnabled(false);
        }
    }

    @Override
    public void onDestroy() {
        try {
            if (web_view != null) {
                web_view.stopLoading();
                web_view.destroy();
                web_view = null;
            }
        } catch (Exception e) {
        }
        super.onDestroy();
    }


    public class AppJs1 extends Object {
        public AppJs1() {

        }


        @JavascriptInterface
        public void toLogin(String data) {
            LogUtil.e(data);

        }

        @JavascriptInterface
        public void toLogin() {
            LogUtil.e("changeSuccess");

        }

        @JavascriptInterface
        public void toLogin(Boolean isSuccess) {
            LogUtil.e("changeSuccess" + isSuccess);

        }


        @JavascriptInterface
        public void message(String json) {
            LogUtil.e("message == "+json);
            Type type = TypeToken.getParameterized(JsBean.class, JsPayBean.class).getType();
            JsBean<JsPayBean> jsBean = new Gson().fromJson(json, type);


//            Gson gson = new Gson();
//            JsBean jsBean = gson.fromJson(json, JsBean.class);
            if (jsBean.type == 1) {
                //登录
            }else if (jsBean.type == 2) {
                //退出
            }else if (jsBean.type == 3) {
                scan();
            }else if (jsBean.type == 4){
                //充值 {"type":4,"value":{"price":150,"payType":2,"source":1}}
                JsPayBean value = (JsPayBean) jsBean.value;
                TreeMap<String,String> map = new TreeMap<>();
                map.put("payType",value.getPayType());
                map.put("price",value.getPrice());
                map.put("source",value.getSource());
                recharge(map);
            }else if (jsBean.type == 5){
                //订单支付

            }


        }


    }


    private void scan() {
        Intent intent = new Intent(mActivity, CaptureActivity.class);
        ZxingConfig config = new ZxingConfig();
        config.setShake(true);//是否震动  默认为true
        config.setDecodeBarCode(true);//是否扫描条形码 默认为true
        config.setReactColor(R.color.baseColor);//设置扫描框四个角的颜色 默认为白色
        config.setFrameLineColor(R.color.white);//设置扫描框边框颜色 默认无色
        config.setScanLineColor(R.color.white);//设置扫描线的颜色 默认白色
        config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
        mActivity.startActivityForResult(intent, 1001);
    }


    private void recharge(TreeMap<String, String> map){
        new RxHttp<BaseResult<RechargeQuestResult>>().send(ApiManager.getService().recharge(map),
                new Response<BaseResult<RechargeQuestResult>>(mActivity) {
                    @Override
                    public void onSuccess(BaseResult<RechargeQuestResult> result) {


                    }

                });
    }
}
