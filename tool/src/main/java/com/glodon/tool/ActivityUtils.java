package com.glodon.tool;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;

import static android.content.Context.WIFI_SERVICE;

/**
 * 说明：
 * Created by lic-w
 *
 * @email lic-w@glodon.com
 * @date 2019/5/14 14:05
 **/
public class ActivityUtils {
private static final String TAG = ActivityUtils.class.getSimpleName();
    public static void showSnackbarShort(Activity activity, String content) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), content,
                Snackbar.LENGTH_SHORT)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        snackbar.show();
    }

    public static Snackbar showSnackbarLong(Activity activity, String content) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), content,
                Snackbar.LENGTH_LONG)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        snackbar.show();
        return snackbar;
    }
//
    public static Snackbar showSnackbarIndefinite(Activity activity, String content) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), content,
                Snackbar.LENGTH_INDEFINITE)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        snackbar.show();
        return snackbar;
    }
    public static void showToast(Context context,String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String getNetGate(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(WIFI_SERVICE);
        DhcpInfo di = wm.getDhcpInfo();
        long getewayIpL = di.gateway;
        String getwayIpS = long2ip(getewayIpL);//网关地址
        long netmaskIpL = di.netmask;
        String netmaskIpS = long2ip(netmaskIpL);//子网掩码地址
        return long2ip(getewayIpL);

    }

    private static String long2ip(long ip) {
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf((int) (ip & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 8) & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 16) & 0xff)));
        sb.append('.');
        sb.append(String.valueOf((int) ((ip >> 24) & 0xff)));
        return sb.toString();
    }

    //=============device Info

    public static String getIccid(Context context) {
        String iccid = "";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        } else {
            iccid = tm.getSimSerialNumber();//取出ICCID
        }

        Log.d(TAG, "iccid:" + iccid);
        return iccid;

    }

    public static int getLocalVersionCode(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            L.d(TAG, "本软件的版本号：" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    public static String getLocalVersionName(Context ctx) {
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            return "v"+packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * check if the network connected or not
     *
     * @param  context
     * @return true: connected, false:not, null:unknown
     */
    public static boolean isNetworkConnected(Context context) {
        try {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm != null) {
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    return true;
                }
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return false;
    }

    public static int getLocalVersion(Context ctx) {
        int localVersion = 0;
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionCode;
            L.d(TAG, "本软件的版本号：" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    /**
     * 获取设备id
     *
     * @return
     */
    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
