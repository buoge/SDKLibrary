package com.glodon.tool;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * 串口数据转换工具类
 * Created by Administrator on 2016/6/2.
 */
public class DataUtils {
    //-------------------------------------------------------
    // 判断奇数或偶数，位运算，最后一位是1则为奇数，为0是偶数
    public static int isOdd(int num) {
        return num & 1;
    }

    //-------------------------------------------------------
    //Hex字符串转int
    public static int HexToInt(String inHex) {
        try {
            return Integer.parseInt(inHex, 16);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String IntToHex(int intHex) {
        return Integer.toHexString(intHex);
    }

    //-------------------------------------------------------
    //Hex字符串转byte
    public static byte HexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     *
     * @param hexStr
     * @return
     */
    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0;
             i < bytes.length;
             i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    //-------------------------------------------------------
    //1字节转2个Hex字符
    @Deprecated
    public static String byte2Hex(Byte inByte) {
        return String.format("%02x", new Object[]{inByte}).toUpperCase();
    }

    /**
     * 字节转十六进制
     *
     * @param b 需要进行转换的byte字节
     * @return 转换后的Hex字符串
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex.toUpperCase();
    }

    //-------------------------------------------------------
    //字节数组转转hex字符串
    public static String byteArrToHex(byte[] inBytArr) {
        StringBuilder strBuilder = new StringBuilder();
        for (byte valueOf : inBytArr) {
            strBuilder.append(byteToHex(valueOf));
        }
        return strBuilder.toString();
    }

    //-------------------------------------------------------
    //字节数组转转hex字符串，可选长度
    public static void byteArrToHex(StringBuilder builder, byte[] inBytArr, int offset,
                                    int byteCount) {
        if (null == builder)
            builder = new StringBuilder();
        int j = byteCount;
        for (int i = offset; i < j; i++) {
            String hexStr = byteToHex(inBytArr[i]);
            if (CheckNull.isNotNull(hexStr)) {
                builder.append(hexStr);
            }
        }
    }

    // ascii byte数 组转成String字符串，可选长度
    public static String byteOfASCIIToString(byte[] inBytArr, int offset, int byteCount) {
        StringBuilder strBuilder = new StringBuilder();
        int j = byteCount;
        for (int i = offset; i < j; i++) {
            char c = (char) inBytArr[i];
            strBuilder.append(c);
        }
        return strBuilder.toString();
    }


    //-------------------------------------------------------
    //转hex字符串转字节数组
    public static byte[] HexToByteArr(String inHex) {
        byte[] result;
        int hexlen = inHex.length();
        if (isOdd(hexlen) == 1) {
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = HexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    /**
     * 按照指定长度切割字符串
     *
     * @param inputString 需要切割的源字符串
     * @param length      指定的长度
     * @return
     */
    public static List<String> getDivLines(String inputString, int length) {
        List<String> divList = new ArrayList<>();
        int remainder = (inputString.length()) % length;
        // 一共要分割成几段
        int number = (int) Math.floor((inputString.length()) / length);
        for (int index = 0; index < number; index++) {
            String childStr = inputString.substring(index * length, (index + 1) * length);
            divList.add(childStr);
        }
        if (remainder > 0) {
            String cStr = inputString.substring(number * length, inputString.length());
            divList.add(cStr);
        }
        return divList;
    }

    /**
     * long转byte数组，大端模式
     *
     * @param l
     * @return
     */
    public static byte[] longToBytes_Big(long l) {
        byte b[] = new byte[8];
        b[0] = (byte) (0xff & (l >> 56));
        b[1] = (byte) (0xff & (l >> 48));
        b[2] = (byte) (0xff & (l >> 40));
        b[3] = (byte) (0xff & (l >> 32));
        b[4] = (byte) (0xff & (l >> 24));
        b[5] = (byte) (0xff & (l >> 16));
        b[6] = (byte) (0xff & (l >> 8));
        b[7] = (byte) (0xff & l);
        return b;
    }

    /**
     * byte数组转long
     *
     * @param bytes        8位的byte数组
     * @param littleEndian 是否是小端模式
     * @return
     * @throws Exception
     */
    public static long bytesToLong(byte[] bytes, boolean littleEndian) throws Exception {
        if (bytes.length != 8) {
            throw new Exception("参数错误，无法解析。");
        }
        ByteBuffer buffer = ByteBuffer.wrap(bytes, 0, 8);
        if (littleEndian) {
            // ByteBuffer.order(ByteOrder) 方法指定字节序,即大小端模式(BIG_ENDIAN/LITTLE_ENDIAN)
            // ByteBuffer 默认为大端(BIG_ENDIAN)模式
            buffer.order(ByteOrder.LITTLE_ENDIAN);
        }
        return buffer.getLong();
    }

    /**
     * int转byte数组  ,小端
     *
     * @param num
     * @return
     */
    public static byte[] intToBytes_Little(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) ((num >>> 0) & 0xff);
        result[1] = (byte) ((num >>> 8) & 0xff);
        result[2] = (byte) ((num >>> 16) & 0xff);
        result[3] = (byte) ((num >>> 24) & 0xff);
        return result;
    }

    /**
     * int转byte数组 ,大端
     *
     * @param num
     * @return
     */
    public static byte[] intToBytes_Big(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) ((num >>> 24) & 0xff);
        result[1] = (byte) ((num >>> 16) & 0xff);
        result[2] = (byte) ((num >>> 8) & 0xff);
        result[3] = (byte) ((num >>> 0) & 0xff);
        return result;
    }


    /**
     * byte数组转int,小端
     *
     * @param bytes
     * @return
     */
    public static int bytesToInt_Little(byte[] bytes) {
        int result = 0;
        if (bytes.length == 4) {
            int a = (bytes[0] & 0xff) << 0;
            int b = (bytes[1] & 0xff) << 8;
            int c = (bytes[2] & 0xff) << 16;
            int d = (bytes[3] & 0xff) << 24;
            result = a | b | c | d;
        }
        return result;
    }

    /**
     * byte数组转int,大端
     *
     * @param bytes
     * @return
     */
    public static int bytesToInt_Big(byte[] bytes) {
        int result = 0;
        if (bytes.length == 4) {
            int a = (bytes[0] & 0xff) << 24;
            int b = (bytes[1] & 0xff) << 16;
            int c = (bytes[2] & 0xff) << 8;
            int d = (bytes[3] & 0xff) << 0;
            result = a | b | c | d;
        }
        return result;
    }

    /**
     * 计算长度，两个字节长度
     *
     * @param val value
     * @return 结果
     */
    public static String twoByte(String val) {
        if (val.length() > 4) {
            val = val.substring(0, 4);
        } else {
            int l = 4 - val.length();
            for (int i = 0; i < l; i++) {
                val = "0" + val;
            }
        }
        return val;
    }

    /**
     * 校验和
     *
     * @param cmd 指令
     * @return 结果
     */
    public static String sum(String cmd) {
        List<String> cmdList = DataUtils.getDivLines(cmd, 2);
        int sumInt = 0;
        for (String c : cmdList) {
            sumInt += DataUtils.HexToInt(c);
        }
        String sum = DataUtils.IntToHex(sumInt);
        sum = DataUtils.twoByte(sum);
        cmd += sum;
        return cmd.toUpperCase();
    }

    public static int str8ToInt(String[] str) {
        int sum = 0;
        for (int i = 0; i < str.length; i++) {

            int value = computerBinary(str[i], str.length - 1 - i);

            sum += value;
        }
        return sum;
    }

    public static int str8ToInt(String str) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            String strIndex = str.charAt(i) + "";
            int value = computerBinary(strIndex, str.length() - 1 - i);

            sum += value;
        }
        return sum;
    }

    private static int computerBinary(String c, int index) {
        int vaule = Integer.valueOf(c);
        for (int i = 0; i < index; i++) {
            vaule *= 2;
        }
        return vaule;
    }

    /**
     * long转byte数组，小端模式
     *
     * @param l
     * @return
     */
    public static byte[] longToBytes_Little(long l) {
        byte b[] = new byte[8];
        b[7] = (byte) (0xff & (l >> 56));
        b[6] = (byte) (0xff & (l >> 48));
        b[5] = (byte) (0xff & (l >> 40));
        b[4] = (byte) (0xff & (l >> 32));
        b[3] = (byte) (0xff & (l >> 24));
        b[2] = (byte) (0xff & (l >> 16));
        b[1] = (byte) (0xff & (l >> 8));
        b[0] = (byte) (0xff & l);
        return b;
    }

    /**
     * byte数组转double
     *
     * @param bytes        8位byte数组
     * @param littleEndian 是否是小端模式
     * @return
     */
    public static double bytesToDouble(byte[] bytes, boolean littleEndian) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, 0, 8);
        if (littleEndian) {
            // ByteBuffer.order(ByteOrder) 方法指定字节序,即大小端模式(BIG_ENDIAN/LITTLE_ENDIAN)
            // ByteBuffer 默认为大端(BIG_ENDIAN)模式
            buffer.order(ByteOrder.LITTLE_ENDIAN);
        }
        long l = buffer.getLong();
        return Double.longBitsToDouble(l);
    }

    /**
     * double转byte数组，大端模式
     *
     * @param d
     * @return
     */
    public static byte[] doubleToBytes_Big(double d) {
        long l = Double.doubleToLongBits(d);
        byte b[] = new byte[8];
        b[0] = (byte) (0xff & (l >> 56));
        b[1] = (byte) (0xff & (l >> 48));
        b[2] = (byte) (0xff & (l >> 40));
        b[3] = (byte) (0xff & (l >> 32));
        b[4] = (byte) (0xff & (l >> 24));
        b[5] = (byte) (0xff & (l >> 16));
        b[6] = (byte) (0xff & (l >> 8));
        b[7] = (byte) (0xff & l);
        return b;
    }

    /**
     * double转byte数组，小端模式
     *
     * @param d
     * @return
     */
    public static byte[] doubleToBytes_Little(double d) {
        long l = Double.doubleToLongBits(d);
        byte b[] = new byte[8];
        b[7] = (byte) (0xff & (l >> 56));
        b[6] = (byte) (0xff & (l >> 48));
        b[5] = (byte) (0xff & (l >> 40));
        b[4] = (byte) (0xff & (l >> 32));
        b[3] = (byte) (0xff & (l >> 24));
        b[2] = (byte) (0xff & (l >> 16));
        b[1] = (byte) (0xff & (l >> 8));
        b[0] = (byte) (0xff & l);
        return b;
    }

    /**
     * 获得bytes的一段数据
     *
     * @param buff     原byte数组
     * @param startPos 起始位置
     * @param lenth    获取的长度
     * @return 返回获得的byte数组
     */
    public static byte[] getFromBuff(byte[] buff, int startPos, int lenth) {
        byte[] bytes = new byte[lenth];
        System.arraycopy(buff, startPos, bytes, 0, lenth);
        return bytes;
    }

    /**
     * 向将bytes添加到另一个bytes结尾，并返回位置
     *
     * @param buff 目标数组
     * @param pos  目标数组放置的起始位置
     * @param lens 添加的长度
     * @param dx   要添加的数组
     * @return lens添加的长度
     */
    public static int addToBuff(byte[] buff, int pos, int lens, byte[] dx) {
        System.arraycopy(dx, 0, buff, pos, lens);
        return lens;
    }


    public static int toLittleEndian(int a) {
        return (((a & 0xFF) << 24) | (((a >> 8) & 0xFF) << 16) | (((a >> 16) & 0xFF) << 8) | ((a >> 24) & 0xFF));

    }

    /**
     * 切换大小端续
     */
    public static byte[] changeBytes(byte[] a){
        byte[] b = new byte[a.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = a[b.length - i - 1];
        }
        return b;
    }

    public static String little2Big(String value) {
//        if(CheckNull.isNull(value))
//            return "";
        byte[] bytes=DataUtils.HexToByteArr(value);
        return DataUtils.byteArrToHex(DataUtils.changeBytes(bytes));
    }

    /**
     * @param md5L16
     * @return
     * @Date:2014-3-18
     * @Author:lulei
     * @Description: 将16位的md5转化为long值
     */
    public static long parseMd5L16ToLong(String md5L16){
        if (md5L16 == null) {
            throw new NumberFormatException("null");
        }
        md5L16 = md5L16.toLowerCase();
        byte[] bA = md5L16.getBytes();
        long re = 0L;
        for (int i = 0; i < bA.length; i++) {
            //加下一位的字符时，先将前面字符计算的结果左移4位
            re <<= 4;
            //0-9数组
            byte b = (byte) (bA[i] - 48);
            //A-F字母
            if (b > 9) {
                b = (byte) (b - 39);
            }
            //非16进制的字符
            if (b > 15 || b < 0) {
                throw new NumberFormatException("For input string '" + md5L16);
            }
            re += b;
        }
        return re;
    }

    /**
     * @param str16
     * @return
     * @Date:2014-3-18
     * @Author:lulei
     * @Description: 将16进制的字符串转化为long值
     */
    public static long parseString16ToLong(String str16){
        if (str16 == null) {
            throw new NumberFormatException("null");
        }
        //先转化为小写
        str16 = str16.toLowerCase();
        //如果字符串以0x开头，去掉0x
        str16 = str16.startsWith("0x") ? str16.substring(2) : str16;
        if (str16.length() > 16) {
            throw new NumberFormatException("For input string '" + str16 + "' is to long");
        }
        return parseMd5L16ToLong(str16);
    }

    /** 获取指令异或值
     * @param datas
     * @return
     */
    public static int getXOR(byte[] datas) {
        int temp = datas[1];              // 此处首位取1是因为本协议中第一个数据不参数异或校验，转为int防止结果出现溢出变成负数

        for (int i = 2; i < datas.length; i++) {
            int preTemp = temp;
            int iData;
            if (datas[i] < 0) {
                iData = datas[i] & 0xff;      // 变为正数计算
            } else {
                iData = datas[i];
            }
            if (temp < 0) {
                temp = temp & 0xff;          // 变为正数
            }
            temp ^= iData;

            System.out.println(preTemp + "异或" + iData + "=" + temp);
        }

        return temp;
    }

    public static String getXORHex(String hex){
        int code=DataUtils.getXOR(DataUtils.HexToByteArr(hex));
        String commXOR=Integer.toHexString(code);
        if(commXOR.length()==1)
            commXOR="0"+commXOR;
        return commXOR.toUpperCase();
    }
}
