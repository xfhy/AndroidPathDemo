package com.xfhy.architecturedemo.livedata;

import java.util.Random;

/**
 * Created by xfhy on 2019/3/3 13:27
 * Description :
 */
public class RandomUtil {

    public static String getRandomNumber() {
        Random random = new Random();
        return random.nextInt(1000) + "";
    }

    public static String getChineseName() {
        Random random = new Random();
        return "姓名" + random.nextInt(1000);
    }

    public static String getRandomPhone() {
        Random random = new Random();
        return random.nextInt(1000) + "" + random.nextInt(1000);
    }


}
