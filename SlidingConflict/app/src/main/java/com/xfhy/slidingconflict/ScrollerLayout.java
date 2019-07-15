package com.xfhy.slidingconflict;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by feiyang on 2019-05-05 13:47
 * Description : 有点类似于ViewPager,
 */
public class ScrollerLayout extends ViewGroup {

    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;
    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;
    /**
     * 手机按下时的屏幕坐标
     */
    private float mXDown;
    /**
     * 手机当时所处的屏幕坐标
     */
    private float mXMove;
    /**
     * 上次出发ACTION_MOVE事件时的屏幕坐标
     */
    private float mXLastMove;
    /**
     * 界面可滚动的左右边界
     */
    private int mLeftBorder, mRightBorder;

    public ScrollerLayout(Context context) {
        this(context, null);
    }

    public ScrollerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        //创建Scroller的实例
        mScroller = new Scroller(getContext());
        //获取TouchSlop值 滑多远才算滑动
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                //为ScrollerLayout中的每一个子控件在水平方向上进行布局
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(),
                        childView.getMeasuredHeight());
            }
            //初始化坐标边界值
            mLeftBorder = getChildAt(0).getLeft();
            mRightBorder = getChildAt(getChildCount() - 1).getRight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        //判断是否为横向滑动,如果是 那么父控件就拦截此事件,自己用

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                //当手指拖动值大于TouchSlop值时,认为应该进行滚动,拦截子控件的事件
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrolledX = (int) (mXLastMove - mXMove);

                //-----越界 处理----------
                //getScrollX() : 返回的是当前View左边界的位置
                if (getScrollX() + scrolledX < mLeftBorder) {
                    scrollTo(mLeftBorder, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrolledX > mRightBorder) {
                    scrollTo(mRightBorder - getWidth(), 0);
                    return true;
                }

                //普通滑动->相当于当前位置开始滑动scrolledX距离
                scrollBy(scrolledX, 0);
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                //当手指抬起时,根据当前的滚动值来判定应该滚动到哪个子控件的界面
                //这个小算法只是用来计算当前应该滑动到哪个子控件的界面的
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                //dx是水平滚动距离   总的宽度-左边界的位置=需要滑动的距离. 因为Scroller是从左边界的位置开始滑动的
                int dx = targetIndex * getWidth() - getScrollX();
                //第二步,调用startScroll()方法来初始化滚动数据并刷新界面
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                //滚动,紧接着开始刷新界面
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        //第三步,重写computeScroll()方法,并在其内部完成平滑滚动的逻辑
        //当您想知道新位置时，请调用此方法。如果返回true，则表示动画尚未完成。
        if (mScroller.computeScrollOffset()) {
            //getCurrX(): 返回滚动中的当前X偏移量。
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //刷新界面 平滑滚动
            invalidate();
        }
    }
}
