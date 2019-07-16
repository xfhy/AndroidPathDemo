package com.xfhy.okhttptest;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by feiyang on 2019-07-16 19:58
 * Description :
 */
public class OkHttpGet implements IHttpRequest {

    private static final String TAG = "OkHttpGet";

    public static final String URL = "https://www.baidu.com";
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private final Request mRequest = new Request.Builder().url(URL).build();

    @Override
    public void request() {
        mOkHttpClient.newCall(mRequest)
                //异步请求
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.w(TAG, "onResponse: " + response.body().string());
                    }
                });
    }

}
