package com.xfhy.architecturedemo.life;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import com.xfhy.architecturedemo.common.BaseActivityPresenter;

/**
 * Created by xfhy on 2019/3/3 10:02
 * Description : 这是一个业务类，是可以感知到Activity的生命周期变化的
 *
 * 相当于我可以在Presenter中观察到Activity的生命周期，而不用自己去回调一下某个生命周期方法
 */
public class ActivityLifeObserver implements BaseActivityPresenter,LifecycleObserver {

    private static final String TAG = "ActivityLifeObserver";

    //  OnLifecycleEvent  对应了Activity的生命周期
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    @Override
    public void onStart() {
        Log.e(TAG, "onStart()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onResume() {
        Log.e(TAG, "onResume()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    @Override
    public void onPause() {
        Log.e(TAG, "onPause()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    @Override
    public void onStop() {
        Log.e(TAG, "onStop()");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy()");
    }
}
