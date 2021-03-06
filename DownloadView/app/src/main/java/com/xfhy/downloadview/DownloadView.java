package com.xfhy.downloadview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by feiyang on 2019/3/27 18:29
 * Description : 下载中View
 * 画2根线,底部一根,上层一根
 */
public class DownloadView extends View implements Animator.AnimatorListener {

    private static final String TAG = "DownloadView";
    /**
     * 刻度  1000  刻度越小,越平滑,障眼法
     */
    private static final int SCALE = 1000;
    /**
     * 默认动画时长
     */
    private static final int DEFAULT_DURATION = 10 * 1000;
    /**
     * 底层画笔
     */
    private Paint mBottomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 上层画笔
     */
    private Paint mTopPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mTopColor = 0xFF119CED;
    private int mBottomColor = 0xFFE0E0E0;
    private int mWidth, mHeight, mRadius;
    private int mProgress = 0;
    private LinearInterpolator mLinearInterpolator = new LinearInterpolator();
    /**
     * 中间可以滑动的区域
     */
    private double mTopEffectiveSlidingWidth;
    private ValueAnimator mLeftToRightAnim;
    private boolean isAnimatorPause;
    private AnimProgressListener mAnimProgressListener;
    /**
     * 动画时长
     */
    private int mDuration = DEFAULT_DURATION;

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

        mLeftToRightAnim = ValueAnimator.ofFloat(0f, SCALE);
        mLeftToRightAnim.setInterpolator(mLinearInterpolator);
        mLeftToRightAnim.setDuration(mDuration);
        mLeftToRightAnim.addUpdateListener(mLeftToRightAnimListener);
        mLeftToRightAnim.addListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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
        mTopEffectiveSlidingWidth = (mWidth - mHeight) * 1.0 / SCALE;
    }

    private ValueAnimator.AnimatorUpdateListener mLeftToRightAnimListener = new ValueAnimator.AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (float) animation.getAnimatedValue();
            mProgress = (int) value;
            if (mProgress % 10 == 0) {
                //回调  进度  x%
                Log.w(TAG, "progress: " + mProgress / 10);
                if (mAnimProgressListener != null) {
                    mAnimProgressListener.progressUpdate(mProgress / 10);
                }
            }
            invalidate();
        }
    };

    /**
     * 设置动画时长
     */
    public void setDuration(int duration) {
        mDuration = duration;
        mLeftToRightAnim.setDuration(mDuration);
    }

    public void setTopColor(int topColor) {
        mTopColor = topColor;
    }

    public void setBottomColor(int bottomColor) {
        mBottomColor = bottomColor;
    }

    /**
     * 开始动画
     */
    public void start() {
        mLeftToRightAnim.start();
    }

    /**
     * 动画暂停
     */
    public void pause() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mLeftToRightAnim.pause();
        } else {
            if (!isAnimatorPause) {
                mLeftToRightAnim.cancel();
                isAnimatorPause = true;
            }
        }
    }

    /**
     * 动画继续
     */
    public void continueAnim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mLeftToRightAnim.resume();
        } else {
            //为minSDK 19以下做兼容
            mLeftToRightAnim.setFloatValues(mProgress, SCALE);
            mLeftToRightAnim.setDuration((mDuration / 1000) * (SCALE - mProgress));
            mLeftToRightAnim.start();
        }
    }

    /**
     * 直接到达终点 100%
     */
    public void stop() {
        mLeftToRightAnim.end();
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        //动画结束 了  记得回调
        Log.w(TAG, "onAnimationEnd: 动画结束");
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        Log.w(TAG, "onAnimationCancel: 动画取消");
    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    /**
     * 获取当前进度
     */
    public int getProgress() {
        return mProgress / 10;
    }

    public void setAnimProgressListener(AnimProgressListener animProgressListener) {
        mAnimProgressListener = animProgressListener;
    }

    public interface AnimProgressListener {
        /**
         * 进度更新
         */
        void progressUpdate(int progress);
    }

}
