package com.xfhy.myokhttp;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;

/**
 * Created by xfhy on 2019/7/18 22:33
 * Description : 一个任务
 */
public class HttpTask<T, M> implements Runnable {

    private IHttpRequest mIHttpRequest;

    public HttpTask(String url, T requestData, Class<M> clazz, IHttpRequest iHttpRequest, HttpRequestListener listener) {
        if (iHttpRequest == null) {
            return;
        }
        mIHttpRequest = iHttpRequest;
        mIHttpRequest.setUrl(url);
        mIHttpRequest.setHttpRequestListener(listener);
        mIHttpRequest.setClazz(clazz);
        String content = JSON.toJSONString(requestData);
        mIHttpRequest.setRequestData(content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void run() {
        if (mIHttpRequest != null) {
            mIHttpRequest.execute();
        }
    }
}
