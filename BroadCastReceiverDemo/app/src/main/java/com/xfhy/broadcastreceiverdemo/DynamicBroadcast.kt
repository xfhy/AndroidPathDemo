package com.xfhy.broadcastreceiverdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

/**
 * Created by xfhy on 2019/6/17 22:45
 * Description :
 */
class DynamicBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("xfhy", "动态广播   我收到消息了")

        //刚好在学习
    }
}
