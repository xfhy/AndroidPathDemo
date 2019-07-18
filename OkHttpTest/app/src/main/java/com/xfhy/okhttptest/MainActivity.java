package com.xfhy.okhttptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xfhy.myokhttp.HttpRequestListener;
import com.xfhy.myokhttp.HttpUtil;
import com.xfhy.okhttptest.test.Dictionary;
import com.xfhy.okhttptest.test.IHttpRequest;
import com.xfhy.okhttptest.test.OkHttpGet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpUtil.getRequest("http://open.iciba.com/dsapi/", null, Dictionary.class, new HttpRequestListener<Dictionary>() {
            @Override
            public void onSuccess(Dictionary dictionary) {
                Log.w(TAG, "onSuccess: " + dictionary.toString());
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });

        findViewById(R.id.btn_test).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        IHttpRequest okHttpGet = new OkHttpGet();
//        IOkHttp okHttpGet = new OkHttpPost();
        okHttpGet.request();
    }
}
