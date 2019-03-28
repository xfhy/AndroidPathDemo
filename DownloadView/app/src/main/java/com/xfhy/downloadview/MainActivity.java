package com.xfhy.downloadview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DownloadView.AnimProgressListener {

    private DownloadView mDownloadView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mDownloadView = findViewById(R.id.view_download);
        mDownloadView.setDuration(9900);
        mDownloadView.setAnimProgressListener(this);

        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_pause).setOnClickListener(this);
        findViewById(R.id.btn_continue).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mDownloadView.start();
                break;
            case R.id.btn_pause:
                mDownloadView.pause();
                break;
            case R.id.btn_continue:
                mDownloadView.continueAnim();
                break;
            case R.id.btn_stop:
                mDownloadView.stop();
                break;

        }
    }

    private static final String TAG = "MainActivity";

    @Override
    public void progressUpdate(int progress) {
        Log.w(TAG, "progressUpdate: progress=" + progress);
    }
}
