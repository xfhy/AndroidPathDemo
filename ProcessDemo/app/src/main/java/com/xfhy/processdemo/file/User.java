package com.xfhy.processdemo.file;

import java.io.Serializable;

/**
 * Created by xfhy on 2019/4/10 22:34
 * Description :
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    public int userId;
    public String userName;
    public boolean isMale;

    public User() {
    }

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", isMale=" + isMale +
                '}';
    }
}
