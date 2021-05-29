package com.xianlv.business;

import android.app.Application;
import android.content.Context;

import com.tencent.bugly.crashreport.CrashReport;
import com.ycbjie.webviewlib.X5WebUtils;

/**
bug:
还没有写完的东西：

 常见问题接口

 */
public class MyApp extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        CrashReport.initCrashReport(getApplicationContext(), "7979473f34", true);

        mContext = this;

        X5WebUtils.init(this);
    }
}
