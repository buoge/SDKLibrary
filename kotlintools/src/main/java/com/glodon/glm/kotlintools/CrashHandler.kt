package com.glodon.glm.kotlintools

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Process
import android.util.Log
import java.io.*
import java.util.*

/**
 * Created by lichongmac@163.com on 2020/6/11.
 */
object CrashHandler : Thread.UncaughtExceptionHandler {

    var TAG = "CrashHandler"

    //系统默认的UncaughtException处理类
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null


    //程序的Context对象
    private lateinit var mContext: Context

    //用来存储设备信息和异常信息
    private val infos: Map<String, String> = HashMap()

    private var mRebootListener: RebootListener? = null

    fun getRebootListener(): RebootListener? {
        return mRebootListener
    }

    fun setRebootListener(rebootListener: RebootListener) {
        mRebootListener = rebootListener
    }
    /**
     * 初始化
     *
     * @param context
     */
    fun init(context: Context) {
        mContext = context
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        if (!handleException(e) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler!!.uncaughtException(t, e)
        } else {
            try {
                Thread.sleep(3 * 1000.toLong())
                if (mRebootListener != null) {
                    mRebootListener!!.onReboot()
                }
                //                Intent intent = new Intent(mContext, getTopActivity());
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                mContext.startActivity(intent);
            } catch (e: InterruptedException) {
                Log.i(TAG, e.toString())
            }
            //退出程序
            Process.killProcess(Process.myPid())
            System.exit(1)
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        //收集设备参数信息
        collectDeviceInfo(mContext)
        //保存日志文件
        val errorContent: String = saveCrashInfo2File(ex)
        saveFile(errorContent)
        return true
    }

    /**
     * 保存到文件里
     *
     * @param errorContent
     */
    private fun saveFile(errorContent: String) {
        val command = Runnable {
            val path = Environment.getExternalStorageDirectory().path + "/com.glodon" +
                    "/log/"
            val fileName: String = DateUtils.timeMillis2Date(System.currentTimeMillis(),DateUtils.YYYY_MM_DD_HH_MM) + ".log"
            val filePath: String = FileUtil.createNewFile(path, fileName).toString()
            var outStream: FileOutputStream? = null
            try {
                outStream = FileOutputStream(File(filePath))
                //模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件。
                outStream.write(errorContent.toByteArray(charset("UTF-8")))
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
            Log.d("CHEN", "错误日志路径:-------- $filePath")
        }
        ThreadPoolUtil.getCachedThreadPool()?.execute(command)
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    fun collectDeviceInfo(ctx: Context) {
        val infos: HashMap<String, String> = HashMap()
        try {
            val pm = ctx.packageManager
            val pi = pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                infos.put("versionName", versionName)
                infos.put("versionCode", versionCode)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.d("CHEN", "CrashHandleran.NameNotFoundException---> error occured when collect " +
                    "package info: " + e.message)
        }
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos.put(field.name, field[null].toString())
            } catch (e: Exception) {
                Log.d("CHEN", "CrashHandler.NameNotFoundException---> an error occured when " +
                        "collect crash info: " + e.message)
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String {
        val sb = StringBuffer()
        sb.append("---------------------sta--------------------------\n")
        for ((key, value) in infos) {
            sb.append("$key=$value\n")
        }
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        sb.append("--------------------end---------------------------")
        Log.e(TAG, sb.toString())
        return sb.toString()
    }

}