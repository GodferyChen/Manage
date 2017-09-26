package com.github.chen.library;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by chen on 2017/9/26.
 */
public class EncryptHelperTest {
    @Test
    public void MD5StrLower32() throws Exception {
        assertEquals("25d55ad283aa400af464c76d713c07ad",EncryptHelper.MD5StrLower32("12345678"));
    }

}