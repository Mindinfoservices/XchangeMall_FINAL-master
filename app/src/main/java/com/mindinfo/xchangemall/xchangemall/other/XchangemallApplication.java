package com.mindinfo.xchangemall.xchangemall.other;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


public class XchangemallApplication extends MultiDexApplication {
    private static XchangemallApplication instance;
    private Context applicationContext;

    public static XchangemallApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        instance = this;
        applicationContext = this;


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
