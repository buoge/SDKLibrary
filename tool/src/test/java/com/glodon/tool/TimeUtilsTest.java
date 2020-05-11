package com.glodon.tool;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeUtilsTest {

    @Test
    public void formatSeconds() {
        Assert.assertEquals("00:05",TimeUtils.formatSeconds(5020/1000));
    }
}