package com.xfhy.processdemo.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.xfhy.processdemo.Book;
import com.xfhy.processdemo.IBookManager;
import com.xfhy.processdemo.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by xfhy on 2019/4/12 20:56
 * Description :
 */
public class BookManagerService extends Service {

    private static final String TAG = "BookManagerService";
    //支持并发的
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);
    //支持并发读写
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            //添加
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            Log.e(TAG, "registerListener: 注册" + listener);
            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            Log.e(TAG, "registerListener: 解注册" + listener);
            mListenerList.unregister(listener);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        //搞点初始数据嘛
        mBookList.add(new Book(1, "Android"));
        mBookList.add(new Book(2, "Ios"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        //校验权限
        int check = checkCallingOrSelfPermission("com.xfhy.processdemo.permission.ACCESS_BOOK_SERVICE");
        if (check == PackageManager.PERMISSION_DENIED) {
            Log.e(TAG, "onBind: null");
            return null;
        }
        return mBinder;
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);

        /*
         * 对RemoteCallbackList的操作,比如有beginBroadcast和finishBroadcast才行,比如获取元素或者获取元素个数等
         * */
        Log.e(TAG, "run: 通知监听器");
        //1. beginBroadcast
        final int N = mListenerList.beginBroadcast();
        for (int i = 0; i < N; i++) {
            IOnNewBookArrivedListener broadcastItem = mListenerList.getBroadcastItem(i);
            if (broadcastItem != null) {
                //被调用的方法运行在客户端的binder线程池中
                broadcastItem.onNewBookArrived(book);
            }
        }
        //2. finishBroadcast  成对出现
        mListenerList.finishBroadcast();
    }

    /**
     * 这个线程很无聊  每隔5秒生产一个Book对象
     */
    private class ServiceWorker implements Runnable {
        @Override
        public void run() {
            while (!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size() + 1;
                Book newBook = new Book(bookId, "new Book#" + bookId);
                try {
                    //通知那些监听器
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
