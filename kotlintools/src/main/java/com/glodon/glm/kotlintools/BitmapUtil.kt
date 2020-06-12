package com.glodon.glm.kotlintools

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import android.util.Log
import java.io.*

/**
 * Created by lichongmac@163.com on 2020/6/11.
 */
object BitmapUtil {
     val TAG: String = BitmapUtil::class.java.getSimpleName()

    fun rgb2YCbCr420(pixels: IntArray, width: Int, height: Int): ByteArray {
        val len = width * height

        //yuv格式数组大小，y亮度占len长度，u,v各占len/4长度。
        val yuv = ByteArray(len * 3 / 2)
        var y: Int
        var u: Int
        var v: Int
        for (i in 0 until height) {
            for (j in 0 until width) {
                //屏蔽ARGB的透明度值
                val rgb = pixels[i * width + j] and 0x00FFFFFF

                //像素的颜色顺序为bgr，移位运算。
                val r = rgb and 0xFF
                val g = rgb shr 8 and 0xFF
                val b = rgb shr 16 and 0xFF

                //套用公式
                y = (66 * r + 129 * g + 25 * b + 128 shr 8) + 16
                u = (-38 * r - 74 * g + 112 * b + 128 shr 8) + 128
                v = (112 * r - 94 * g - 18 * b + 128 shr 8) + 128

                //调整
                y = if (y < 16) 16 else if (y > 255) 255 else y
                u = if (u < 0) 0 else if (u > 255) 255 else u
                v = if (v < 0) 0 else if (v > 255) 255 else v

                //赋值
                yuv[i * width + j] = y.toByte()
                yuv[len + (i shr 1) * width + (j and 1.inv()) + 0] = u.toByte()
                yuv[len + +(i shr 1) * width + (j and 1.inv()) + 1] = v.toByte()
            }
        }
        return yuv
    }

    fun yCbCr2Rgb(yuv: ByteArray, width: Int, height: Int) {
        Log.d(TAG, "yCbCr2Rgb called!")
        val frameSize = width * height
        val rgba = IntArray(frameSize)
        for (i in 0 until height) {
            for (j in 0 until width) {
                var y = 0xff and yuv[i * width + j].toInt()
                val u = 0xff and yuv[frameSize + (i shr 1) * width + (j and 1.inv()) + 0].toInt()
                val v = 0xff and yuv[frameSize + (i shr 1) * width + (j and 1.inv()) + 1].toInt()
                y = if (y < 16) 16 else y
                var r = Math.round(1.166f * (y - 16) + 1.596f * (v - 128))
                var g = Math.round(1.164f * (y - 16) - 0.813f * (v - 128) - 0.391f * (u - 128))
                var b = Math.round(1.164f * (y - 16) + 2.018f * (u - 128))
                r = if (r < 0) 0 else if (r > 255) 255 else r
                g = if (g < 0) 0 else if (g > 255) 255 else g
                b = if (b < 0) 0 else if (b > 255) 255 else b
                rgba[i * width + j] = -0x1000000 + (b shl 16) + (g shl 8) + r
            }
        }
        val bmp = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888)
        bmp.setPixels(rgba, 0, width, 0, 0, width, height)
        val bmpName = "test.jpg"
        val path = Environment.getExternalStorageDirectory()
                .absolutePath + "/scan_test"

        // 文件目录
        val root = File(path)
        if (!root.isDirectory || !root.exists()) {
            root.mkdirs()
        }
        val myCaptureFile = File(path, bmpName)
        try {
            myCaptureFile.createNewFile()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        try {
            val bos = BufferedOutputStream(
                    FileOutputStream(myCaptureFile))

            // 采用压缩转档方法
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, bos)
            bos.flush()
            bos.close()
            Log.d(TAG, " 保存文件路径：" + myCaptureFile.absolutePath)
        } catch (e: Exception) {
            myCaptureFile.delete()
        }
    }

    fun argbInts2File(pixels: IntArray, width: Int, height: Int) {
        val yuv: ByteArray = rgb2YCbCr420(pixels, width, height)
       yCbCr2Rgb(yuv, width, height)
    }

    fun pixels2File(pixels: IntArray, width: Int, height: Int, x: Int, y: Int): String? {
        val bmp: Bitmap = pixels2Bitmap(pixels, width, height, x, y)
        val bmpName = "test.jpg"
        val path = Environment.getExternalStorageDirectory()
                .absolutePath + "/scan_test"

        // 文件目录
        val root = File(path)
        if (!root.isDirectory || !root.exists()) {
            root.mkdirs()
        }
        val myCaptureFile = File(path, bmpName)
        try {
            myCaptureFile.createNewFile()
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        try {
            val bos = BufferedOutputStream(
                    FileOutputStream(myCaptureFile))

            // 采用压缩转档方法
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, bos)
            bos.flush()
            bos.close()
            Log.d(TAG, " 保存文件路径：" + myCaptureFile.absolutePath)
            return myCaptureFile.absolutePath
        } catch (e: java.lang.Exception) {
            myCaptureFile.delete()
        } finally {
            bmp?.recycle()
        }
        return ""
    }

    fun pixels2Bitmap(pixels: IntArray?, width: Int, height: Int, x: Int, y: Int): Bitmap{
        val bmp = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888)
        bmp.setPixels(pixels, 0, width, x, y, width, height)
        return bmp
    }

    fun pixels2Bitmap(pixels: IntArray?, width: Int, height: Int): Bitmap {
        return pixels2Bitmap(pixels, width, height, 0, 0)
    }

    fun clipBitmap(bitmap: Bitmap, width: Float, height: Float, x: Float, y: Float): Bitmap? {
        var x = x
        var y = y
        val w = bitmap.width // 得到图片的宽，高
        val h = bitmap.height
        if (x + width > w) {
            x = w - width
        }
        if (y + height > height) {
            y = h - height
        }
        Log.i(TAG, "width=" + width
                + " height = " + height
                + " x=" + x
                + " y=" + y
        )
        return Bitmap.createBitmap(bitmap, x.toInt(),
                y.toInt(), width.toInt(), height.toInt())
    }

    fun bitmap2File(filePath: String, mBitmap: Bitmap, quality: Int) {
        val file = File(filePath)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(file)
            mBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)
            Log.d(TAG, "已经保存到$filePath")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Log.d(TAG, "保存失败到" + filePath + e.message)
        }
        try {
            out!!.flush()
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d(TAG, "保存发生异常" + filePath + e.message)
        }
    }
    fun bitmap2Array(argb: IntArray, width: Int, height: Int): ByteArray? {
        val bm: Bitmap = pixels2Bitmap(argb, width, height)
        return bitmap2Array(bm)
    }
    fun bitmap2Array(bitmap: Bitmap): ByteArray? {
        if (bitmap == null)
            throw NullPointerException(" bitmap is null ")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        return baos.toByteArray()
    }

    /*
     * bitmap转base64
     * */
    fun bitmapToBase64(bitmap: Bitmap?): String? {
        var result: String? = null
        var baos: ByteArrayOutputStream? = null
        try {
            if (bitmap != null) {
                baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                baos.flush()
                baos.close()
                val bitmapBytes = baos.toByteArray()
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (baos != null) {
                    baos.flush()
                    baos.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return result
    }
    /*end*/
    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    fun base64ToBitmap(base64Data: String?): Bitmap? {
        val bytes = Base64.decode(base64Data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }
}