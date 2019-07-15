package com.xfhy.architecturedemo.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.xfhy.architecturedemo.room.bean.Order;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by xfhy on 2019/3/3 14:57
 * Description : Dao
 */

@Dao
public interface OrderDao {

    @Query("select * from orders")
    List<Order> loadAllOrders();

    /**
     * 可观察的数据  数据库中数据变化时,可以感知到
     */
    @Query("select * from orders")
    LiveData<List<Order>> loadAllOrderData();

    @Insert
    void insertAll(Order... orders);

    @Insert
    void insert(Order order);

    @Query("select * from orders where order_id in (:orderIds)")
    List<Order> queryOrderById(long[] orderIds);

    @Delete
    void deleteOrder(Order... orders);

    @Update
    void updateOrder(Order... orders);

    //-------------------配合RxJava---------------------
    @Query("select * from orders where order_id = :id limit 1")
    Flowable<Order> queryOrderById2(long id);

    @Query("select * from orders where order_id = :id limit 1")
    Maybe<Order> queryOrderById3(long id);

}
