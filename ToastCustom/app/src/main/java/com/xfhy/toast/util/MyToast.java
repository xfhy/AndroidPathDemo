package com.xfhy.toast.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xfhy.toast.R;

/**
 * Created by feiyang on 2019/3/19 17:46
 * Description :
 */
public class MyToast {

    private Toast mToast;
    private final View mRootView;
    private final TextView mTextView;

    private MyToast(Context context, CharSequence text, int duration) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        mTextView = mRootView.findViewById(R.id.tv_toast_content);
        mTextView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(mRootView);
    }


    public static MyToast makeText(Context context, CharSequence text, int duration) {
        return new MyToast(context, text, duration);
    }

    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }

    public void setText(CharSequence msg) {
        if (mTextView != null) {
            mTextView.setText(msg);
        }
    }

    public void setDuration(int len) {
        mToast.setDuration(len);
    }
}
