package com.github.chen.library;

import java.util.regex.Pattern;

public class StringHelper {

    /**
     * 判断是否为浮点数，包括double和float
     *
     * @param str 传入的字符串
     * @return 是浮点数返回true, 否则返回false
     */
    public static boolean isDouble(String str){
        Pattern pattern = Pattern.compile("^([+-]?)\\d*\\.?\\d+$");
        return pattern.matcher(str).matches();
    }

}
