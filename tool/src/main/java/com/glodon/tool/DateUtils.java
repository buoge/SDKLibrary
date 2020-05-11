package com.glodon.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具
 */
public class DateUtils {
    /*
     * 将时间转换为时间戳
     */
    @Deprecated
    public static long dateToTimeMillis(String s) throws ParseException {
        return dateToTimeMillis(s,"yyyy-MM-dd HH:mm:ss");
    }

    public static long dateToTimeMillis(String s,String format) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(s);
        return date.getTime();
    }



    @Deprecated
    public static String timeMillis2Date(long selectedTime) {
        return timeMillis2Date(selectedTime, "yyyy-MM-dd");
    }

    public static String timeMillis2Date(long selectedTime, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(selectedTime);

        return simpleDateFormat.format(date);
    }

    public static String timeMillis2Date(String selectedTime, String pattern) {
        return timeMillis2Date(Long.valueOf(selectedTime),pattern);
    }


    public static String getDefaultDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        return simpleDateFormat.format(date);
    }

    public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public final static String MM_DD_HH_MM = "MM-dd HH:mm";
    public final static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public final static String HH_MM_SS = "HH:mm:ss";
    public final static String HH_MM = "HH:mm";
    public final static String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public final static String YYYY_MM_DD_ZN = "yyyy年MM月dd日";
    public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String getDate(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date(System.currentTimeMillis());

        return simpleDateFormat.format(date);
    }
    public static String getDateHex(long time){
        return Long.toHexString(time);//5DD72E96
    }

    public static String getDateHexWithLittle(long time) {
        String hexTime=getDateHex(time);
        byte[] bytesTime=DataUtils.HexToByteArr(hexTime);
        byte[] bytesTimeLittle=DataUtils.changeBytes(bytesTime);
        return DataUtils.byteArrToHex(bytesTimeLittle);
    }


    public static String getDateToStringByFormat(Date date,String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String getWeek(long time) {

        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date(time));

        int year  = cd.get(Calendar.YEAR); //获取年份
        int month = cd.get(Calendar.MONTH); //获取月份
        int day   = cd.get(Calendar.DAY_OF_MONTH); //获取日期
        int week  = cd.get(Calendar.DAY_OF_WEEK); //获取星期

        String weekString;
        switch (week) {
            case Calendar.SUNDAY:
                weekString = "周日";
                break;
            case Calendar.MONDAY:
                weekString = "周一";
                break;
            case Calendar.TUESDAY:
                weekString = "周二";
                break;
            case Calendar.WEDNESDAY:
                weekString = "周三";
                break;
            case Calendar.THURSDAY:
                weekString = "周四";
                break;
            case Calendar.FRIDAY:
                weekString = "周五";
                break;
            default:
                weekString = "周六";
                break;

        }

        return weekString;
    }
}
