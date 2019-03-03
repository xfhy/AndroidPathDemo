package com.xfhy.architecturedemo.livedata;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

/**
 * Created by xfhy on 2019/3/3 13:22
 * Description :
 */
public class UserViewModel extends ViewModel implements BaseViewModel<User> {

    private static final String TAG = "UserViewModel";

    /**
     * 将User封装到这里面
     */
    private MutableLiveData<User> mLiveData;

    public MutableLiveData<User> getData(){
        if (mLiveData == null) {
            mLiveData = new MutableLiveData<>();
        }

        mLiveData.setValue(loadData());
        return mLiveData;
    }

    /**
     * 测试方法  用来改变user的值的
     */
    public void changeData(){
        if (mLiveData != null) {
            mLiveData.setValue(loadData());
        }
    }

    @Override
    public User loadData() {
        User user = new User();
        user.userId = RandomUtil.getRandomNumber();
        user.name = RandomUtil.getChineseName();
        user.phone = RandomUtil.getRandomPhone();
        Log.e(TAG, "loadData(): " + user.toString());
        return user;
    }

    @Override
    public void clearData() {

    }
}
