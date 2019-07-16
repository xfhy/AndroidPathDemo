package com.xfhy.okhttptest;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by feiyang on 2019-07-16 20:00
 * Description :
 */
public class OkHttpPost implements IOkHttp {

    public static final String URL = "https://api.github.com/markdown/raw";
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private final Request mRequest = new Request.Builder().url(URL).build();
    MediaType mMediaType = MediaType.parse("text/x-markdown; charset=utf-8");

    @Override
    public void request() {

    }
}
