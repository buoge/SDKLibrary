package com.glodon.tool;

import android.util.Log;

/**
 * Created by zhangtianjie on 2018/8/20.
 */
public class L {
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int NOTHING = 6;
    public static final int level = DEBUG;

    private static boolean isShowGlobal = true;
    private static String globalTag = "global_gate";


    public static void v(String tag, String msg) {
        if (level <= VERBOSE) {
            if (msg == null) return;
            Log.v(tag, msg);
            if (isShowGlobal) {
                Log.v(globalTag, msg);
            }

        }
    }

    public static void d(String tag, String msg) {
        if (level <= DEBUG) {
            if (msg == null) return;
            Log.d(tag, msg);
            if (isShowGlobal) {
                Log.d(globalTag, msg);
            }

          /*  if (null != messageHandler) {

                Message mess = new Message();
                mess.obj = msg;

                messageHandler.sendMessage(mess);
            }*/
        }
    }

    public static void i(String tag, String msg) {
        if (level <= INFO) {
            if (msg == null) return;
            Log.i(tag, msg);
            if (isShowGlobal) {
                Log.i(globalTag, msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (level <= WARN) {
            if (msg == null) return;
            Log.w(tag, msg);
            if (isShowGlobal) {
                Log.w(globalTag, msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (level <= ERROR) {
            if (msg == null) return;
            Log.e(tag, msg);

            if (isShowGlobal) {
                Log.e(globalTag, msg);
            }
        }
    }
}
