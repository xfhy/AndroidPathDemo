package com.xfhy.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mBtnRequest.setOnClickListener {
            //testCoroutine()
//            startCoroutine()
//            startCoroutineOnUi()
            displayDashboard(mContentTv)
        }

        mBtnReset.setOnClickListener {
            mContentTv.text = ""
        }
    }

    private fun startCoroutineOnUi() {

        //在主线程中启动协程
        GlobalScope.launch(Dispatchers.Main) {
            Log.e("xfhy", "在主线程中启动协程 ${Thread.currentThread().name}")
        }
    }

    private fun testCoroutine() {

        runBlocking {
            /*val job = launch {
                //这里也是主线程
                Log.e("xfhy", "threadName=${Thread.currentThread().name}")
                repeat(1000) { i ->
                    Log.e("xfhy", "挂其中 $i.....")
                    //等待500毫秒
                    delay(500)
                }
            }*/

            val job2 = async {
                delay(500)
                return@async "hello"
            }
            val await = job2.await()
            Log.e("xfhy", "await result = $await")

            //这里是阻塞主线程1300毫秒
            //delay(1300)
            Log.e("xfhy", "主线程等待中")
            /*job.cancel()
            job.join()*/
            Log.e("xfhy", "即将完成退出")
        }
    }
}
