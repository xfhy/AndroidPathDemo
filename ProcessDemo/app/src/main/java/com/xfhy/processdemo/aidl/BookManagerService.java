package com.xfhy.processdemo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
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
    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();

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
            if (!mListenerList.contains(listener)) {
                mListenerList.add(listener);
            } else {
                Log.e(TAG, "already exists.");
            }
            Log.e(TAG, "registerListener,size:" + mListenerList.size());
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (mListenerList.contains(listener)) {
                mListenerList.remove(listener);
                Log.e(TAG, "unregister listener succeed.");
            } else {
                Log.e(TAG, "not found,can not unregister.");
            }
            Log.d(TAG, "unregisterListener,current size:" + mListenerList.size());
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
        return mBinder;
    }

    private void onNewBookArrived(Book book) throws RemoteException {
        mBookList.add(book);
        Log.e(TAG, "onNewBookArrived,notify listeners:" + mListenerList.size());

        for (IOnNewBookArrivedListener iOnNewBookArrivedListener : mListenerList) {
            Log.e(TAG, "onNewBookArrived,notify listener:" + iOnNewBookArrivedListener);
            iOnNewBookArrivedListener.onNewBookArrived(book);
        }
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
