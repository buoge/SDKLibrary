package com.glodon.glm.kotlintools

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by lichongmac@163.com on 2020/6/1.
 */
object DateUtils {
    val YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    val MM_DD_HH_MM = "MM-dd HH:mm";
    val YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    val HH_MM_SS = "HH:mm:ss";
    val HH_MM = "HH:mm";
    val YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    val YYYY_MM_DD = "yyyy-MM-dd";
    val YYYY_MM_DD_ZN = "yyyy年MM月dd日";
    val YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 将日期时间转换为时间戳
     * */
    fun dateToTimeMillis(date: String, format: String): Long {
        if (date.isEmpty() || format.isEmpty())
            return 0L
        try {
            return SimpleDateFormat(format).parse(date, ParsePosition(0)).time
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
        return 0L
    }

    /**
     * 将时间戳转换为日期时间
     * */
    fun timeMillis2Date(time: Long, format: String): String {
        return SimpleDateFormat(format).format(Date(time))
    }

    fun getDateHex(time: Long): String? {
        return java.lang.Long.toHexString(time) //5DD72E96
    }

    fun getWeek(time: Long): String? {
        val cd = Calendar.getInstance()
        cd.time = Date(time)
        val year = cd[Calendar.YEAR] //获取年份
        val month = cd[Calendar.MONTH] //获取月份
        val day = cd[Calendar.DAY_OF_MONTH] //获取日期
        val week = cd[Calendar.DAY_OF_WEEK] //获取星期
        val weekString: String
        weekString = when (week) {
            Calendar.SUNDAY -> "周日"
            Calendar.MONDAY -> "周一"
            Calendar.TUESDAY -> "周二"
            Calendar.WEDNESDAY -> "周三"
            Calendar.THURSDAY -> "周四"
            Calendar.FRIDAY -> "周五"
            else -> "周六"
        }
        return weekString
    }

}