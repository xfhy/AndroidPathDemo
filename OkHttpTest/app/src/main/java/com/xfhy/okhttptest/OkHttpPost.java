package com.xfhy.okhttptest;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by feiyang on 2019-07-16 20:00
 * Description :
 */
public class OkHttpPost implements IHttpRequest {

    private static final String TAG = "OkHttpPost";
    public static final String URL = "https://api.github.com/markdown/raw";
    /**
     * 推荐让 OkHttpClient 保持单例，用同一个 OkHttpClient 实例来执行你的所有请求，因为每一个 OkHttpClient 实例都拥有自己的连接池和线程池，重用这些资源可以减少延时和节省资源，如果为每个请求创建一个 OkHttpClient
     * 实例，显然就是一种资源的浪费。
     */
    private OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor())
            .build();
    MediaType mMediaType = MediaType.parse("text/x-markdown; charset=utf-8");
    String requestBody = "I am Jdqm.";
    private final Request mRequest = new Request.Builder()
            .url(URL)
            .post(RequestBody.create(mMediaType, requestBody))
            .build();

    @Override
    public void request() {
        //每一个Call（其实现是RealCall）只能执行一次，否则会报异常
        mOkHttpClient.newCall(mRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.w(TAG, "onResponse: " + response.protocol() + " " + response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d(TAG, headers.name(i) + ":" + headers.value(i));
                }
                Log.w(TAG, "onResponse: " + response.body().string());
            }
        });
    }

    /**
     * 监听每个接口的时间 url
     */
    private class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //关键点1
            Request request = chain.request();

            long startTime = System.currentTimeMillis();
            Log.e(TAG, String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));

            //关键点2
            Response response = chain.proceed(request);

            long endTime = System.currentTimeMillis();
            Log.e(TAG, String.format("Received response for %s in %dms %n%s",
                    response.request().url(), (endTime - startTime), response.headers()));

            //关键点3
            return response;
        }
    }
}

/*
*
* 上面LoggingInterceptor监听的时间类似
* 2019-07-16 22:24:22.712 10694-10770/com.xfhy.okhttptest E/OkHttpPost: Received response for https://api.github.com/markdown/raw in 1219ms
    Date: Tue, 16 Jul 2019 14:24:23 GMT
    Content-Type: text/html;charset=utf-8
    Content-Length: 18
    Server: GitHub.com
    Status: 200 OK
    X-RateLimit-Limit: 60
    X-RateLimit-Remaining: 58
    X-RateLimit-Reset: 1563290663
    X-CommonMarker-Version: 0.20.0
    Access-Control-Expose-Headers: ETag, Link, Location, Retry-After, X-GitHub-OTP, X-RateLimit-Limit, X-RateLimit-Remaining, X-RateLimit-Reset, X-OAuth-Scopes, X-Accepted-OAuth-Scopes, X-Poll-Interval, X-GitHub-Media-Type
    Access-Control-Allow-Origin: *
    Strict-Transport-Security: max-age=31536000; includeSubdomains; preload
    X-Frame-Options: deny
    X-Content-Type-Options: nosniff
    X-XSS-Protection: 1; mode=block
    Referrer-Policy: origin-when-cross-origin, strict-origin-when-cross-origin
    Content-Security-Policy: default-src 'none'
    Vary: Accept-Encoding
    X-GitHub-Request-Id: 1D80:32FB:EE7429:13E1F30:5D2DDE16
* */
