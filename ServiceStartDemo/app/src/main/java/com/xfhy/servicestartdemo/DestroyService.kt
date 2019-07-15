package com.xfhy.servicestartdemo

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.widget.Toast

class DestroyService : Service() {

    inner class MyBinder : Binder() {
        fun callbanzheng(money: Int) {
            banzheng(money)
        }
    }

    private fun banzheng(money: Int) {
        if (money > 100) {
            Toast.makeText(applicationContext, "我是领导,把证给你办了", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(applicationContext, "就这点儿钱,还想办事", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onBind(intent: Intent): IBinder {
        Log.e("xfhy", "onBind")
        return MyBinder()
    }

    override fun onCreate() {
        super.onCreate()
        Log.e("xfhy", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("xfhy", "onStartCommand")

        /*//开启线程  5秒后停止Service
        Thread {
            Thread.sleep(5000)
            //停止
            stopSelf()
        }.start()*/

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("xfhy", "onDestroy")
    }

}
