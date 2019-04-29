package com.xfhy.recyclerviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.xfhy.recyclerviewdemo.common.ItemTouchHelperCallback;
import com.xfhy.recyclerviewdemo.common.divider.DividerListItemDecoration;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mStringDataRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mStringDataRv = findViewById(R.id.rv_string_data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mStringDataRv.setLayoutManager(linearLayoutManager);
        StringDataAdapter stringDataAdapter = new StringDataAdapter(this, DataUtil.getStrTestData());
        mStringDataRv.setAdapter(stringDataAdapter);

        //分割线
        mStringDataRv.addItemDecoration(new DividerListItemDecoration(this, LinearLayoutManager.VERTICAL));

        //拖拽 滑动删除
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback());
        itemTouchHelper.attachToRecyclerView(mStringDataRv);
    }

}
