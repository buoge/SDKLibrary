package com.glodon.glm.kotlintools

import android.Manifest
import android.R
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar

/**
 * Created by lichongmac@163.com on 2020/6/11.
 */
object ActivityUtils {
    private val TAG: String = ActivityUtils::class.java.getSimpleName()

    fun showSnackbarShort(activity: Activity, content: String) {
        val snackbar: Snackbar = Snackbar.make(activity.findViewById(R.id.content), content,
                Snackbar.LENGTH_SHORT)
                .setAction("确定", { })
        snackbar.show()
    }

    fun showSnackbarLong(activity: Activity, content: String): Snackbar? {
        val snackbar = Snackbar.make(activity.findViewById(R.id.content), content,
                Snackbar.LENGTH_LONG)
                .setAction("确定") { }
        snackbar.show()
        return snackbar
    }

    fun showSnackbarIndefinite(activity: Activity, content: String): Snackbar? {
        val snackbar = Snackbar.make(activity.findViewById(R.id.content), content,
                Snackbar.LENGTH_INDEFINITE)
                .setAction("确定") { }
        snackbar.show()
        return snackbar
    }

    fun showToast(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    //    TODO 移动到DeviceUtils
    fun getNetGate(context: Context): String? {
        val wm = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val di = wm.dhcpInfo
        val getewayIpL = di.gateway.toLong()
        val getwayIpS: String = long2ip(getewayIpL) //网关地址
        val netmaskIpL = di.netmask.toLong()
        val netmaskIpS: String = long2ip(netmaskIpL) //子网掩码地址
        return long2ip(getewayIpL)
    }

    fun long2ip(ip: Long): String {
        val sb = StringBuffer()
        sb.append((ip and 0xff) as Int)
        sb.append('.')
        sb.append((ip shr 8 and 0xff) as Int)
        sb.append('.')
        sb.append((ip shr 16 and 0xff) as Int)
        sb.append('.')
        sb.append((ip shr 24 and 0xff) as Int)
        return sb.toString()
    }

    //=============device Info
    fun getIccid(context: Context): String? {
        var iccid = ""
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        } else {
            iccid = tm.simSerialNumber //取出ICCID
        }
        Log.d(TAG, "iccid:$iccid")
        return iccid
    }

    fun getLocalVersionCode(ctx: Context): Int {
        var localVersion = 0
        try {
            val packageInfo = ctx.applicationContext
                    .packageManager
                    .getPackageInfo(ctx.packageName, 0)
            localVersion = packageInfo.versionCode
            Log.d(TAG, "本软件的版本号：$localVersion")
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return localVersion
    }

    fun getLocalVersionName(ctx: Context): String? {
        try {
            val packageInfo = ctx.applicationContext
                    .packageManager
                    .getPackageInfo(ctx.packageName, 0)
            return "v" + packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * check if the network connected or not
     *
     * @param context
     * @return true: connected, false:not, null:unknown
     */
    fun isNetworkConnected(context: Context): Boolean {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (cm != null) {
                val networkInfo = cm.activeNetworkInfo
                if (networkInfo != null && networkInfo.isConnected) {
                    return true
                }
            }
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
        return false
    }

    /**
     * 获取运营商名字
     *
     * @param context context
     * @return int
     */
    fun getOperatorName(context: Context): String? {
        /*
         * getSimOperatorName()就可以直接获取到运营商的名字
         * 也可以使用IMSI获取，getSimOperator()，然后根据返回值判断，例如"46000"为移动
         * IMSI相关链接：http://baike.baidu.com/item/imsi
         */
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        // getSimOperatorName就可以直接获取到运营商的名字
        return telephonyManager.simOperatorName
    }

    const val NETWORK_NONE = 0 // 没有网络连接

    const val NETWORK_WIFI = 1 // wifi连接

    const val NETWORK_2G = 2 // 2G

    const val NETWORK_3G = 3 // 3G

    const val NETWORK_4G = 4 // 4G

    const val NETWORK_MOBILE = 5 // 手机流量

    /**
     * 获取当前网络连接的类型
     *
     * @param context context
     * @return int
     */
    fun getNetworkState(context: Context): Int {
        val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // 获取网络服务
        // 获取网络类型，如果为空，返回无网络
        val activeNetInfo = connManager.activeNetworkInfo
        if (activeNetInfo == null || !activeNetInfo.isAvailable) {
            return NETWORK_NONE
        }
        // 判断是否为WIFI
        val wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (null != wifiInfo) {
            val state = wifiInfo.state
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    return NETWORK_WIFI
                }
            }
        }
        // 若不是WIFI，则去判断是2G、3G、4G网
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val networkType = telephonyManager.networkType
        return when (networkType) {
            TelephonyManager.NETWORK_TYPE_GPRS
                , TelephonyManager.NETWORK_TYPE_CDMA
                , TelephonyManager.NETWORK_TYPE_EDGE
                , TelephonyManager.NETWORK_TYPE_1xRTT
                , TelephonyManager.NETWORK_TYPE_IDEN -> NETWORK_2G
            TelephonyManager.NETWORK_TYPE_EVDO_A
                , TelephonyManager.NETWORK_TYPE_UMTS
                , TelephonyManager.NETWORK_TYPE_EVDO_0
                , TelephonyManager.NETWORK_TYPE_HSDPA
                , TelephonyManager.NETWORK_TYPE_HSUPA
                , TelephonyManager.NETWORK_TYPE_HSPA
                , TelephonyManager.NETWORK_TYPE_EVDO_B
                , TelephonyManager.NETWORK_TYPE_EHRPD
                , TelephonyManager.NETWORK_TYPE_HSPAP -> NETWORK_3G
                 TelephonyManager.NETWORK_TYPE_LTE -> NETWORK_4G
            else -> NETWORK_MOBILE
        }
    }

    /**
     * 判断是否wifi连接
     *
     * @param context context
     * @return true/false
     */
    @Synchronized
    fun isWifiConnected(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val networkInfo = connectivityManager.activeNetworkInfo
                if (networkInfo != null) {
                    val networkInfoType = networkInfo.type
                    if (networkInfoType == ConnectivityManager.TYPE_WIFI || networkInfoType == ConnectivityManager.TYPE_ETHERNET) {
                        return networkInfo.isConnected
                    }
                }
            }
        } else {
            //获得ConnectivityManager对象
            val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            //获取所有网络连接的信息
            val networks = connMgr.allNetworks
            //用于存放网络连接信息
            for (i in networks.indices) {
                val networkInfo = connMgr.getNetworkInfo(networks[i])
                if (null == networkInfo) {
                    Log.w(TAG, "networkInfo is null! index=$i")
                    continue
                }
                Log.i(TAG, networkInfo.typeName + " connect is " + networkInfo.isConnected)
                if ("WIFI" == networkInfo.typeName && networkInfo.isConnected) {
                    return true
                }
            }
        }
        return false
    }

    fun getNetworkWifiLevel(context: Context): Int {
        if (!isWifiConnected(context)) {
            return 0
        }
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        //获得信号强度值
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val level = wifiInfo.rssi
            //根据获得信号的强度发送信息
            return if (level <= 0 && level >= -50) { //最强
                println("level==========1===========$level")
                1
            } else if (level < -50 && level >= -70) { //较强
                println("level===========2==========$level")
                2
            } else if (level < -70 && level >= -80) { //较弱
                println("level==========3===========$level")
                3
            } else if (level < -80 && level >= -100) { //微弱
                println("level==========4===========$level")
                4
            } else {
                println("level==========5===========$level")
                5
            }
        }
        return 0
    }

    /**
     * 判断是否开启了自动亮度调节
     */
    fun isAutoBrightness(aContentResolver: ContentResolver?): Boolean {
        var automicBrightness = false
        try {
            automicBrightness = Settings.System.getInt(aContentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
        return automicBrightness
    }

    /**
     * 获取屏幕的亮度
     *
     * @param activity
     * @return
     */
    fun getScreenBrightness(activity: Activity): Int {
        var nowBrightnessValue = 0
        val resolver = activity.contentResolver
        try {
            nowBrightnessValue = Settings.System.getInt(
                    resolver, Settings.System.SCREEN_BRIGHTNESS)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return nowBrightnessValue
    }

    /**
     * 设置亮度
     *
     * @param activity
     * @param brightness
     */
    fun setBrightness(activity: Activity, brightness: Int) {
        // Settings.System.putInt(activity.getContentResolver(),

        // Settings.System.SCREEN_BRIGHTNESS_MODE,

        // Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        val lp = activity.window.attributes
        lp.screenBrightness = java.lang.Float.valueOf(brightness.toFloat()) * (1f / 255f)
        Log.d("lxy", "set  lp.screenBrightness == " + lp.screenBrightness)
        activity.window.attributes = lp
    }
    // 那么，能设置了，但是为什么还是会出现，设置了，没反映呢？
    // 嘿嘿，那是因为，开启了自动调节功能了，那如何关闭呢？这才是最重要的：
    /**
     * 停止自动亮度调节
     *
     * @param activity
     */
    fun stopAutoBrightness(activity: Activity) {
        Settings.System.putInt(activity.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)
    }

    /**
     * 开启亮度自动调节
     *
     * @param activity
     */
    fun startAutoBrightness(activity: Activity) {
        Settings.System.putInt(activity.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC)
    }

    // 至此，应该说操作亮度的差不多都有了，结束！
    // 哎，本来认为是应该结束了，但是悲剧得是，既然像刚才那样设置的话，只能在当前的activity中有作用，一段退出的时候，会发现毫无作用，悲剧，原来是忘记了保存了。汗！

    // 至此，应该说操作亮度的差不多都有了，结束！
    // 哎，本来认为是应该结束了，但是悲剧得是，既然像刚才那样设置的话，只能在当前的activity中有作用，一段退出的时候，会发现毫无作用，悲剧，原来是忘记了保存了。汗！
    /**
     * 保存亮度设置状态
     *
     * @param resolver
     * @param brightness
     */
    fun saveBrightness(resolver: ContentResolver, brightness: Int) {
        val uri = Settings.System
                .getUriFor("screen_brightness")
        Settings.System.putInt(resolver, "screen_brightness",
                brightness)
        // resolver.registerContentObserver(uri, true, myContentObserver);
        resolver.notifyChange(uri, null)
    }

}