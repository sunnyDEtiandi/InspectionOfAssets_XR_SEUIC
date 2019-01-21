package com.xiangrong.yunyang.inspectionofassets.app;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * 作者    yunyang
 * 时间    2019/1/21 9:06
 * 文件    InspectionOfAssets
 * 描述   全局Application
 */
public class App extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        LitePal.initialize(this);
    }

    public static Context getContext() {
        return sContext;
    }

}
