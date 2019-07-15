package com.xfhy.scrolldemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class ScrollToActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_scroll_to.setOnClickListener {
            //向右下 滑动  只能滑动LinearLayout里面的内容,不能滑动LinearLayout
            //LinearLayout里面的内容相对于左上角的绝对滑动
            ll_root_view.scrollTo(-60, -100)
        }

        btn_scroll_by.setOnClickListener {
            //LinearLayout里面的内容相对于当前的位置进行滑动,右下
            ll_root_view.scrollBy(-60, -100)
        }

    }
}
