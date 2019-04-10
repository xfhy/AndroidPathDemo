package com.xfhy.processdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xfhy.processdemo.file.FileCommunicate;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.btn_read_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileCommunicate.recoverFromFile();
            }
        });
        /*Log.w("xfhy", "SecondActivity onCreate: " + UserManager.sUserId);
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);*/
    }
}
