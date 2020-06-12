package com.glodon.glm.kotlintools

import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import java.io.*

/**
 * Created by lichongmac@163.com on 2020/6/11.
 */
object FileUtil {
    private val TAG: String = FileUtil::class.java.getSimpleName()

    /**
     * 读取文件内容，作为字符串返回
     */
    @Throws(IOException::class)
    fun readFileAsString(filePath: String?): String? {
        val file = File(filePath)
        if (!file.exists()) {
            throw FileNotFoundException(filePath)
        }
        if (file.length() > 1024 * 1024 * 1024) {
            throw IOException("File is too large")
        }
        val sb = StringBuilder(file.length().toInt())
        // 创建字节输入流
        val fis = FileInputStream(filePath)
        // 创建一个长度为10240的Buffer
        val bbuf = ByteArray(10240)
        // 用于保存实际读取的字节数
        var hasRead = 0
        while (fis.read(bbuf).also { hasRead = it } > 0) {
            sb.append(String(bbuf, 0, hasRead))
        }
        fis.close()
        return sb.toString()
    }

    /**
     * 根据文件路径读取byte[] 数组
     */
    @Throws(IOException::class)
    fun readFileByBytes(filePath: String): ByteArray? {
        val file = File(filePath)
        return if (!file.exists()) {
            throw FileNotFoundException(filePath)
        } else {
            val bos = ByteArrayOutputStream(file.length().toInt())
            var `in`: BufferedInputStream? = null
            try {
                `in` = BufferedInputStream(FileInputStream(file))
                val bufSize: Short = 1024
                val buffer = ByteArray(bufSize.toInt())
                var len1: Int
                while (-1 != `in`.read(buffer, 0, bufSize.toInt()).also { len1 = it }) {
                    bos.write(buffer, 0, len1)
                }
                bos.toByteArray()
            } finally {
                try {
                    `in`?.close()
                } catch (var14: IOException) {
                    var14.printStackTrace()
                }
                bos.close()
            }
        }
    }

    fun isSdCardAvailable(): Boolean {
        return Environment.getExternalStorageState() == "mounted"
    }

    fun getSDRootFile(): File? {
        return if (isSdCardAvailable()) Environment.getExternalStorageDirectory() else null
    }

    fun getFaceDirectory(): File? {
        val sdRootFile: File? = this!!.getSDRootFile()
        var file: File? = null
        if (sdRootFile != null && sdRootFile.exists()) {
            file = File(sdRootFile, "face/pic")
            if (!file.exists()) {
                val var2 = file.mkdirs()
            }
        }
        return file
    }

    fun getBatchFaceDirectory(batchDir: String?): File? {
        val sdRootFile: File? = getSDRootFile()
        var file: File? = null
        if (sdRootFile != null && sdRootFile.exists()) {
            file = File(sdRootFile, batchDir)
            if (!file.exists()) {
                val var3 = file.mkdirs()
            }
        }
        return file
    }

    fun saveFile(file: File?, bitmap: Bitmap): Boolean {
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            return true
        } catch (var13: Exception) {
            var13.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (var12: Exception) {
                var12.printStackTrace()
            }
        }
        return false
    }

    fun createNewFile(dir: String?, fileName: String?): String? {
        // 文件目录
        val root = File(dir)
        if (!root.isDirectory || !root.exists()) {
            root.mkdirs()
        }
        val myCaptureFile = File(dir, fileName)
        try {
            myCaptureFile.createNewFile()
            return myCaptureFile.absolutePath
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return ""
    }

    /**
     * 复制文件
     *
     * @param source 输入文件
     * @param target 输出文件
     */
    fun copy(source: File?, target: File) {
        if (!target.exists()) {
            createNewFile(target.parent, target.name)
        }
        var fileInputStream: FileInputStream? = null
        var fileOutputStream: FileOutputStream? = null
        try {
            fileInputStream = FileInputStream(source)
            fileOutputStream = FileOutputStream(target)
            val buffer = ByteArray(1024)
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                fileInputStream?.close()
                fileOutputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 复制文件
     *
     * @param target 文件
     * @param bytes  数据
     */
    fun copy(target: File, bytes: ByteArray?) {
        if (!target.exists()) {
            createNewFile(target.parent, target.name)
        }
        try {
            //如果手机已插入sd卡,且app具有读写sd卡的权限
            var output: FileOutputStream? = null
            output = FileOutputStream(target)
            output.write(bytes)
            output.close()
            Log.i(TAG, target.absolutePath)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 删除文件
     */
    fun deleteFile(filePath: String?): Boolean {
        val file = File(filePath)
        return if (file.exists()) {
            file.delete()
        } else false
    }

    /**
     * Java文件操作 获取文件扩展名
     *
     * Created on: 2011-8-2
     * Author: blueeagle
     */
    fun getExtensionName(filename: String?): String? {
        if (filename != null && filename.length > 0) {
            val dot = filename.lastIndexOf('.')
            if (dot > -1 && dot < filename.length - 1) {
                return filename.substring(dot + 1)
            }
        }
        return filename
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     *
     * Created on: 2011-8-2
     * Author: blueeagle
     */
    fun getFileNameNoEx(filename: String?): String? {
        if (filename != null && filename.length > 0) {
            val dot = filename.lastIndexOf('.')
            if (dot > -1 && dot < filename.length) {
                return filename.substring(0, dot)
            }
        }
        return filename
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val fileName = "5000052-1562817303281.jpg"
        println(fileName)
        println("获取文件扩展名:" + getExtensionName(fileName))
        println("获取不带扩展名的文件名:" + getFileNameNoEx(fileName))
    }
}