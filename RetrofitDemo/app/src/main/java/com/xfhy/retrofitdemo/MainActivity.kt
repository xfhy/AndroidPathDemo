package com.xfhy.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val mRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())   //数据解析器
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnRequest.setOnClickListener {
            val iArticleApi = mRetrofit.create(IArticleApi::class.java)
            val airticlesCall = iArticleApi.getAirticles("408", "1")
            airticlesCall.enqueue(object : Callback<BaseData> {
                override fun onFailure(call: Call<BaseData>, t: Throwable) {
                    t.printStackTrace()
                    Log.e("xfhy", "请求失败")
                }

                override fun onResponse(call: Call<BaseData>, response: Response<BaseData>) {
                    val body = response.body()
                    Log.e("xfhy", "请求成功 ${body?.toString()}")
                }
            })
        }

    }
}
