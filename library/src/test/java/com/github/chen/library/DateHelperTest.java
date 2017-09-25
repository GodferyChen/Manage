package com.github.chen.library;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chen on 2017/9/25.
 */
public class DateHelperTest {
    @Test
    public void string() throws Exception {
        String string = DateHelper.string(1506339206000L, DateHelper.yMdHms);
        assertEquals("2017-09-25 19:33:23",string);
    }

}