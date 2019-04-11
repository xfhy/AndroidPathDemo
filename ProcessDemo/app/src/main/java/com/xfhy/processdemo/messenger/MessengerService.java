package com.xfhy.processdemo.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import com.xfhy.processdemo.config.MyConstants;

/**
 * Created by xfhy on 2019/4/11 21:39
 * Description :Messenger的服务端  其实就是创建一个Messenger,返回里面的Binder
 */
public class MessengerService extends Service {

    private static final String TAG = "MessengerService";

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_CLIENT:
                    Log.w(TAG, "receive msg from Client:" + msg.getData().
                            getString("msg"));
                    Message relpyMessage = Message.obtain(null, MyConstants.MSG_FROM_SERVICE);

                    Messenger clientMessenger = relpyMessage.replyTo;
                    Bundle bundle = new Bundle();
                    bundle.putString("reply","嗯，你的消息我已经收到，稍后会回复你。");
                    relpyMessage.setData(bundle);

                    try {
                        clientMessenger.send(relpyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }
}
