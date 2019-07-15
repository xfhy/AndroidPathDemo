package com.xfhy.toast;

import android.app.Application;
import android.content.Context;

/**
 * Created by feiyang on 2019/3/19 17:35
 * Description :
 */
public class BaseApplication extends Application {

    private static Context sContext;

    public static Context getInstance() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
