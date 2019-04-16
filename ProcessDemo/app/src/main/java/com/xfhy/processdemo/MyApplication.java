package com.xfhy.processdemo;

import android.app.Application;
import android.os.Process;
import android.util.Log;

/**
 * Created by xfhy on 2019/4/8 23:00
 * Description :
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e(TAG, "onCreate: " + Process.myPid());
    }
}
