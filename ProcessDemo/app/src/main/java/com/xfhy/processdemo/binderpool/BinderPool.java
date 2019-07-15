package com.xfhy.processdemo.binderpool;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.xfhy.processdemo.IBinderPool;

import java.util.concurrent.CountDownLatch;

/**
 * Created by xfhy on 2019/4/16 22:08
 * Description : Binder连接池  负责转发
 *
 * 只需要创建一个Service即可完成多个AIDL接口的工作
 *
 */
public class BinderPool {

    public static final int BINDER_NONE = -1;
    /**
     * 标识用于计算的aidl  ICompute
     */
    public static final int BINDER_COMPUTE = 0;
    /**
     * 标识用于加密的aidl  ISecurityCenter
     */
    public static final int BINDER_SECURITY_CENTER = 1;
    private static final String TAG = "BinderPool";
    private static volatile BinderPool sInstance;
    private Context mContext;
    /**
     * aidl 当前负责转发的
     */
    private IBinderPool mBinderPool;
    /**
     * CountDownLatch是通过一个计数器来实现的，计数器的初始值为线程的数量。每当一个线程完成了自己的任务后，计数器的值就会减1。
     * 当计数器值到达0时，它表示所有的线程已经完成了任务，然后在闭锁上等待的线程就可以恢复执行任务。
     */
    private CountDownLatch mConnectBinderPoolCountDownLatch;
    /**
     * 监听器,当服务端断开时 会回调binderDied()方法
     */
    private IBinder.DeathRecipient mBinderPoolDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e(TAG, "binder died.");
            //解绑
            mBinderPool.asBinder().unlinkToDeath(mBinderPoolDeathRecipient, 0);
            mBinderPool = null;

            //断开时又去开始连接
            connectBinderPoolService();
        }
    };
    private ServiceConnection mBinderPoolConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mBinderPool = IBinderPool.Stub.asInterface(iBinder);
            try {
                //加个监听器  当服务端断开时会收到通知
                mBinderPool.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //连接上了 这个数值-1
            mConnectBinderPoolCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    private BinderPool(Context context) {
        mContext = context.getApplicationContext();
        //连接服务端
        connectBinderPoolService();
    }

    /**
     * 连接服务端
     */
    private void connectBinderPoolService() {
        mConnectBinderPoolCountDownLatch = new CountDownLatch(1);
        Intent service = new Intent(mContext, BinderPoolService.class);
        mContext.bindService(service, mBinderPoolConnection, Context.BIND_AUTO_CREATE);

        try {
            //导致当前线程等到锁存器倒计数到零为止，除非该线程是{@linkplain Thread＃interrupt interrupted}。
            mConnectBinderPoolCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static BinderPool getInstance(Context context) {
        if (sInstance == null) {
            synchronized (BinderPool.class) {
                if (sInstance == null) {
                    sInstance = new BinderPool(context);
                }
            }
        }
        return sInstance;
    }

    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mBinderPool != null) {
                binder = mBinderPool.queryBinder(binderCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }

    public static class BinderPoolImpl extends IBinderPool.Stub {

        public BinderPoolImpl() {
            super();
        }

        @Override
        public IBinder queryBinder(int binderCode) throws RemoteException {
            IBinder binder = null;
            switch (binderCode) {
                case BINDER_SECURITY_CENTER: {
                    binder = new SecurityCenterImpl();
                    break;
                }
                case BINDER_COMPUTE: {
                    binder = new ComputeImpl();
                    break;
                }
                default:
                    break;
            }
            return binder;
        }
    }

}
