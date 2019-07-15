package com.xfhy.processdemo.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xfhy.processdemo.R;
import com.xfhy.processdemo.config.MyConstants;

public class MessengerActivity extends AppCompatActivity {

    private static final String TAG = "MessengerActivity";
    private Messenger mServiceMessenger;
    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //1. 通过服务端返回的IBinder对象构建Messenger
            mServiceMessenger = new Messenger(iBinder);

            //2. 给服务端发送消息
            //搞一个Message 并且将what赋值成MSG_FROM_CLIENT
            Message msg = Message.obtain(null, MyConstants.MSG_FROM_SERVICE);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "hello,this is client.");
            msg.setData(bundle);
            msg.replyTo = mGetReplyMessenger;
            try {
                mServiceMessenger.send(msg);   //核心代码
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        //在super之前
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_SERVICE:
                    Log.e(TAG, "receive msg from Service:" + msg.getData().
                            getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }

}
