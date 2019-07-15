package com.xfhy.scrolldemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_layout_params.*

class LayoutParamsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_params)

        btn_layout_params.setOnClickListener {

            val layoutParams = btn_layout_params.layoutParams as? FrameLayout.LayoutParams
            layoutParams?.leftMargin = layoutParams?.leftMargin?.plus(100)
            //或者btn_layout_params.setLayoutParams(params)
            btn_layout_params.requestLayout()
        }
    }
}
