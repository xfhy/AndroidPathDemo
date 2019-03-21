package com.xfhy.livedatatest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MutableLiveData<String> mStringLiveData = new MutableLiveData<>();
    private TextView mContentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContentTv = findViewById(R.id.tv_content);

        mStringLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String content) {
                mContentTv.setText(content);
            }
        });

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStringLiveData.postValue("新数据....");
            }
        });
    }
}
