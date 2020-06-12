package com.glodon.glm.kotlintools

import android.content.Context

/**
 * Created by lichongmac@163.com on 2020/6/11.
 */
object DeviceUtils {
    /**
     * 四舍五入
     */
    private const val DOT_FIVE = 0.5f

    /**
     * dip转换成px
     *
     * @param context Context
     * @param dip     dip Value
     * @return 换算后的px值
     */
    fun dip2px(context: Context, dip: Float): Int {
        val density: Float = getDensity(context)
        return (dip * density + DOT_FIVE) as Int
    }

    /**
     * 得到显示密度
     *
     * @param context Context
     * @return 密度
     */
    fun getDensity(context: Context): Float {
        return context.resources.displayMetrics.density
    }
}