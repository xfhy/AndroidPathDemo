package com.xfhy.servicestartdemo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.e("xfhy", "onServiceConnected")
            val myBinder = service as DestroyService.MyBinder
            myBinder.callbanzheng(100)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStartService.setOnClickListener {
            val intent = Intent(this, DestroyService::class.java)
            startService(intent)
            startService(intent)
        }

        btnBindService.setOnClickListener {
            val intent = Intent(this, DestroyService::class.java)
            bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
        }

        btnForeService.setOnClickListener {
            val intent = Intent(this, DestroyService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
                //需要在Service中调用Service.startForeground()
            }
        }

        unbindService.setOnClickListener {
            unbindService(mServiceConnection)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        //这里需要判断一下service是否已经解除绑定
        unbindService(mServiceConnection)
    }

}
