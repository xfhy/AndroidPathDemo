package com.xfhy.toast.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import com.xfhy.toast.BaseApplication;


/**
 * Created by feiyang on 2018/4/18 11:29
 * Description : Toast工具类
 */
public final class ToastUtils {

    private static Handler handler = new Handler(Looper.getMainLooper());

    private static MyToast mToast = null;

    private static final Object synObj = new Object();

    public static void showMessage(final String msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    public static void showMessageLong(final String msg) {
        showMessage(msg, Toast.LENGTH_LONG);
    }

    public static void showMessage(final CharSequence msg, final int len) {
        if (msg == null || msg.equals("")) {
            return;
        }
        handler.post(new Runnable() {
            @SuppressLint("ShowToast")
            @Override
            public void run() {
                synchronized (synObj) { // 加上同步是为了每个toast只要有机会显示出来
                    if (mToast != null) {
                        mToast.setText(msg);
                        mToast.setDuration(len);
                    } else {
                        mToast = MyToast.makeText(BaseApplication.getInstance(), msg, len);
                        mToast.setGravity(Gravity.CENTER, 0, 0);
                    }
                    mToast.show();
                }
            }
        });
    }

}
