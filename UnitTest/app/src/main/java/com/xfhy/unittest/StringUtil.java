package com.xfhy.unittest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xfhy on 2019/3/28 21:14
 * Description :
 */
public class StringUtil {

    public static boolean isValidEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
        }
        return flag;

    }

}
