package com.xfhy.viewmodeldemo;

import java.io.Serializable;

/**
 * Created by xfhy on 2019/3/19 20:53
 * Description :
 */
class User implements Serializable {

    public int age;
    public String name;

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
