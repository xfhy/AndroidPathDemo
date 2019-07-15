package com.xfhy.architecturedemo.livedata;

/**
 * Created by xfhy on 2019/3/3 13:22
 * Description :
 */
interface BaseViewModel<T> {

    T loadData();

    void clearData();

}
