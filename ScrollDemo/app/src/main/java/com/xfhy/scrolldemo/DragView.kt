package com.xfhy.scrolldemo

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

/**
 * Created by 13195 on 2019/5/5 22:24
 * Description :
 */
class DragView : View {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var mLastX = 0f
    var mLastY = 0f

    var containerWidth = 0f
    var containerHeight = 0f

    init {
        //获取屏幕宽度
        val dm = resources.displayMetrics
        containerWidth = dm.widthPixels.toFloat()
        containerHeight = dm.heightPixels.toFloat()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.rawX ?: 0f
        val y = event?.rawY ?: 0f
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                //每次移动的距离
                val distanceX = x - mLastX
                val distanceY = y - mLastY
                //需要移动到的位置
                var nextX = getX() + distanceX
                var nextY = getY() + distanceY

                //-------边界限制-----
                //如果将要移动到的 x 轴坐标小于0,则等于0,防止移出容器左边
                if (nextX < 0) {
                    nextX = 0f
                }
                //防止移出容器右边
                if (nextX > containerWidth - measuredWidth) {
                    nextX = containerWidth - measuredWidth
                }
                //防止移出容器顶边
                if (nextY < 0) {
                    nextY = 0f
                }
                //防止移出容器底边
                if (nextY > containerHeight - measuredHeight) {
                    //这里还有个小问题,应该计算一下虚拟按键的高度,部分手机是开启了虚拟按键的
                    nextY = containerHeight - measuredHeight
                }

                //属性动画 设置x和y
                animate().setDuration(0).x(nextX).y(nextY).start()
            }
            MotionEvent.ACTION_UP -> {
            }
            else -> {
            }
        }
        mLastX = x
        mLastY = y
        return true
    }

}