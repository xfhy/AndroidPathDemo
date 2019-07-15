package com.xfhy.architecturedemo;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by xfhy on 2019/3/3 15:11
 * Description :
 */
public class MyApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        Stetho.initializeWithDefaults(this);
    }

    public static Context getContext() {
        return sContext;
    }
}
