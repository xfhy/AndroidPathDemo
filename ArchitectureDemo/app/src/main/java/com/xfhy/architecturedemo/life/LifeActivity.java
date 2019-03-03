package com.xfhy.architecturedemo.life;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xfhy.architecturedemo.R;

/**
 * 可供观察生命周期的Activity  需要实现LifecycleOwner
 *
 *
 */
public class LifeActivity extends AppCompatActivity implements LifecycleOwner {

    private static final String TAG = "LifeActivity";
    private LifecycleRegistry mLifecycleRegistry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);

        mLifecycleRegistry = new LifecycleRegistry(this);

        //注册需要监听的Observer
        mLifecycleRegistry.addObserver(new ActivityLifeObserver());
    }

    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }
}
