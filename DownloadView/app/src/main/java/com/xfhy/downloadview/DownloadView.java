package com.xfhy.downloadview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by feiyang on 2019/3/27 18:29
 * Description :
 */
public class DownloadView extends View implements Animator.AnimatorListener {

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
    private LinearInterpolator mLinearInterpolator = new LinearInterpolator();
    /**
     * 中间可以滑动的区域
     */
    private double mTopEffectiveSlidingWidth;
    private ValueAnimator mLeftToRightAnim;

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

        mLeftToRightAnim = ValueAnimator.ofFloat(0f, 500f, 1000f);
        mLeftToRightAnim.setInterpolator(mLinearInterpolator);
        mLeftToRightAnim.setDuration(10 * 1000);
        mLeftToRightAnim.addUpdateListener(mLeftToRightAnimListener);
        mLeftToRightAnim.addListener(this);
        mLeftToRightAnim.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        canvas.drawLine(mRadius, mRadius, mWidth - mRadius, mRadius, mBottomPaint);

        double slideWidth = mTopEffectiveSlidingWidth * mProgress + mHeight;
        //避免超出区域
        if (slideWidth + mRadius > mWidth) {
            canvas.drawLine(mRadius, mRadius, mWidth - mRadius, mRadius, mTopPaint);
        } else {
            canvas.drawLine(mRadius, mRadius, (float) slideWidth, mRadius, mTopPaint);
        }
       // Log.w(TAG, "onDraw:  slideWidth=" + slideWidth + "     mWidth=" + mWidth + "    mProgress=" + mProgress);
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
        mTopEffectiveSlidingWidth = (mWidth - mHeight) * 1.0 / 1000;
    }

    private ValueAnimator.AnimatorUpdateListener mLeftToRightAnimListener = new ValueAnimator.AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (float) animation.getAnimatedValue();
            mProgress = (int) value;
            if (mProgress % 10 == 0) {
                //回调  进度  x%
                Log.w(TAG, "progress: " + mProgress / 10);
            }
            invalidate();
        }
    };

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        //动画结束 了  记得回调
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
