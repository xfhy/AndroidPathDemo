package com.xfhy.architecturedemo.livedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xfhy.architecturedemo.R;

/**
 * 观察User的数据变化  进行展示
 */
public class LifeDataActivity extends AppCompatActivity {

    private UserViewModel mUserViewModel;
    private TextView mTestTv;
    private Button mChangeDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_data);

        mTestTv = findViewById(R.id.tv_observer);
        mChangeDataBtn = findViewById(R.id.btn_change_data);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mUserViewModel.getData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    mTestTv.setText(user.toString());
                }
            }
        });

        mChangeDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserViewModel != null) {
                    //改变数据   上面的观察者会接收到,然后重新设置TExtView的内容
                    mUserViewModel.changeData();
                }
            }
        });
    }
}
