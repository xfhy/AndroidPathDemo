package com.xfhy.architecturedemo.common;

/**
 * Created by xfhy on 2019/3/3 10:02
 * Description :
 */
public interface BaseActivityPresenter {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

}
