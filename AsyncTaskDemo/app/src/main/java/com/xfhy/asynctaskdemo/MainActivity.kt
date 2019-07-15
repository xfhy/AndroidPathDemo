package com.xfhy.asynctaskdemo

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //有规律地并行执行的
        /*
        2019-07-02 23:15:10.884 7677-7677/com.xfhy.asynctaskdemo E/xfhy: async1 execute finish at 2019-07-02 23:15:10
        2019-07-02 23:15:11.883 7677-7677/com.xfhy.asynctaskdemo E/xfhy: async2 execute finish at 2019-07-02 23:15:11
        2019-07-02 23:15:12.893 7677-7677/com.xfhy.asynctaskdemo E/xfhy: async3 execute finish at 2019-07-02 23:15:12
        2019-07-02 23:15:13.898 7677-7677/com.xfhy.asynctaskdemo E/xfhy: async4 execute finish at 2019-07-02 23:15:13
        2019-07-02 23:15:14.900 7677-7677/com.xfhy.asynctaskdemo E/xfhy: async5 execute finish at 2019-07-02 23:15:14
        2019-07-02 23:15:15.901 7677-7677/com.xfhy.asynctaskdemo E/xfhy: async6 execute finish at 2019-07-02 23:15:15
        2019-07-02 23:15:16.903 7677-7677/com.xfhy.asynctaskdemo E/xfhy: async7 execute finish at 2019-07-02 23:15:16
        * */
        MyAsyncTask("async1").execute("")
        MyAsyncTask("async2").execute("")
        MyAsyncTask("async3").execute("")
        MyAsyncTask("async4").execute("")
        MyAsyncTask("async5").execute("")
        MyAsyncTask("async6").execute("")
        MyAsyncTask("async7").execute("")
    }

    private class MyAsyncTask(val name: String) : AsyncTask<String, Int, String>() {
        override fun doInBackground(vararg params: String?): String {
            Thread.sleep(1000)
            return name
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            Log.e("xfhy", "$result execute finish at ${simpleDateFormat.format(Date())}")
        }

    }

}
