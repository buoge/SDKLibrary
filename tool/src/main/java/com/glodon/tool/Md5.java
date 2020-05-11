package com.glodon.tool;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    public static String getFileMd5(File file) {
        try {
            StringBuffer sb = new StringBuffer();
            MessageDigest digest = MessageDigest.getInstance("md5");
            FileInputStream fin = new FileInputStream(file);
            int len = -1;
            byte[] buffer = new byte[1024];//设置输入流的缓存大小 字节
            //将整个文件全部读入到加密器中
            while ((len = fin.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            //对读入的数据进行加密
            byte[] bytes = digest.digest();
            for (byte b : bytes) {
                // 数byte 类型转换为无符号的整数
                int n = b & 0XFF;
                // 将整数转换为16进制
                String s = Integer.toHexString(n);
                // 如果16进制字符串是一位，那么前面补0
                if (s.length() == 1) {
                    sb.append("0" + s);
                } else {
                    sb.append(s);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    public static String encryption(String source) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5.toUpperCase();
    }

}

