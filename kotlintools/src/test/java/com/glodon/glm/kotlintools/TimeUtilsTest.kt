package com.glodon.glm.kotlintools

import org.junit.Assert
import org.junit.Test


/**
 * Created by lichongmac@163.com on 2020/5/26.
 */
class TimeUtilsTest {
    @Test
    fun test11() {
        Assert.assertEquals("98:48:09", TimeUtils.formatByMillis(355689L))
     Assert.assertEquals("30:01", TimeUtils.formatByMillis(60 * 30 + 1L))
     Assert.assertEquals("01:00:01", TimeUtils.formatByMillis(60 * 60 + 1L))
     Assert.assertEquals("01:03:01", TimeUtils.formatByMillis(60 * 60 + 60 * 3 + 1L))
     Assert.assertEquals("60:03:01", TimeUtils.formatByMillis(60 * 60 * 60 + 60 * 3 + 1L))
    }




}