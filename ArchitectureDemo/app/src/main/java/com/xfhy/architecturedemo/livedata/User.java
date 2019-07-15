package com.xfhy.architecturedemo.livedata;

/**
 * Created by xfhy on 2019/3/3 12:59
 * Description :
 */
public class User {
    public String userId;

    public String name;

    public String phone;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
