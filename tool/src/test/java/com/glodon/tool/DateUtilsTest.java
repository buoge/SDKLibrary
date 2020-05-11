package com.glodon.tool;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lichongmac@163.com on 2019-11-25.
 */
public class DateUtilsTest {

    @Test
    public void checkDateFormat(){
        String timeStr=DateUtils.timeMillis2Date(1575548059000l,DateUtils.YYYY_MM_DD_HH_MM_SS);
        Assert.assertEquals("2019-12-05 20:14:19",timeStr);
    }

    @Test
    public void getDateHexWithLittle(){

        long time = 1546272087l;//1574383254  962ED75D
//        long time = System.currentTimeMillis()/1000;//1574383254
//        long time = System.currentTimeMillis()/1000;//1574929243
//        System.out.println(time);
//        String hexTime=DateUtils.getDateHex(time);//5DD72E96
//        System.out.println(hexTime.toUpperCase());
//        byte[] bytesTime=DataUtils.HexToByteArr(hexTime);
//        byte[] bytesTimeLittle=DataUtils.changeBytes(bytesTime);
//        System.out.println(DataUtils.byteArrToHex(bytesTimeLittle));

        String hexTimeLittle=DateUtils.getDateHexWithLittle(time);
        Assert.assertEquals("A2D8E85D",hexTimeLittle);
    }



}