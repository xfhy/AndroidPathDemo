package com.xfhy.scrolldemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)


        btn_animation_scroll.setOnClickListener {
            //View动画
//            val loadAnimation = AnimationUtils.loadAnimation(applicationContext, R.anim.animation_scroll)
//            btn_animation_scroll.startAnimation(loadAnimation)
            //属性动画

            btn_animation_scroll.animate().translationX(200f).translationY(200f).start()
        }
    }
}
