package com.glodon.glm.kotlintools

import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

/**
 * Created by lichongmac@163.com on 2020/6/12.
 */
object Md5 {
    fun getFileMd5(file: File): String? {
        try {
            val sb = StringBuffer()
            val digest = MessageDigest.getInstance("md5")
            val fin = FileInputStream(file)
            var len = -1
            val buffer = ByteArray(1024) //设置输入流的缓存大小 字节
            //将整个文件全部读入到加密器中
            while (fin.read(buffer).also { len = it } != -1) {
                digest.update(buffer, 0, len)
            }
            //对读入的数据进行加密
            val bytes = digest.digest()
            for (b in bytes) {
                // 数byte 类型转换为无符号的整数
                val n: Byte = b and 0XFF.toByte()
                // 将整数转换为16进制
                val s = Integer.toHexString(n.toInt())
                // 如果16进制字符串是一位，那么前面补0
                if (s.length == 1) {
                    sb.append("0$s")
                } else {
                    sb.append(s)
                }
            }
            return sb.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    //	public static String  getStringMd5(String s){
    //		 char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    //	        try {
    //	            byte[] btInput = s.getBytes();
    //	            // 获得MD5摘要算法的 MessageDigest 对象
    //	            MessageDigest mdInst = MessageDigest.getInstance("MD5");
    //	            // 使用指定的字节更新摘要
    //	            mdInst.update(btInput);
    //	            // 获得密文
    //	            byte[] md = mdInst.digest();
    //	            // 把密文转换成十六进制的字符串形式
    //	            int j = md.length;
    //	            char str[] = new char[j * 2];
    //	            int k = 0;
    //	            for (int i = 0; i < j; i++) {
    //	                byte byte0 = md[i];
    //	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
    //	                str[k++] = hexDigits[byte0 & 0xf];
    //	            }
    //	            return new String(str);
    //	        } catch (Exception e) {
    //	            e.printStackTrace();
    //	            return null;
    //	        }
    //	    }
    /**
     * 明文
     *
     * @return 32位密文
     */
    fun encryption(source: String): String? {
        var re_md5 = String()
        try {
            val md = MessageDigest.getInstance("MD5")
            md.update(source.toByteArray())
            val b = md.digest()
            var i: Int
            val buf = StringBuffer("")
            for (offset in b.indices) {
                i = b[offset].toInt()
                if (i < 0) i += 256
                if (i < 16) buf.append("0")
                buf.append(Integer.toHexString(i))
            }
            re_md5 = buf.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return re_md5.toUpperCase()
    }
}