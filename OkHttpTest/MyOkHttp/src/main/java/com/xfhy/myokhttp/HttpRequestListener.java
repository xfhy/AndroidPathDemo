package com.xfhy.myokhttp;

/**
 * Created by xfhy on 2019/7/18 22:02
 * Description : 网络成功与否的回调
 */
public interface HttpRequestListener<T> {

    void onSuccess(T t);

    void onFailure(String errorMsg);

}
