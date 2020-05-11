package com.glodon.tool;

/**
 * Created by lichongmac@163.com on 2019-08-08.
 * @deprecated
 */
public class DebugHelper {
    private static class SingletonHolder {
        private static final DebugHelper INSTANCE = new DebugHelper();
    }

    private DebugHelper() {
    }

    public static final DebugHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public final static String IS_DEBUG = "isDebug";

    public boolean isDebug() {
//        if (BuildConfig.DEBUG) {
//            return true;
//        }
        return SharedPreferencesUtil.Instance().getBoolean(IS_DEBUG);
    }

    public void setDebug(boolean value) {
        SharedPreferencesUtil.Instance().putBoolean(IS_DEBUG, value);
    }
}
