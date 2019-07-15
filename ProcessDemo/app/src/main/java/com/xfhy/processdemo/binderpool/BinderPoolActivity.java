package com.xfhy.processdemo.binderpool;

import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xfhy.processdemo.ICompute;
import com.xfhy.processdemo.ISecurityCenter;
import com.xfhy.processdemo.R;

public class BinderPoolActivity extends AppCompatActivity {

    private static final String TAG = "BinderPoolActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_pool);

        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
            }
        }).start();

    }

    private void doWork() {
        //别担心内存泄露,里面用的是ApplicationContext
        BinderPool binderPool = BinderPool.getInstance(this);
        IBinder securityBinder = binderPool.queryBinder(BinderPool.BINDER_SECURITY_CENTER);
        ISecurityCenter ISecurityCenter = SecurityCenterImpl.asInterface(securityBinder);
        Log.e(TAG, "visit ISecurityCenter");
        String msg = "helloworld-安卓";
        try {
            String password = ISecurityCenter.encrypt(msg);
            Log.e(TAG, "encrypt:" + password);
            Log.e(TAG, "decrypt:" + ISecurityCenter.decrypt(password));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        Log.e(TAG, "visit ICompute");
        IBinder computeBinder = binderPool.queryBinder(BinderPool.BINDER_COMPUTE);
        ICompute iCompute = ComputeImpl.asInterface(computeBinder);
        try {
            int result = iCompute.add(1, 2);
            Log.e(TAG, "1+2=" + result);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
