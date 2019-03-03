package com.xfhy.architecturedemo.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.xfhy.architecturedemo.MyApplication;
import com.xfhy.architecturedemo.room.bean.Order;
import com.xfhy.architecturedemo.room.dao.OrderDao;

/**
 * Created by xfhy on 2019/3/3 15:01
 * Description :
 *
 * @Entity(tableName = "orders")   //   定义表名；
 * @PrimaryKey                           //   定义主键；
 * @ColumnInfo(name = "order_id")   //   定义数据表中的字段名；
 * @Ignore                                 //    指示 Room 需要忽略的字段或方法；
 * @Embedded   //  指定嵌入实体
 * @Query("SELECT * FROM orders")  //    定义查询数据接口；
 * @Insert   //   定义增加数据接口；
 * @Delete  //  定义删除数据接口；
 * @Update  //  定义更新数据接口；
 * @Database  //  定义数据库信息，表信息，数据库版本
 */
@Database(entities = {Order.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";

    public abstract OrderDao getOrderDao();

    public static AppDatabase buildDb() {
        AppDatabase appDatabase = Room.databaseBuilder(MyApplication.getContext(),
                AppDatabase.class, "test_db")  //指定数据库名称
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        //数据库创建回调
                        Log.w(TAG, "onCreate: ");
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        //数据库使用回调
                        Log.w(TAG, "onOpen: ");
                    }
                })
                .allowMainThreadQueries()   //数据库操作可运行在主线程
                .build();
        return appDatabase;
    }
}
