package com.glodon.glm.kotlintools

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.*
import kotlin.experimental.and

/**
 * Created by lichongmac@163.com on 2020/6/11.
 */
object DataUtils {
    //-------------------------------------------------------
    // 判断奇数或偶数，位运算，最后一位是1则为奇数，为0是偶数
    fun isOdd(num: Int): Int {
        return num and 1
    }

    //-------------------------------------------------------
    //Hex字符串转int
    fun HexToInt(inHex: String): Int {
        try {
            return inHex.toInt(16)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        return 0
    }

    fun IntToHex(intHex: Int): String {
        return Integer.toHexString(intHex)
    }

    fun HexToByte(inHex: String): Byte {
        return inHex.toInt(16).toByte()
    }

    /**
     * 16进制直接转换成为字符串(无需Unicode解码)
     *
     * @param hexStr
     * @return
     */
    fun hexStr2Str(hexStr: String): String? {
        val str = "0123456789ABCDEF"
        val hexs = hexStr.toCharArray()
        val bytes = ByteArray(hexStr.length / 2)
        var n: Int
        for (i in bytes.indices) {
            n = str.indexOf(hexs[2 * i]) * 16
            n += str.indexOf(hexs[2 * i + 1])
            bytes[i] = (n and 0xff).toByte()
        }
        return String(bytes)
    }

    /**
     * 字节转十六进制
     *
     * @param b 需要进行转换的byte字节
     * @return 转换后的Hex字符串
     */
    fun byteToHex(b: Byte): String {
        var hex = Integer.toHexString((b and 0xFF.toByte()).toInt())
        if (hex.length < 2) {
            hex = "0$hex"
        }
        return hex.toUpperCase()
    }

    //-------------------------------------------------------
    //字节数组转转hex字符串
    fun byteArrToHex(inBytArr: ByteArray): String? {
        val strBuilder = StringBuilder()
        for (valueOf in inBytArr) {
            strBuilder.append(byteToHex(valueOf))
        }
        return strBuilder.toString()
    }

    //-------------------------------------------------------
    //字节数组转转hex字符串，可选长度
    fun byteArrToHex(builder: java.lang.StringBuilder?, inBytArr: ByteArray, offset: Int,
                     byteCount: Int) {
        var builder = builder
        if (null == builder) builder = java.lang.StringBuilder()
        for (i in offset until byteCount) {
            val hexStr: String = byteToHex(inBytArr[i])
            if (hexStr.isNotBlank()) {
                builder.append(hexStr)
            }
        }
    }

    // ascii byte数 组转成String字符串，可选长度
    fun byteOfASCIIToString(inBytArr: ByteArray, offset: Int, byteCount: Int): String? {
        val strBuilder = java.lang.StringBuilder()
        for (i in offset until byteCount) {
            val c = inBytArr[i].toChar()
            strBuilder.append(c)
        }
        return strBuilder.toString()
    }

    //-------------------------------------------------------
    //转hex字符串转字节数组
    fun HexToByteArr(inHex: String): ByteArray {
        var inHex = inHex
        val result: ByteArray
        var hexlen = inHex.length
        if (isOdd(hexlen) == 1) {
            hexlen++
            result = ByteArray(hexlen / 2)
            inHex = "0$inHex"
        } else {
            result = ByteArray(hexlen / 2)
        }
        var j = 0
        var i = 0
        while (i < hexlen) {
            result[j] = HexToByte(inHex.substring(i, i + 2))
            j++
            i += 2
        }
        return result
    }

    /**
     * 按照指定长度切割字符串
     *
     * @param inputString 需要切割的源字符串
     * @param length      指定的长度
     * @return
     */
    fun getDivLines(inputString: String, length: Int): List<String> {
        val divList: MutableList<String> = ArrayList()
        val remainder = inputString.length % length
        // 一共要分割成几段
        val number = Math.floor(inputString.length / length.toDouble()).toInt()
        for (index in 0 until number) {
            val childStr = inputString.substring(index * length, (index + 1) * length)
            divList.add(childStr)
        }
        if (remainder > 0) {
            val cStr = inputString.substring(number * length, inputString.length)
            divList.add(cStr)
        }
        return divList
    }

    /**
     * long转byte数组，大端模式
     *
     * @param l
     * @return
     */
    fun longToBytes_Big(l: Long): ByteArray? {
        val b = ByteArray(8)
        b[0] = (0xff and (l shr 56).toInt()).toByte()
        b[1] = (0xff and (l shr 48).toInt()).toByte()
        b[2] = (0xff and (l shr 40).toInt()).toByte()
        b[3] = (0xff and (l shr 32).toInt()).toByte()
        b[4] = (0xff and (l shr 24).toInt()).toByte()
        b[5] = (0xff and (l shr 16).toInt()).toByte()
        b[6] = (0xff and (l shr 8).toInt()).toByte()
        b[7] = (0xff and l.toInt()).toByte()
        return b
    }

    /**
     * byte数组转long
     *
     * @param bytes        8位的byte数组
     * @param littleEndian 是否是小端模式
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun bytesToLong(bytes: ByteArray, littleEndian: Boolean): Long {
        if (bytes.size != 8) {
            throw Exception("参数错误，无法解析。")
        }
        val buffer = ByteBuffer.wrap(bytes, 0, 8)
        if (littleEndian) {
            // ByteBuffer.order(ByteOrder) 方法指定字节序,即大小端模式(BIG_ENDIAN/LITTLE_ENDIAN)
            // ByteBuffer 默认为大端(BIG_ENDIAN)模式
            buffer.order(ByteOrder.LITTLE_ENDIAN)
        }
        return buffer.long
    }

    /**
     * int转byte数组  ,小端
     *
     * @param num
     * @return
     */
    fun intToBytes_Little(num: Int): ByteArray? {
        val result = ByteArray(4)
        result[0] = (num ushr 0 and 0xff).toByte()
        result[1] = (num ushr 8 and 0xff).toByte()
        result[2] = (num ushr 16 and 0xff).toByte()
        result[3] = (num ushr 24 and 0xff).toByte()
        return result
    }

    /**
     * int转byte数组 ,大端
     *
     * @param num
     * @return
     */
    fun intToBytes_Big(num: Int): ByteArray? {
        val result = ByteArray(4)
        result[0] = (num ushr 24 and 0xff).toByte()
        result[1] = (num ushr 16 and 0xff).toByte()
        result[2] = (num ushr 8 and 0xff).toByte()
        result[3] = (num ushr 0 and 0xff).toByte()
        return result
    }

//    /**
//     * byte数组转int,小端
//     *
//     * @param bytes
//     * @return
//     */
//    fun bytesToInt_Little(bytes: ByteArray): Int {
//        var result = 0
//        if (bytes.size == 4) {
//            val a: Int = bytes[0] and 0xff.toByte() shl 0
//            val b: Int = bytes[1] and 0xff.toByte() shl 8
//            val c: Int = bytes[2] and 0xff.toByte() shl 16
//            val d: Int = bytes[3] and 0xff.toByte() shl 24
//            result = a or b or c or d
//        }
//        return result
//    }

    /**
     * byte数组转int,大端
     *
     * @param bytes
     * @return
     */
//    fun bytesToInt_Big(bytes: ByteArray): Int {
//        var result = 0
//        if (bytes.size == 4) {
//            val a: Int = bytes[0] and 0xff.toByte() shl 24
//            val b: Int = bytes[1] and 0xff.toByte() shl 16
//            val c: Int = bytes[2] and 0xff.toByte() shl 8
//            val d: Int = bytes[3] and 0xff.toByte() shl 0
//            result = a or b or c or d
//        }
//        return result
//    }

    /**
     * 计算长度，两个字节长度
     *
     * @param val value
     * @return 结果
     */
    fun twoByte(`val`: String): String {
        var `val` = `val`
        if (`val`.length > 4) {
            `val` = `val`.substring(0, 4)
        } else {
            val l = 4 - `val`.length
            for (i in 0 until l) {
                `val` = "0$`val`"
            }
        }
        return `val`
    }

    /**
     * 校验和
     *
     * @param cmd 指令
     * @return 结果
     */
    fun sum(cmd: String): String? {
        var cmd = cmd
        val cmdList: List<String> = getDivLines(cmd, 2)
        var sumInt = 0
        for (c in cmdList) {
            sumInt += HexToInt(c)
        }
        var sum: String = IntToHex(sumInt)
        sum = twoByte(sum)
        cmd += sum
        return cmd.toUpperCase()
    }

    fun str8ToInt(str: Array<String>): Int {
        var sum = 0
        for (i in str.indices) {
            val value: Int = computerBinary(str[i], str.size - 1 - i)
            sum += value
        }
        return sum
    }

    fun str8ToInt(str: String): Int {
        var sum = 0
        for (i in 0 until str.length) {
            val strIndex = str[i].toString() + ""
            val value: Int = computerBinary(strIndex, str.length - 1 - i)
            sum += value
        }
        return sum
    }

    private fun computerBinary(c: String, index: Int): Int {
        var vaule = Integer.valueOf(c)
        for (i in 0 until index) {
            vaule *= 2
        }
        return vaule
    }

    /**
     * long转byte数组，小端模式
     *
     * @param l
     * @return
     */
    fun longToBytes_Little(l: Long): ByteArray? {
        val b = ByteArray(8)
        b[7] = (0xff and (l shr 56).toInt()).toByte()
        b[6] = (0xff and (l shr 48).toInt()).toByte()
        b[5] = (0xff and (l shr 40).toInt()).toByte()
        b[4] = (0xff and (l shr 32).toInt()).toByte()
        b[3] = (0xff and (l shr 24).toInt()).toByte()
        b[2] = (0xff and (l shr 16).toInt()).toByte()
        b[1] = (0xff and (l shr 8).toInt()).toByte()
        b[0] = (0xff and l.toInt()).toByte()
        return b
    }

    /**
     * byte数组转double
     *
     * @param bytes        8位byte数组
     * @param littleEndian 是否是小端模式
     * @return
     */
    fun bytesToDouble(bytes: ByteArray?, littleEndian: Boolean): Double {
        val buffer = ByteBuffer.wrap(bytes, 0, 8)
        if (littleEndian) {
            // ByteBuffer.order(ByteOrder) 方法指定字节序,即大小端模式(BIG_ENDIAN/LITTLE_ENDIAN)
            // ByteBuffer 默认为大端(BIG_ENDIAN)模式
            buffer.order(ByteOrder.LITTLE_ENDIAN)
        }
        val l = buffer.long
        return java.lang.Double.longBitsToDouble(l)
    }

    /**
     * double转byte数组，大端模式
     *
     * @param d
     * @return
     */
    fun doubleToBytes_Big(d: Double): ByteArray? {
        val l = java.lang.Double.doubleToLongBits(d)
        val b = ByteArray(8)
        b[0] = (0xff and (l shr 56).toInt()).toByte()
        b[1] = (0xff and (l shr 48).toInt()).toByte()
        b[2] = (0xff and (l shr 40).toInt()).toByte()
        b[3] = (0xff and (l shr 32).toInt()).toByte()
        b[4] = (0xff and (l shr 24).toInt()).toByte()
        b[5] = (0xff and (l shr 16).toInt()).toByte()
        b[6] = (0xff and (l shr 8).toInt()).toByte()
        b[7] = (0xff and l.toInt()).toByte()
        return b
    }

    /**
     * double转byte数组，小端模式
     *
     * @param d
     * @return
     */
    fun doubleToBytes_Little(d: Double): ByteArray? {
        val l = java.lang.Double.doubleToLongBits(d)
        val b = ByteArray(8)
        b[7] = (0xff and (l shr 56).toInt()).toByte()
        b[6] = (0xff and (l shr 48).toInt()).toByte()
        b[5] = (0xff and (l shr 40).toInt()).toByte()
        b[4] = (0xff and (l shr 32).toInt()).toByte()
        b[3] = (0xff and (l shr 24).toInt()).toByte()
        b[2] = (0xff and (l shr 16).toInt()).toByte()
        b[1] = (0xff and (l shr 8).toInt()).toByte()
        b[0] = (0xff and l.toInt()).toByte()
        return b
    }

    /**
     * 获得bytes的一段数据
     *
     * @param buff     原byte数组
     * @param startPos 起始位置
     * @param lenth    获取的长度
     * @return 返回获得的byte数组
     */
    fun getFromBuff(buff: ByteArray?, startPos: Int, lenth: Int): ByteArray? {
        val bytes = ByteArray(lenth)
        System.arraycopy(buff!!, startPos, bytes, 0, lenth)
        return bytes
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
    fun addToBuff(buff: ByteArray?, pos: Int, lens: Int, dx: ByteArray?): Int {
        System.arraycopy(dx!!, 0, buff, pos, lens)
        return lens
    }

    fun toLittleEndian(a: Int): Int {
        return a and 0xFF shl 24 or (a shr 8 and 0xFF shl 16) or (a shr 16 and 0xFF shl 8) or (a shr 24 and 0xFF)
    }

    /**
     * 切换大小端续
     */
    fun changeBytes(a: ByteArray): ByteArray? {
        val b = ByteArray(a.size)
        for (i in b.indices) {
            b[i] = a[b.size - i - 1]
        }
        return b
    }

    fun little2Big(value: String): String? {
//        if(CheckNull.isNull(value))
//            return "";
        val bytes: ByteArray = HexToByteArr(value)
        return changeBytes(bytes)?.let { byteArrToHex(it) }
    }

    /**
     * @param md5L16
     * @return
     * @Date:2014-3-18
     * @Author:lulei
     * @Description: 将16位的md5转化为long值
     */
    fun parseMd5L16ToLong(md5L16: String?): Long {
        var md5L16 = md5L16 ?: throw java.lang.NumberFormatException("null")
        md5L16 = md5L16.toLowerCase()
        val bA = md5L16.toByteArray()
        var re = 0L
        for (i in bA.indices) {
            //加下一位的字符时，先将前面字符计算的结果左移4位
            re = re shl 4
            //0-9数组
            var b = (bA[i] - 48).toByte()
            //A-F字母
            if (b > 9) {
                b = (b - 39).toByte()
            }
            //非16进制的字符
            if (b > 15 || b < 0) {
                throw java.lang.NumberFormatException("For input string '$md5L16")
            }
            re += b.toLong()
        }
        return re
    }

    /**
     * @param str16
     * @return
     * @Date:2014-3-18
     * @Author:lulei
     * @Description: 将16进制的字符串转化为long值
     */
    fun parseString16ToLong(str16: String): Long {
        var str16: String? = str16 ?: throw java.lang.NumberFormatException("null")
        //先转化为小写
        str16 = str16?.toLowerCase()
        //如果字符串以0x开头，去掉0x
        str16 = if (str16!!.startsWith("0x")) str16.substring(2) else str16
        if (str16?.length ?:0  >16) {
            throw java.lang.NumberFormatException("For input string '$str16' is to long")
        }
        return parseMd5L16ToLong(str16)
    }

    /** 获取指令异或值
     * @param datas
     * @return
     */
    fun getXOR(datas: ByteArray): Int {
        var temp = datas[1].toInt() // 此处首位取1是因为本协议中第一个数据不参数异或校验，转为int防止结果出现溢出变成负数
        for (i in 2 until datas.size) {
            val preTemp = temp
            var iData: Int
            iData = if (datas[i] < 0) {
                (datas[i] and 0xff.toByte()).toInt() // 变为正数计算
            } else {
                datas[i].toInt()
            }
            if (temp < 0) {
                temp = temp and 0xff // 变为正数
            }
            temp = temp xor iData
            println(preTemp.toString() + "异或" + iData + "=" + temp)
        }
        return temp
    }

    fun getXORHex(hex: String): String? {
        val code: Int = getXOR(HexToByteArr(hex))
        var commXOR = Integer.toHexString(code)
        if (commXOR.length == 1) commXOR = "0$commXOR"
        return commXOR.toUpperCase()
    }


}

//private infix fun Number.shl(i: Int): Int {
//    TODO("Not yet implemented")
//}
