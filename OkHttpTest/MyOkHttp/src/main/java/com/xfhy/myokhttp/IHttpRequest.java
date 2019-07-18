package com.xfhy.myokhttp;

/**
 * Created by xfhy on 2019/7/18 22:01
 * Description : 不同的请求都基于这个规则   GET POST
 */
public interface IHttpRequest {

    void setUrl(String url);

    /**
     * 比如post时的body
     */
    void setRequestData(byte[] requestData);

    /**
     * 设置网络请求回调
     */
    void setHttpRequestListener(HttpRequestListener listener);

    /**
     * 执行请求
     */
    void execute();

    /**
     * 设置返回值类型
     */
    <M> void setClazz(Class<M> clazz);
}
