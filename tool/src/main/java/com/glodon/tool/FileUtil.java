package com.glodon.tool;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件读取工具类
 */
public class FileUtil {
private static final String TAG = FileUtil.class.getSimpleName();
    /**
     * 读取文件内容，作为字符串返回
     */
    public static String readFileAsString(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        if (file.length() > 1024 * 1024 * 1024) {
            throw new IOException("File is too large");
        }

        StringBuilder sb = new StringBuilder((int) (file.length()));
        // 创建字节输入流  
        FileInputStream fis = new FileInputStream(filePath);
        // 创建一个长度为10240的Buffer
        byte[] bbuf = new byte[10240];
        // 用于保存实际读取的字节数  
        int hasRead = 0;
        while ((hasRead = fis.read(bbuf)) > 0) {
            sb.append(new String(bbuf, 0, hasRead));
        }
        fis.close();
        return sb.toString();
    }

    /**
     * 根据文件路径读取byte[] 数组
     */
    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

                bos.close();
            }
        }
    }

    public static boolean isSdCardAvailable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static File getSDRootFile() {
        return isSdCardAvailable() ? Environment.getExternalStorageDirectory() : null;
    }

    public static File getFaceDirectory() {
        File sdRootFile = getSDRootFile();
        File file = null;
        if (sdRootFile != null && sdRootFile.exists()) {
            file = new File(sdRootFile, "face/pic");
            if (!file.exists()) {
                boolean var2 = file.mkdirs();
            }
        }

        return file;
    }

    public static File getBatchFaceDirectory(String batchDir) {
        File sdRootFile = getSDRootFile();
        File file = null;
        if (sdRootFile != null && sdRootFile.exists()) {
            file = new File(sdRootFile, batchDir);
            if (!file.exists()) {
                boolean var3 = file.mkdirs();
            }
        }

        return file;
    }

    public static boolean saveFile(File file, Bitmap bitmap) {
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            boolean var3 = true;
            return var3;
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception var12) {
                var12.printStackTrace();
            }

        }

        return false;
    }

    public static String createNewFile(String dir, String fileName) {
        // 文件目录

        File root = new File(dir);

        if (!root.isDirectory() || !root.exists()) {

            root.mkdirs();

        }

        File myCaptureFile = new File(dir, fileName);

        try {

            myCaptureFile.createNewFile();
            return myCaptureFile.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();

        }
        return "";
    }


    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    public static void copy(File source, File target) {

        if (!target.exists()) {
            FileUtil.createNewFile(target.getParent(), target.getName());
        }

        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(target);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制文件
     *
     * @param target 文件
     * @param bytes  数据
     */
    public static void copy(File target, byte[] bytes) {
        if (!target.exists()) {
            FileUtil.createNewFile(target.getParent(), target.getName());
        }
        try {
            //如果手机已插入sd卡,且app具有读写sd卡的权限
            FileOutputStream output = null;
            output = new FileOutputStream(target);
            output.write(bytes);
            output.close();
            Log.i(TAG,target.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     * */
    public static boolean deleteFile(String filePath) {
        File file=new File(filePath);
        if(file.exists()) {
            return file.delete();
        }
        return false;
    }


    /**
     * Java文件操作 获取文件扩展名
     *
     *  Created on: 2011-8-2
     *      Author: blueeagle
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
    /**
     * Java文件操作 获取不带扩展名的文件名
     *
     *  Created on: 2011-8-2
     *      Author: blueeagle
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static void main(String[] args){
        String fileName="5000052-1562817303281.jpg";
        System.out.println(fileName);

        System.out.println("获取文件扩展名:"+getExtensionName(fileName));
        System.out.println("获取不带扩展名的文件名:"+getFileNameNoEx(fileName));

    }
}
