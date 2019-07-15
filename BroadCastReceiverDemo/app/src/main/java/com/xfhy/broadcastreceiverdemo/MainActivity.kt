package com.xfhy.broadcastreceiverdemo

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val dynamicBroadcast = DynamicBroadcast()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_static.setOnClickListener {
            //用Activity或者applicationContext都行
//            val intent = Intent(baseContext, StaticReceiver::class.java)
            val intent = Intent(applicationContext, StaticReceiver::class.java)
            intent.action = "com.xfhy.staticreceiver"
            sendBroadcast(intent)
        }

        btn_dynamic.setOnClickListener {
            val intentFilter = IntentFilter()
            registerReceiver(dynamicBroadcast, intentFilter)

            val intent = Intent(this, DynamicBroadcast::class.java)
            sendBroadcast(intent)
        }

    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(dynamicBroadcast)
    }

}
