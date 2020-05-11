package com.glodon.tool;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by lichongmac@163.com on 2019-06-11.
 */
public class SDCardHelper {

    //创建照片的路径
    public static String createPhotoPath(Context context, int reqWidth, int reqHeight, String type) {
        String path = getPathWithPackage();
        return path + "/" + System.currentTimeMillis()
                +"_"+reqWidth
                +"_"+reqHeight
                + "."+type;
    }

    public static String getPathWithPackage(){
        return getPath(Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/glodon/");
    }
    //获取SD卡下的一个安全路径
    public static String getPath(String name) {
        File file = new File(name);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        return file.getAbsolutePath();
    }
    //获取SD卡的路径
    public static String getSDPath() {
        return Environment
                .getExternalStorageDirectory().getPath();
    }
}
