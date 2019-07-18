package com.xfhy.myokhttp;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xfhy on 2019/7/18 22:17
 * Description : 用于json的执行体
 */
public class JsonHttpRequest implements IHttpRequest {

    private String mUrl;
    /**
     * get 请求时用不到
     */
    private byte[] mRequestData;
    private HttpRequestListener mHttpRequestListener;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Class clazz;

    @Override
    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        mRequestData = requestData;
    }

    @Override
    public void setHttpRequestListener(HttpRequestListener listener) {
        mHttpRequestListener = listener;
    }

    @Override
    public <V> void setClazz(Class<V> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void execute() {
        //这里用于请求网络

        URL url = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            url = new URL(mUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            String result = inputStreamToString(inputStream);
            final Object o = JSON.parseObject(result, clazz);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //切换到主线程   返回数据出去
                    mHttpRequestListener.onSuccess(o);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                    httpURLConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    private String inputStreamToString(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        String line = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while (true) {
            try {
                if ((line = br.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            sb.append(line);
        }
        return sb.toString();
    }

}
