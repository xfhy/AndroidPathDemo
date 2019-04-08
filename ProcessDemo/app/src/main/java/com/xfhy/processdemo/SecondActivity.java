package com.xfhy.processdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.w("xfhy", "SecondActivity onCreate: " + UserManager.sUserId);
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }
}
