package com.xfhy.processdemo.binderpool;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

@SuppressLint("Registered")
public class BinderPoolService extends Service {

    private static final String TAG = "BinderPoolService";
    private Binder mBinderPool = new BinderPool.BinderPoolImpl();

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind");
        return mBinderPool;
    }
}
