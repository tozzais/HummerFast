package com.xianlv.business;

import android.webkit.JavascriptInterface;

import com.tozzais.baselibrary.util.log.LogUtil;
import com.xianlv.business.ui.AgreementWebViewActivity;

public class AppJs extends Object {
    private AgreementWebViewActivity h5Activity;
    public AppJs(AgreementWebViewActivity h5Activity) {
            this.h5Activity = h5Activity;
        }



    @JavascriptInterface
    public void toLogin(String data) {
        LogUtil.e(data);
        h5Activity.toLogin();

    }
    @JavascriptInterface
    public void toLogin() {
        LogUtil.e("changeSuccess");
        h5Activity.toLogin();

    }

    @JavascriptInterface
    public void toLogin(Boolean isSuccess) {
        LogUtil.e("changeSuccess"+isSuccess);
        h5Activity.toLogin();

    }


}
