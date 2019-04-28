package com.xfhy.recyclerviewdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13195 on 2019/4/28 23:11
 * Description :
 */
class DataUtil {
    public static List<String> getStrTestData() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            strings.add(String.valueOf(i + "=====item"));
        }
        return strings;
    }
}
