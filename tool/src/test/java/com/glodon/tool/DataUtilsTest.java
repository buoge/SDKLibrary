package com.glodon.tool;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by lichongmac@163.com on 2019-10-11.
 */
public class DataUtilsTest {
    @Test
    public void long2Hex() {
        long time = 1574383254l;//1574383254
        System.out.println(time);
        String hexTime = DateUtils.getDateHex(time);//5DD72E96
        System.out.println(hexTime.toUpperCase());
        Assert.assertEquals(time, DataUtils.parseString16ToLong(hexTime));
    }

    @Test
    public void getXor() {
        int b= DataUtils.getXOR(DataUtils.HexToByteArr("280209962ED75D"));
        Assert.assertEquals("39",Integer.toHexString(b));

          b= DataUtils.getXOR(DataUtils.HexToByteArr("280309962ED75D"));
        Assert.assertEquals("38",Integer.toHexString(b));
   }

   @Test
   public void getXorHex(){
       Assert.assertEquals("38",DataUtils.getXORHex("280309962ED75D"));
       Assert.assertEquals("39",DataUtils.getXORHex("280209962ED75D"));
   }

    @Test
    public void isOdd() {
    }

    @Test
    public void hexToInt() {
    }

    @Test
    public void intToHex() {
    }

    @Test
    public void hexToByte() {
    }

    @Test
    public void hexStr2Str() {
    }

    @Test
    public void byte2Hex() {
        assertEquals("E8", DataUtils.byte2Hex((byte) 1000));
    }

    @Test
    public void byteToHex() {
        assertEquals("E8", DataUtils.byteToHex((byte) 1000));
    }

    @Test
    public void byteArrToHex() {
    }

    @Test
    public void byteArrToHex1() {
    }

    @Test
    public void byteOfASCIIToString() {
    }

    @Test
    public void hexToByteArr() {
    }

    @Test
    public void getDivLines() {
    }

    @Test
    public void twoByte() {
    }

    @Test
    public void sum() {
    }

    @Test
    public void str8ToInt() {
    }

    @Test
    public void str8ToInt1() {
    }

    @Test
    public void parseString16ToLong(){
        assertEquals(134495389, DataUtils.parseString16ToLong("08043C9D"));
        assertEquals(134495388, DataUtils.parseString16ToLong("08043C9C"));
    }
}