package com.xianlv.business.ui;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import androidx.recyclerview.widget.RecyclerView;

import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tozzais.baselibrary.ui.BaseFragment;
import com.tozzais.baselibrary.util.log.LogUtil;
import com.xianlv.business.AppJs;
import com.xianlv.business.R;
import com.xianlv.business.global.GlobalParam;

import butterknife.BindView;


public class ChargeFragment extends BaseFragment  {




    @BindView(R.id.web_view)
    WebView web_view;


    public static ChargeFragment newInstance(String url){
        ChargeFragment balanceFragment = new ChargeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
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

        web_view.loadUrl(url);


        web_view.setWebChromeClient(new WebChrome());
        WebSettings settings = web_view.getSettings();
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        web_view.addJavascriptInterface(new AppJs(this), "toLogin");
        if (GlobalParam.getLoginBean() != null){
            LogUtil.e("调用H5加载了1111111111");
            web_view.loadUrl("javascript:userLogin(\""+ GlobalParam.getLoginBean().token +"\")");
        }






    }

    public class WebChrome extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            LogUtil.e("调用H5加载了"+i);
            if (i == 100){
                LogUtil.e("调用H5加载了");
                if (GlobalParam.getLoginBean() != null)
                web_view.loadUrl("javascript:userLogin(\""+ GlobalParam.getLoginBean().token +"\")");
            }
//            mProgress.setProgress(i);
//            String title1 = getIntent().getStringExtra("title");
//            if (i == 100){
//                title.setText(TextUtils.isEmpty(title1)?webView.getTitle():title1);
//            }

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



}
