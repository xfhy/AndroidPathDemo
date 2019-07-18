package com.xfhy.myokhttp;

/**
 * Created by xfhy on 2019/7/18 22:29
 * Description :  便于请求网络的工具类
 */
public class HttpUtil {

    public static <T, M> void getRequest(String url, T requestData, Class<M> clazz, final HttpRequestListener listener) {
        JsonHttpRequest jsonHttpRequest = new JsonHttpRequest();
        HttpTask<T, M> httpTask = new HttpTask<>(url, requestData, clazz, jsonHttpRequest, listener);
        ThreadPoolManager.getInstance().addTask(httpTask);
    }

}
