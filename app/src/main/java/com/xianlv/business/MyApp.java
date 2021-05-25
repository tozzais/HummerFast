package com.xianlv.business;

import android.app.Application;
import android.content.Context;

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

        mContext = this;

        X5WebUtils.init(this);
    }
}
