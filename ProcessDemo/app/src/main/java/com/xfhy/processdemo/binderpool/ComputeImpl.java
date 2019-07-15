package com.xfhy.processdemo.binderpool;

import android.os.RemoteException;

import com.xfhy.processdemo.ICompute;

/**
 * Created by xfhy on 2019/4/16 22:02
 * Description :  aidl的实现
 */
public class ComputeImpl extends ICompute.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
