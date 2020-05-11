package com.glodon.tool;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 图片处理工具
 * Created by lichongmac@163.com on 2019-07-04.
 */
public class BitmapUtil {
    private static final String TAG = BitmapUtil.class.getSimpleName();

    public static byte[] rgb2YCbCr420(int[] pixels, int width, int height) {

        int len = width * height;

        //yuv格式数组大小，y亮度占len长度，u,v各占len/4长度。

        byte[] yuv = new byte[len * 3 / 2];

        int y, u, v;

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {

//屏蔽ARGB的透明度值

                int rgb = pixels[i * width + j] & 0x00FFFFFF;

                //像素的颜色顺序为bgr，移位运算。

                int r = rgb & 0xFF;

                int g = (rgb >> 8) & 0xFF;

                int b = (rgb >> 16) & 0xFF;

                //套用公式

                y = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;

                u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;

                v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;

                //调整

                y = y < 16 ? 16 : (y > 255 ? 255 : y);

                u = u < 0 ? 0 : (u > 255 ? 255 : u);

                v = v < 0 ? 0 : (v > 255 ? 255 : v);

                //赋值

                yuv[i * width + j] = (byte) y;

                yuv[len + (i >> 1) * width + (j & ~1) + 0] = (byte) u;

                yuv[len + +(i >> 1) * width + (j & ~1) + 1] = (byte) v;

            }

        }

        return yuv;

    }


    public static void yCbCr2Rgb(byte[] yuv, int width, int height) {
        Log.d(TAG, "yCbCr2Rgb called!");
        int frameSize = width * height;

        int[] rgba = new int[frameSize];

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {

                int y = (0xff & ((int) yuv[i * width + j]));

                int u = (0xff & ((int) yuv[frameSize + (i >> 1) * width

                        + (j & ~1) + 0]));

                int v = (0xff & ((int) yuv[frameSize + (i >> 1) * width

                        + (j & ~1) + 1]));


                y = y < 16 ? 16 : y;


                int r = Math.round(1.166f * (y - 16) + 1.596f * (v - 128));

                int g = Math.round(1.164f * (y - 16) - 0.813f * (v - 128)

                        - 0.391f * (u - 128));

                int b = Math.round(1.164f * (y - 16) + 2.018f * (u - 128));


                r = r < 0 ? 0 : (r > 255 ? 255 : r);

                g = g < 0 ? 0 : (g > 255 ? 255 : g);

                b = b < 0 ? 0 : (b > 255 ? 255 : b);


                rgba[i * width + j] = 0xff000000 + (b << 16) + (g << 8) + r;

            }

        }

        Bitmap bmp = Bitmap.createBitmap(width, height,

                Bitmap.Config.ARGB_8888);

        bmp.setPixels(rgba, 0, width, 0, 0, width, height);

        String bmpName = "test.jpg";

        String path = Environment.getExternalStorageDirectory()

                .getAbsolutePath() + "/scan_test";

        // 文件目录

        File root = new File(path);

        if (!root.isDirectory() || !root.exists()) {

            root.mkdirs();

        }

        File myCaptureFile = new File(path, bmpName);

        try {

            myCaptureFile.createNewFile();

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {

            BufferedOutputStream bos = new BufferedOutputStream(

                    new FileOutputStream(myCaptureFile));

            // 采用压缩转档方法

            bmp.compress(Bitmap.CompressFormat.JPEG, 85, bos);

            bos.flush();

            bos.close();

            Log.d(TAG, " 保存文件路径：" + myCaptureFile.getAbsolutePath());

        } catch (Exception e) {

            myCaptureFile.delete();

        }

    }

    public static void argbInts2File(int[] pixels, int width, int height) {
        byte[] yuv = rgb2YCbCr420(pixels, width, height);
        yCbCr2Rgb(yuv, width, height);
    }

    public static String pixels2File(int[] pixels, int width, int height, int x, int y) {
        Bitmap bmp = pixels2Bitmap(pixels, width, height, x, y);

        String bmpName = "test.jpg";

        String path = Environment.getExternalStorageDirectory()

                .getAbsolutePath() + "/scan_test";

        // 文件目录

        File root = new File(path);

        if (!root.isDirectory() || !root.exists()) {

            root.mkdirs();

        }

        File myCaptureFile = new File(path, bmpName);

        try {

            myCaptureFile.createNewFile();

        } catch (IOException e1) {
            e1.printStackTrace();

        }

        try {

            BufferedOutputStream bos = new BufferedOutputStream(

                    new FileOutputStream(myCaptureFile));

            // 采用压缩转档方法
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, bos);

            bos.flush();

            bos.close();

            Log.d(TAG, " 保存文件路径：" + myCaptureFile.getAbsolutePath());
            return myCaptureFile.getAbsolutePath();
        } catch (Exception e) {

            myCaptureFile.delete();

        } finally {
            if (bmp != null) {
                bmp.recycle();
            }
        }
        return "";
    }

    public static Bitmap pixels2Bitmap(int[] pixels, int width, int height, int x, int y) {
        Bitmap bmp = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bmp.setPixels(pixels, 0, width, x, y, width, height);

        return bmp;
    }

    public static Bitmap pixels2Bitmap(int[] pixels, int width, int height) {
        return pixels2Bitmap(pixels, width, height, 0, 0);
    }

    public static Bitmap clipBitmap(Bitmap bitmap, float width, float height, float x, float y) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();

        if(x+width>w) {
            x=w-width;
        }
        if(y+height>height) {
            y=h-height;
        }
        Log.i(TAG, "width=" + width
                + " height = " + height
                + " x=" + x
                + " y=" + y
        );
        return Bitmap.createBitmap(bitmap, (int) x,
                (int) y, (int) width, (int) height);
    }

    public static void bitmap2File(String filePath, Bitmap mBitmap, int quality) {

        File file = new File(filePath);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            L.d(TAG, "已经保存到" + filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            L.d(TAG, "保存失败到" + filePath + e.getMessage());

        }
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            L.d(TAG, "保存发生异常" + filePath + e.getMessage());
        }


    }

    public static byte[] bitmap2Array(int[] argb, int width, int height) {
        Bitmap bm=pixels2Bitmap(argb,width,height);

        return bitmap2Array(bm);

    }

    public static byte[] bitmap2Array(Bitmap bitmap){

        if(bitmap==null)
            throw new NullPointerException(" bitmap is null ");


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        return baos.toByteArray();

    }

    /*
     * bitmap转base64
     * */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /*end*/

    /**
     * base64转为bitmap
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
