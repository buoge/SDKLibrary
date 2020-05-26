package com.glodon.glm.kotlintools

import java.util.*

/**
 * Created by lichongmac@163.com on 2020/5/26.
 */
object TimeUtils {

    fun formatByMillis(seconds: Long): String {
        var standardTime = ""
        if (seconds <= 0) {
            standardTime = "00:00"
        } else if (seconds < 60) {
            standardTime = String.format(Locale.getDefault()
                    , "00:%02d", seconds % 60)
        } else if (seconds < 60 * 60) {
            standardTime = String.format(Locale.getDefault()
                    , "%02d:%02d"
                    , seconds / 60
                    , seconds % 60
            )
        } else {
            standardTime = String.format(Locale.getDefault()
                    , "%02d:%02d:%02d"
                    , seconds / (60 * 60)
                    , seconds % (60 * 60) / 60
                    , seconds % 60
            )
        }
        return standardTime
    }


}

fun main() {
    println(TimeUtils.formatByMillis(-10L))
    println(TimeUtils.formatByMillis(10L + 1L))
    println(TimeUtils.formatByMillis(60 * 30 + 1L))
    println(TimeUtils.formatByMillis(60 * 60 + 1L))
    println(TimeUtils.formatByMillis(60 * 60 + 60 * 3 + 1L))
    println(TimeUtils.formatByMillis(60 * 60 * 60 + 60 * 3 + 1L))

}