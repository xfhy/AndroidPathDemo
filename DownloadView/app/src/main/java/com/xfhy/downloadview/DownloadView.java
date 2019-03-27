package com.xfhy.downloadview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by feiyang on 2019/3/27 18:29
 * Description :
 */
public class DownloadView extends View {

    private static final String TAG = "DownloadView";
    /**
     * 底层画笔
     */
    private Paint mBottomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 上层画笔
     */
    private Paint mTopPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mTopColor = 0xFF119CED;
    private int mBottomColor = 0xFF999999;
    private int mWidth, mHeight, mRadius;
    private int mProgress = 0;
    private int mDelay = 100;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mProgress++;
            invalidate();
            if (mProgress == 100) {
                mHandler.removeCallbacks(mNextRunnable);
            } else {
                mHandler.postDelayed(mNextRunnable, mDelay);
            }
        }
    };

    private Runnable mNextRunnable = new Runnable() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
        }
    };
    private int mTopEffectiveSlidingWidth;

    public DownloadView(Context context) {
        this(context, null);
    }

    public DownloadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownloadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mTopPaint.setColor(mTopColor);
        mTopPaint.setStyle(Paint.Style.FILL);
        mTopPaint.setStrokeCap(Paint.Cap.ROUND);

        mBottomPaint.setColor(mBottomColor);
        mBottomPaint.setStyle(Paint.Style.FILL);
        mBottomPaint.setStrokeCap(Paint.Cap.ROUND);
        start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        canvas.drawLine(mRadius, mRadius, mWidth - mRadius, mRadius, mBottomPaint);

        int slideWidth = mTopEffectiveSlidingWidth * mProgress + mHeight;
        //避免超出区域
        if (slideWidth + mRadius > mWidth) {
            canvas.drawLine(mRadius, mRadius, mWidth - mRadius, mRadius, mTopPaint);
        } else {
            canvas.drawLine(mRadius, mRadius, slideWidth, mRadius, mTopPaint);
        }
        Log.w(TAG, "onDraw:  slideWidth=" + slideWidth + "     mWidth=" + mWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        initData();
    }

    private void initData() {
        mTopPaint.setStrokeWidth(mHeight);
        mBottomPaint.setStrokeWidth(mHeight);
        mRadius = mHeight / 2;
        mTopEffectiveSlidingWidth = (mWidth - mHeight) / 100;
    }

    public void start() {
        mHandler.sendEmptyMessage(0);
    }

}
