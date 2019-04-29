package com.xfhy.recyclerviewdemo.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by feiyang on 2019-04-29 10:45
 * Description : 垂直的列表 或者 水平的列表
 */
public class DividerListItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 全局的style,分割线
     */
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    /**
     * 分割线
     */
    private Drawable mDivider;
    /**
     * 方向
     */
    private int mOrientation;

    public DividerListItemDecoration(Context context, int orientation) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        setOrientation(orientation);
    }

    public DividerListItemDecoration(Context context, int orientation, int drawableId) {
        mDivider = ContextCompat.getDrawable(context, drawableId);
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        int paddingLeft = parent.getPaddingLeft();
        int paddingRight = parent.getPaddingRight();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            //线的顶部=View相对于父视图的底部+View的bottomMargin
            int divideTop = child.getBottom() + layoutParams.bottomMargin;
            //线的底部其实就是线的顶部+线的高度
            int divideBottom = divideTop + mDivider.getIntrinsicHeight();

            //设置边界 画出来
            mDivider.setBounds(paddingLeft, divideTop, paddingRight, divideBottom);
            mDivider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        int paddingBottom = parent.getPaddingBottom();
        int paddingTop = parent.getPaddingTop();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int divideLeft = child.getRight() + layoutParams.getMarginEnd();
            int divideRight = divideLeft + mDivider.getIntrinsicWidth();

            //设置边界  画出来
            mDivider.setBounds(divideLeft, paddingTop, divideRight, paddingBottom);
            mDivider.draw(c);
        }
    }

    //设置条目周边的偏移量 填充
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
