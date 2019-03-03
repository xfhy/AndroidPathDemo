package com.xfhy.architecturedemo.room.bean;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by xfhy on 2019/3/3 14:15
 * Description :
 */
@Entity(tableName = "orders")
public class Order {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id")
    public long orderId;

    @ColumnInfo(name = "address")
    public String address;

    @ColumnInfo(name = "owner_name")
    public String ownerName;

    @ColumnInfo(name = "owner_phone")
    public String ownerPhone;

    //指示Room需要忽略的字段或方法
    @Ignore
    public String ignoreText;

    @Embedded
    public OwerAddress owerAddress;

    static class OwerAddress {
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", address='" + address + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", ownerPhone='" + ownerPhone + '\'' +
                ", ignoreText='" + ignoreText + '\'' +
                ", owerAddress=" + owerAddress +
                '}';
    }
}
