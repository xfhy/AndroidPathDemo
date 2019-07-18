package com.xfhy.myokhttp;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by xfhy on 2019/7/18 21:52
 * Description :
 * <p>
 * 1. 封装url,requestData,callBackListener,执行    相当于是一个执行体
 * 2. 封装线程池,队列,核心线程(用于死循环取队列中的执行体)
 * 3. 外界将执行体传入  然后就可以将接口返回数据给返回出去
 */
public class ThreadPoolManager {

    private LinkedBlockingQueue<Runnable> mLinkedBlockingQueue = new LinkedBlockingQueue<>();
    private ThreadPoolExecutor mThreadPoolExecutor = new ThreadPoolExecutor(3, 10, 60, TimeUnit.SECONDS,
            new ArrayBlockingQueue<Runnable>(4),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    addTask(r);
                }
            });

    private ThreadPoolManager() {
        //一开始就开始执行核心线程,然后再利用核心线程循环取队列中的任务,一旦有则放线程中去执行
        mThreadPoolExecutor.execute(mCorePool);
    }

    private static class SingleHolder {
        private static final ThreadPoolManager THREAD_POOL_MANAGER = new ThreadPoolManager();
    }

    public static ThreadPoolManager getInstance() {
        return SingleHolder.THREAD_POOL_MANAGER;
    }

    public void addTask(Runnable runnable) {
        if (runnable != null) {
            mLinkedBlockingQueue.offer(runnable);
        }
    }

    private Runnable mCorePool = new Runnable() {
        private Runnable privateRun;

        @Override
        public void run() {
            try {
                privateRun = mLinkedBlockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mThreadPoolExecutor.execute(privateRun);
        }
    };

}
