package com.example.demo.Utils;

public class DataUtil {

    /**
     * 去除字符串中头部和尾部所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
     *
     * @param s
     * @return
     */
    public static String trim(String s) {
        String result = "";
        if (null != s && !"".equals(s)) {
            result = s.replaceAll("^[　*| *| *|\\s*]*", "").replaceAll("^[ *| *| *|\\s*]*", "")
                    .replaceAll("[　*| *| *|\\s*]*$", "").replaceAll("[ *| *| *|\\s*]*$", "");
        }
        return result;
    }


}
