package com.xianlv.business;

import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;
import com.ycbjie.webviewlib.X5WebUtils;

import cn.jpush.android.api.JPushInterface;

/**


 */
public class MyApp extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(getApplicationContext(), "7979473f34", true);

        mContext = this;

        X5WebUtils.init(this);


        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
