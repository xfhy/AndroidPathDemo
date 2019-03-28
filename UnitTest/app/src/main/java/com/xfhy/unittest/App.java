package com.xfhy.unittest;

import android.app.Application;
import android.content.Context;

/**
 * Created by xfhy on 2019/3/28 21:45
 * Description :
 */
public class App extends Application {

    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
