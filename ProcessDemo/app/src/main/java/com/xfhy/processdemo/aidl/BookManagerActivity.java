package com.xfhy.processdemo.aidl;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xfhy.processdemo.Book;
import com.xfhy.processdemo.IBookManager;
import com.xfhy.processdemo.IOnNewBookArrivedListener;
import com.xfhy.processdemo.R;

import java.util.List;

public class BookManagerActivity extends AppCompatActivity {

    private static final String TAG = "BookManagerActivity";
    private static final int MESSAGE_NEW_BOOK_ARRIVED = 1;
    private IBookManager mRemoteBookManager;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_BOOK_ARRIVED:
                    Log.e(TAG, "receive new book :" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };
    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            //eg:  onNewBookArrived: 当前线程Binder_2
            Log.e(TAG, "onNewBookArrived: 当前线程" + Thread.currentThread().getName());
            //这里是运行在客户端的Binder线程池中的   这里不能访问UI
            mHandler.obtainMessage(MESSAGE_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mRemoteBookManager = IBookManager.Stub.asInterface(iBinder);
            try {
                //这里其实应该放到子线程中去调用,因为这个方法可能很耗时
                List<Book> bookList = mRemoteBookManager.getBookList();
                Log.e(TAG, "query book list,list type:" + bookList.getClass().
                        getCanonicalName());
                Log.e(TAG, "query book list:" + bookList.toString());

                //这里的调用方法运行在服务端binder线程中
                mRemoteBookManager.addBook(new Book(3, "开发艺术探索"));
                Log.e(TAG, "add book: 3");
                List<Book> newList = mRemoteBookManager.getBookList();
                Log.i(TAG, "query book list:" + newList.toString());

                //注册监听器
                mRemoteBookManager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mRemoteBookManager = null;
            Log.e(TAG, "binder died." + Thread.currentThread().getName());
            //可以在这里重新连接Service
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manager);

        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        //如果还连接着  解除
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
            try {
                mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mServiceConnection);
        super.onDestroy();
    }
}
