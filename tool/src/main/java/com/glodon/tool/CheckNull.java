package com.glodon.tool;

public class CheckNull {

    public static boolean isNull(String value) {
        return value == null || value.length() == 0 ? true : false;
    }
    public static String getSelfOrBlank(String value) {
        return isNull(value) ? "" : value;
    }

    public static boolean isNotNull(String value){
        return !isNull(value);
    }

}
