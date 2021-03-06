package com.xianlv.business.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tozzais.baselibrary.http.RxHttp;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.xianlv.business.R;
import com.xianlv.business.bean.JsBean;
import com.xianlv.business.bean.recharge.JsPayBean;
import com.xianlv.business.bean.recharge.RechargeQuestResult;
import com.xianlv.business.global.GlobalParam;
import com.xianlv.business.http.ApiManager;
import com.xianlv.business.http.BaseResult;
import com.xianlv.business.http.Response;
import com.xianlv.business.ui.activity.LoginActivity;
import com.xianlv.business.util.CenterDialogUtil;
import com.xianlv.business.util.OnGetStringListener;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.lang.reflect.Type;
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
        LogUtil.e("?????????url = "+url);

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
        tv_title.setText("????????????");

        web_view.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                if (GlobalParam.getLoginBean() != null){
                    LogUtil.e("??????H5?????????"+GlobalParam.getLoginBean().token);
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
                //??????
                showLoginDialog();
            }else if (jsBean.type == 2) {
                //??????
            }else if (jsBean.type == 3) {
                scan();
            }else if (jsBean.type == 4){
                //?????? {"type":4,"value":{"price":150,"payType":2,"source":1}}
                JsPayBean value = (JsPayBean) jsBean.value;
                TreeMap<String,String> map = new TreeMap<>();
                map.put("payType",value.getPayType());
                map.put("price",value.getPrice());
                map.put("source",value.getSource());
                recharge(map);
            }else if (jsBean.type == 5){
                //????????????

            }


        }


    }


    private void scan() {
        Intent intent = new Intent(mActivity, CaptureActivity.class);
        ZxingConfig config = new ZxingConfig();
        config.setShake(true);//????????????  ?????????true
        config.setDecodeBarCode(true);//????????????????????? ?????????true
        config.setReactColor(R.color.baseColor);//????????????????????????????????? ???????????????
        config.setFrameLineColor(R.color.white);//??????????????????????????? ????????????
        config.setScanLineColor(R.color.white);//???????????????????????? ????????????
        config.setFullScreenScan(false);//??????????????????  ?????????true  ??????false??????????????????????????????
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


    private void showLoginDialog(){
        CenterDialogUtil.show(getContext(), "????????????", "?????????????????????????????????", new OnGetStringListener() {
            @Override
            public void getString(String s) {
                if (s.equals("1")){
                    LoginActivity.launch1(mActivity);
                }
            }
        });
    }

    public void onload() {

        if (GlobalParam.getLoginBean() != null){
            LogUtil.e("??????H5?????????"+GlobalParam.getLoginBean().token);
            web_view.loadUrl("javascript:userLogin(\"" + GlobalParam.getLoginBean().token + "\")");
        }
    }
}
