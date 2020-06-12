package com.glodon.glm.kotlintools

import com.glodon.glm.kotlintools.FileUtil.createNewFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by lichongmac@163.com on 2020/6/12.
 */
object LogFileUtil {

    fun writeLogFile(fileName: String?, msg: String, path: String?) {
        val filePath: String = createNewFile(path, fileName)
        var outStream: FileOutputStream? = null
        try {
            outStream = FileOutputStream(File(filePath), true)
            outStream.write(msg.toByteArray(charset("UTF-8")))
            outStream.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (outStream != null) {
                try {
                    outStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}