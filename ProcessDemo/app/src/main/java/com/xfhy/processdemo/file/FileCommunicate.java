package com.xfhy.processdemo.file;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by xfhy on 2019/4/10 22:33
 * Description :
 */
public class FileCommunicate {

    private static final String TAG = "FileCommunicate";

    /**
     * 写入文件数据
     */
    public static void persistToFile() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User(1, "hello1", false);
                File rootDirectory = Environment.getExternalStorageDirectory();
                File realFile = new File(rootDirectory.getAbsolutePath() + "/user.txt");
                try {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(realFile));
                    objectOutputStream.writeObject(user);
                    Log.e(TAG, "persist user:" + user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 读取文件数据
     */
    public static void recoverFromFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File rootDirectory = Environment.getExternalStorageDirectory();
                File realFile = new File(rootDirectory.getAbsolutePath() + "/user.txt");
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(realFile));
                    User user = (User) objectInputStream.readObject();
                    Log.e(TAG, "recover user:" + user);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
