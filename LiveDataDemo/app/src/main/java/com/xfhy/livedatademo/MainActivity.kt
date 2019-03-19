package com.xfhy.livedatademo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var liveData: MutableLiveData<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        liveData = MutableLiveData<String>()
        liveData.observe(this, Observer { newContent ->
            Toast.makeText(this@MainActivity, newContent, Toast.LENGTH_LONG).show()
        })

        mTestBtn.setOnClickListener {
            liveData.value = "测试,,,,"
        }
    }
}
