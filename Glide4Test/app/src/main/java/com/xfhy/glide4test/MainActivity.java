package com.xfhy.glide4test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private final static String IMAGE_URL = "http://guolin.tech/book.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.iv_test);

        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });
    }

    private void loadImage() {
        //CenterCrop、FitCenter、CircleCrop,circleCrop
        //Glide自带圆形裁剪
//        RequestOptions requestOptions = new RequestOptions().circleCrop();  //圆形

        //模糊+黑白处理
        RequestOptions transform = new RequestOptions().transforms(new BlurTransformation(), new GrayscaleTransformation());
        Glide.with(this).load(IMAGE_URL).apply(transform).into(mImageView);


    }


}
