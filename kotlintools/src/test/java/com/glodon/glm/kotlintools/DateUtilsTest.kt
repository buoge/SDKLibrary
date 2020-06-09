package com.glodon.glm.kotlintools

import org.junit.Assert
import org.junit.Test

/**
 * Created by lichongmac@163.com on 2020/6/1.
 */
class DateUtilsTest {

    @Test
    fun dateToTimeMillis() {
        Assert.assertEquals(1591010042000L, DateUtils.dateToTimeMillis("2020-06-01 19:14:02", DateUtils.YYYY_MM_DD_HH_MM_SS))
    }

    @Test
    fun dateToTimeMillisWithBlank() {
        Assert.assertEquals(0L, DateUtils.dateToTimeMillis("", DateUtils.YYYY_MM_DD_HH_MM_SS))
    }

    @Test
    fun dateToTimeMillisWithBlank2() {
        Assert.assertEquals(0L, DateUtils.dateToTimeMillis("2020-06-01 19:14:02", ""))
    }

    @Test
    fun dateToTimeMillisWithIll() {
        Assert.assertEquals(0L, DateUtils.dateToTimeMillis("2321321323", DateUtils.YYYY_MM_DD_HH_MM_SS))
    }


    @Test
    fun timeMillis2Date() {
        Assert.assertEquals("2020-06-01 19:14:02", DateUtils.timeMillis2Date(1591010042000L, DateUtils.YYYY_MM_DD_HH_MM_SS))
    }

    @Test
    fun timeMillis2DateWithBlank() {
        Assert.assertEquals("", DateUtils.timeMillis2Date(1591010042000L, ""))
    }

    @Test
    fun timeMillis2DateWith0() {
        Assert.assertEquals("1970-01-01 08:00:00", DateUtils.timeMillis2Date(0L, DateUtils.YYYY_MM_DD_HH_MM_SS))
    }

    @Test
    fun timeMillis2DateWith_1() {
        Assert.assertEquals("1970-01-01 07:59:59", DateUtils.timeMillis2Date(-1L, DateUtils.YYYY_MM_DD_HH_MM_SS))
    }

    @Test
    fun testDateHex() {
        Assert.assertEquals("1726f96a090", DateUtils.getDateHex(1591010042000L))
    }

    @Test
    fun testDateHex_0() {
        Assert.assertEquals("0", DateUtils.getDateHex(0L))
    }

    @Test
    fun testDateHex__1() {
        Assert.assertEquals("ffffffffffffffff", DateUtils.getDateHex(-1L))
    }

    @Test
    fun testDateHex__2() {
        Assert.assertEquals("fffffffffffffffe", DateUtils.getDateHex(-2L))
    }

    @Test
    fun testDateHex__10() {
        Assert.assertEquals("fffffffffffffff6", DateUtils.getDateHex(-10L))
    }

    @Test
    fun getWeek__10() {
        Assert.assertEquals("周四", DateUtils.getWeek(-10L))
    }

    @Test
    fun getWeek_0() {
        Assert.assertEquals("周四", DateUtils.getWeek(0L))
    }

    @Test
    fun getWeek_1591010042000L() {
        Assert.assertEquals("周一", DateUtils.getWeek(1591010042000L))
    }

//@Test
//    fun getWeek_() {
//        Assert.assertEquals("周一", DateUtils.getWeek(null))
//    }


}