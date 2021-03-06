package com.xianlv.business.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tozzais.baselibrary.ui.BaseActivity;
import com.tozzais.baselibrary.util.ClickUtils;
import com.tozzais.baselibrary.util.NetworkUtil;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.xianlv.business.AppJs;
import com.xianlv.business.R;
import com.xianlv.business.global.GlobalParam;
import com.ycbjie.webviewlib.X5WebView;

import butterknife.BindView;
import butterknife.OnClick;
import me.jingbin.progress.WebProgress;


/**
 蜂鸟速充安卓对接
 一、Html传值给安卓调用方法：window.htmlMessage.message(JSON.stringify(message));
 message = {
 type: 1, //类型
 value: ‘’//值为泛型
 }
 type:
 1.登录
 2.退出登录
 3.扫码充电

 二、安卓调取h5方法
 登录：userLogin(token)
 退出登录：userLogout()

 三、加载页面 https://fn.hanyu365.com.cn/park/h5/index.html#/pages/index/index?route=&longitude=&latitude&city=&atOpenStatus=&value=
 1.非必填
 longitude：当前城市纬度
 latitude：当前城市经度
 city：当前城市名称
 atOpenStatus：当前城市是否开通
 2必填
 route：页面名称
 value：页面需要参数 为字典形式，需转成json字符串，如页面需要id参数，则value 为 {id:0} 的json字符串
 route值及value如下：
 topup：底部菜单余额充值
 scan：底部菜单扫码充电
 activity：底部菜单优惠活动
 chargeInfo：充电桩详情，value中id为充电桩id
 mineInfo：个人资料
 car：我的车辆
 order：我的订单
 invoice：我的发票
 wallet：我的钱包
 coupon：我的优惠券
 collect：我的收藏
 message：我的消息
 html：关于我们，value中的 value为3、text为3、title为 关于我们
 start：开始充电，value中的code为终端编号
 */
public class AgreementWebViewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    X5WebView web_view;
    @BindView(R.id.progress)
    WebProgress mProgress;
    @BindView(R.id.title)
    TextView title;


    public static void launch(Context from, String type) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, AgreementWebViewActivity.class);
        intent.putExtra("type", type);
        from.startActivity(intent);
    }

    public static void launch(Context from, String type, String title) {
        if (!ClickUtils.isFastClick()) {
            return;
        }
        Intent intent = new Intent(from, AgreementWebViewActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("title", title);
        from.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected int getToolbarLayout() {
        return R.layout.layout_header;
    }

    @Override
    public void initView(Bundle savedInstanceState) {


    }

    @Override
    public void loadData() {


        String url = getIntent().getStringExtra("type");
        if (GlobalParam.getLoginBean() != null){
            url = url +"&token="+GlobalParam.getLoginBean().token;
        }
        LogUtil.e("加载的url = "+url);
        web_view.loadUrl(url);


        web_view.setWebChromeClient(new WebChrome());
        //显示进度条
        mProgress.show();
        mProgress.setColor(getResources().getColor(R.color.baseColor));

        WebSettings settings = web_view.getSettings();
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        web_view.addJavascriptInterface(new AppJs(this), "message");

        settings.setDomStorageEnabled(true);
        settings.setAppCacheMaxSize(1024*1024*8);
        String appCachePath = mActivity.getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setGeolocationEnabled(true);

        //提高渲染的优先级
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //使把图片加载放在最后来加载渲染
        settings.setBlockNetworkImage(true);
        //开启H5(APPCache)缓存功能

        settings.setAppCacheEnabled(true);
        //开启 DOM storage 功能

        settings.setDomStorageEnabled(true);
        //应用可以有数据库

        settings.setDatabaseEnabled(true);
        //根据网络连接情况，设置缓存模式，
        if (NetworkUtil.isNetworkAvailable(this)) {
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);// 根据cache-control决定是否从网络上取数据
        } else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 先查找缓存，没有的情况下从网络获取。
        }
        //可以读取文件缓存(manifest生效)
        settings.setAllowFileAccess(true);

    }

    public void toLogin() {
        Intent intent = getPackageManager().getLaunchIntentForPackage("com.cinderellavip.store");
        if (intent != null) {
            startActivity(intent);
        } else {
//            tsg("");
        }

    }

    @Override
    public void initListener() {

    }

    @OnClick({R.id.iv_left_back, R.id.iv_left_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left_back:
                if (web_view.canGoBack()) {
                    web_view.goBack();
                } else {
                    finish();
                }
                break;
            case R.id.iv_left_close:
                finish();
                break;
        }
    }

    public class WebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            mProgress.setProgress(i);
            String title1 = getIntent().getStringExtra("title");
            if (i == 100) {
                title.setText(TextUtils.isEmpty(title1) ? webView.getTitle() : title1);
//                if (GlobalParam.getLoginBean() != null) {
//                    LogUtil.e("调用H5加载了" + GlobalParam.getLoginBean().token);
//                    web_view.loadUrl("javascript:userLogin(\"" + GlobalParam.getLoginBean().token + "\")");
//                }
            }

        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
            AgreementWebViewActivity.this.valueCallback = valueCallback;
            selectImage();
            return true;
        }
    }

    //全局声明，用于记录选择图片返回的地址值
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> valueCallback;

    private void selectImage() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), 15);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (web_view != null) {
            web_view.getSettings().setJavaScriptEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (web_view != null) {
            web_view.getSettings().setJavaScriptEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {
            web_view.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 15) {
            if (null == uploadMessage && null == valueCallback) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (valueCallback != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != 15 || valueCallback == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        valueCallback.onReceiveValue(results);
        valueCallback = null;
    }
}
