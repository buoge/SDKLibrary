package com.glodon.glm.kotlintools

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by lichongmac@163.com on 2020/6/12.
 */
object SharedPreferencesUtil {
    private const val KEY = "com.glodon.glm.sharePrefer"
    private var mSharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.applicationContext.getSharedPreferences(KEY, 0)
        }
    }

    /**
     * str: md5+isTracing
     */
    fun put(id: String, str: String) {
        mSharedPreferences!!.edit().putString(id, str).commit()
    }

    operator fun get(id: String?): String? {
        return mSharedPreferences!!.getString(id, "")
    }

    fun getMd5(id: String?): String? {
        val str = mSharedPreferences!!.getString(id, "")
        return if ("" != str) {
            str!!.substring(0, str.length - 1)
        } else ""
    }

    fun getIsTracing(id: String?): Int {
        val str = mSharedPreferences!!.getString(id, "")
        return if (str!!.isNotEmpty()) {
            Integer.valueOf(str.substring(str.length - 1), str.length)
        } else 1
    }

    fun remove(id: String?) {
        mSharedPreferences!!.edit().remove(id).commit()
    }

    fun getIds(): Set<String?>? {
        return mSharedPreferences!!.all.keys
    }

    fun clear() {
        mSharedPreferences!!.edit().clear().commit()
    }

    fun getBoolean(key: String?): Boolean {
        return mSharedPreferences!!.getBoolean(key, false)
    }

    fun getBooleanDefaultFalse(key: String?): Boolean {
        return mSharedPreferences!!.getBoolean(key, false)
    }

    fun putInt(mainPort: String?, port: Int): Boolean {
        return mSharedPreferences!!.edit().putInt(mainPort, port).commit()
    }

    fun getInt(key: String?): Int {
        return mSharedPreferences!!.getInt(key, 0)
    }


    fun putFloat(mainPort: String?, port: Float): Boolean {
        return mSharedPreferences!!.edit().putFloat(mainPort, port).commit()
    }

    fun getFloat(key: String?): Float {
        return mSharedPreferences!!.getFloat(key, 0f)
    }


}