package com.xfhy.processdemo.binderpool;

import android.os.RemoteException;

import com.xfhy.processdemo.ISecurityCenter;

/**
 * Created by xfhy on 2019/4/16 22:48
 * Description : aidl的实现
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {
    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
