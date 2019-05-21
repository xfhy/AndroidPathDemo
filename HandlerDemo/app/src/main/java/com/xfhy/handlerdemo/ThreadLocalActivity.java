package com.xfhy.handlerdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

public class ThreadLocalActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    static final ThreadLocal<Integer> INTEGER_THREAD_LOCAL = new ThreadLocal<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Looper looper = Looper.myLooper();
        Looper mainLooper = Looper.getMainLooper();
        Log.w(TAG, "onCreate: looper=" + looper + "   mainLooper=" + mainLooper);

        INTEGER_THREAD_LOCAL.set(1);
        Log.w(TAG, "主线程 " + INTEGER_THREAD_LOCAL.get() + "       " + INTEGER_THREAD_LOCAL);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Integer integer = INTEGER_THREAD_LOCAL.get();
                Log.w(TAG, "另外的线程 run: " + integer + "           " + INTEGER_THREAD_LOCAL);
            }
        }).start();

    }
}
