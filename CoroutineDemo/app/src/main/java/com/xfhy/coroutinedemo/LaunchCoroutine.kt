package com.xfhy.coroutinedemo

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * Created by 13195 on 2019/7/8 23:47
 * Description :
 */

private val mOkHttpClient = OkHttpClient()
private val mRequest = Request.Builder().url("https://www.baidu.com/").get().build()
/*
fun displayDashboard(textview: TextView) = runBlocking {
    //在主线程中启动协程
    launch(Dispatchers.Main) {
        val job = async(Dispatchers.IO) {
            //这里不考虑异常情况
            Log.e("xfhy", "执行async")
            mOkHttpClient.newCall(mRequest).execute().body?.string()
        }
        val await = job.await()
        textview.text = await
        Log.e("xfhy", "result = $await")
    }

}*/

fun displayDashboard(textview: TextView) {

    GlobalScope.launch {
        //发起网络请求
        val result = mOkHttpClient.newCall(mRequest).execute().body?.string()

        //这里是子线程哦
        Log.e("xfhy", "ThreadName = ${Thread.currentThread().name} time = ${System.currentTimeMillis()}")

        //阻塞的时候让出CPU 不会阻塞主线程
        withContext(Dispatchers.Main) {
            //回到主线程
            textview.text = result
        }
    }
}


class AndroidCommonPool : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        //AsyncTask已经给我们准备好了线程池的,利用起来   去线程池执行block
        AsyncTask.THREAD_POOL_EXECUTOR.execute(block)
    }
}
