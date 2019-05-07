package com.xfhy.slidingconflict;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        ListView listView1 = findViewById(R.id.lv_one);
        ListView listView2 = findViewById(R.id.lv_two);
        ListView listView3 = findViewById(R.id.lv_three);


        initData(listView1);
        initData(listView2);
        initData(listView3);

    }

    private void initData(ListView listView) {
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.content_list_item, R.id.tv_name, datas);
        listView.setAdapter(adapter);
    }
}
