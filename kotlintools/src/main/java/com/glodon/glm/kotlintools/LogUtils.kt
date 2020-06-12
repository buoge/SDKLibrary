package com.glodon.glm.kotlintools

import android.util.Log

/**
 * Created by lichongmac@163.com on 2020/6/12.
 */
object LogUtils {

    const val VERBOSE = 1
    const val DEBUG = 2
    const val INFO = 3
    const val WARN = 4
    const val ERROR = 5
    const val NOTHING = 6
    private var level = DEBUG

    private var isShowGlobal = true
    private const val globalTag = "global_gate"


    fun setLogLevel(l: Int) {
        level = l
    }

    fun setShowGlobal(isShow: Boolean) {
        isShowGlobal = isShow;
    }

    fun v(tag: String?, msg: String?) {
        if (level <= VERBOSE) {
            if (msg == null) return
            Log.v(tag, msg)
            if (isShowGlobal) {
                Log.v(globalTag, msg)
            }
        }
    }

    fun d(tag: String?, msg: String?) {
        if (level <= DEBUG) {
            if (msg == null) return
            Log.d(tag, msg)
            if (isShowGlobal) {
                Log.d(globalTag, msg)
            }

        }
    }

    fun i(tag: String?, msg: String?) {
        if (level <= INFO) {
            if (msg == null) return
            Log.i(tag, msg)
            if (isShowGlobal) {
                Log.i(globalTag, msg)
            }
        }
    }

    fun w(tag: String?, msg: String?) {
        if (level <= WARN) {
            if (msg == null) return
            Log.w(tag, msg)
            if (isShowGlobal) {
                Log.w(globalTag, msg)
            }
        }
    }

    fun e(tag: String?, msg: String?) {
        if (level <= ERROR) {
            if (msg == null) return
            Log.e(tag, msg)
            if (isShowGlobal) {
                Log.e(globalTag, msg)
            }
        }
    }
}