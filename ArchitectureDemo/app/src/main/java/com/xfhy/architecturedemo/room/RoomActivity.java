package com.xfhy.architecturedemo.room;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xfhy.architecturedemo.R;
import com.xfhy.architecturedemo.livedata.RandomUtil;
import com.xfhy.architecturedemo.room.bean.Order;
import com.xfhy.architecturedemo.room.dao.OrderDao;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 增
     */
    private Button mBtnInsert;
    /**
     * 删
     */
    private Button mBtnDelete;
    /**
     * 改
     */
    private Button mBtnUpdate;
    /**
     * 查
     */
    private Button mBtnQuery;
    private TextView mContentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        initView();
    }

    private void initView() {
        mBtnInsert = findViewById(R.id.btn_insert);
        mBtnInsert.setOnClickListener(this);
        mBtnDelete = findViewById(R.id.btn_delete);
        mBtnDelete.setOnClickListener(this);
        mBtnUpdate = findViewById(R.id.btn_update);
        mBtnUpdate.setOnClickListener(this);
        mBtnQuery = findViewById(R.id.btn_query);
        mBtnQuery.setOnClickListener(this);
        mContentTv = findViewById(R.id.tv_content);

        DbManager.getDbInstance().getOrderDao().loadAllOrderData().observe(this, new Observer<List<Order>>() {
            @Override
            public void onChanged(@Nullable List<Order> orders) {
                if (orders == null || orders.size() == 0) {
                    mContentTv.setText("无数据");
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (Order order : orders) {
                    stringBuilder.append(order.toString()).append("\n");
                }
                mContentTv.setText(stringBuilder.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_insert:
                insertData();
                break;
            case R.id.btn_delete:
                deleteData();
                break;
            case R.id.btn_update:
                updateData();
                break;
            case R.id.btn_query:
                queryData();
                break;
        }
    }

    private static final String TAG = "RoomActivity";

    private void queryData() {
        AppDatabase dbInstance = DbManager.getDbInstance();
        OrderDao orderDao = dbInstance.getOrderDao();
        List<Order> orderList = orderDao.loadAllOrders();
        for (Order order : orderList) {
            Log.w(TAG, "queryData: " + order.toString());
        }
    }

    private void updateData() {
        AppDatabase dbInstance = DbManager.getDbInstance();
        OrderDao orderDao = dbInstance.getOrderDao();
        Order order = new Order();
        order.orderId = 3;
        order.address = "更新:打算打打死";
        orderDao.updateOrder(order);
    }

    private void deleteData() {
        AppDatabase dbInstance = DbManager.getDbInstance();
        OrderDao orderDao = dbInstance.getOrderDao();
        Order order = new Order();
        order.orderId = 5;
        orderDao.deleteOrder(order);
    }

    private void insertData() {
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Order order = new Order();
            order.address = RandomUtil.getChineseName();
            order.ownerPhone = RandomUtil.getRandomPhone();
            orderList.add(order);
        }
        for (Order order : orderList) {
            DbManager.getDbInstance().getOrderDao().insert(order);
        }
    }
}
