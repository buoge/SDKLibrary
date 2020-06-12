package com.glodon.glm.kotlintools

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * Created by lichongmac@163.com on 2020/6/12.
 */
object SDCardUtils {
    //创建照片的路径
    fun createPhotoPath(context: Context?, reqWidth: Int, reqHeight: Int, type: String): String? {
        val path: String = getPathWithPackage().toString()
        return (path + "/" + System.currentTimeMillis()
                + "_" + reqWidth
                + "_" + reqHeight
                + "." + type)
    }

    fun getPathWithPackage(): String? {
        return getPath(Environment.getExternalStorageDirectory()
                .absolutePath + "/glodon/").toString()
    }

    //获取SD卡下的一个安全路径
    fun getPath(name: String?): String? {
        val file = File(name)
        if (!file.exists()) {
            try {
                file.mkdirs()
            } catch (e: Exception) {
                e.printStackTrace()
                return ""
            }
        }
        return file.absolutePath
    }

    //获取SD卡的路径
    fun getSDPath(): String? {
        return Environment
                .getExternalStorageDirectory().path
    }
}