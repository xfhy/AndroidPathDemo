package com.xfhy.unittest;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by xfhy on 2019/3/28 21:16
 * Description :
 */
public class StringUtilTest {

    @Test
    public void isValidEmail() {
        assertThat(StringUtil.isValidEmail("name@email.com"), is(true));
        assertThat(StringUtil.isValidEmail("#name@email.com"), is(false));
    }
}