package com.xfhy.architecturedemo.room;

/**
 * Created by xfhy on 2019/3/3 15:25
 * Description :
 */
public class DbManager {

    static class SingletonHolder {
        private final static DbManager INSTANCE = new DbManager();
    }

    private DbManager() {
        mAppDatabase = AppDatabase.buildDb();
    }

    public static DbManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private AppDatabase mAppDatabase;

    public static AppDatabase getDbInstance() {
        return getInstance().mAppDatabase;
    }

}
