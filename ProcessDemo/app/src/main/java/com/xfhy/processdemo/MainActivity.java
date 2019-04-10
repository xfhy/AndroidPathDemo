package com.xfhy.processdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xfhy.processdemo.file.FileCommunicate;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileCommunicate.persistToFile();

        UserManager.sUserId = 2;
        Log.w("xfhy", "MainActivity onCreate: " + UserManager.sUserId);
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}
