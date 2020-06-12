package com.glodon.glm.kotlintools

import kotlin.experimental.and

/**
 * Created by lichongmac@163.com on 2020/6/11.
 */
object Base64Util {
    private val last2byte = "00000011".toInt(2).toChar()
    private val last4byte = "00001111".toInt(2).toChar()
    private val last6byte = "00111111".toInt(2).toChar()
    private val lead6byte = "11111100".toInt(2).toChar()
    private val lead4byte = "11110000".toInt(2).toChar()
    private val lead2byte = "11000000".toInt(2).toChar()
    private val encodeTable = charArrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/')

//    TODO 单元测试估测边界
    fun encode(from: ByteArray): String? {
        val to = StringBuilder((from.size.toDouble() * 1.34).toInt() + 3)
        var num = 0
        var currentByte = 0.toChar()
        var i: Int
        i = 0
        while (i < from.size) {
            num %= 8
            while (num < 8) {
                when (num) {
                    0 -> {
                        currentByte = (from[i] and lead6byte.toByte()) as Char
                        currentByte = (currentByte.toInt() ushr 2).toChar()
                    }
                    1, 3, 5 -> {
                    }
                    2 -> currentByte = (from[i] and last6byte.toByte()) as Char
                    4 -> {
                        currentByte = (from[i] and last4byte.toByte()) as Char
                        currentByte = (currentByte.toInt() shl 2).toChar()
                        if (i + 1 < from.size) {
                            currentByte = (currentByte.toInt() or ((from[i + 1] and lead2byte.toByte()).toInt()) ushr 6).toChar()
                        }
                    }
                    6 -> {
                        currentByte = (from[i] and last2byte.toByte()) as Char
                        currentByte = (currentByte.toInt() shl 4).toChar()
                        if (i + 1 < from.size) {
                            currentByte = (currentByte.toInt() or ((from[i + 1] and lead4byte.toByte()).toInt()) ushr 4).toChar()
                        }
                    }
                    else -> {
                    }
                }
                to.append(encodeTable.get(currentByte.toInt()))
                num += 6
            }
            ++i
        }
        if (to.length % 4 != 0) {
            i = 4 - to.length % 4
            while (i > 0) {
                to.append("=")
                --i
            }
        }
        return to.toString()
    }
}