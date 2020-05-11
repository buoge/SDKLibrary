package com.glodon.tool.exception;

import com.glodon.tool.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lichongmac@163.com on 2019-12-20.
 */
public class LogFileUtil {

    public static void writeLogFile(String fileName,String msg,String path){

        String filePath = FileUtil.createNewFile(path, fileName);
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(new File(filePath),true);
            outStream.write(msg.getBytes("UTF-8"));
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
