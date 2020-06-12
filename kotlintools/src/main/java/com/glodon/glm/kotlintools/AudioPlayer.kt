package com.glodon.glm.kotlintools

import android.content.Context
import android.media.MediaPlayer

/**
 * Created by lichongmac@163.com on 2020/6/11.
 */
object AudioPlayer {
    private var mPlayer: MediaPlayer? = null

    /**
     * 暂停
     */
    fun pause() {
        mPlayer?.pause()
    }

    /**
     * 停止
     */
    fun stop() {
        if (mPlayer != null) {
            /**
             * MediaPlayer.release()方法可销毁MediaPlayer的实例。销毁是“停止”的一种具有攻击意味的说法，
             * 但我们有充足的理由使用销毁一词。
             * 除非调用MediaPlayer.release()方法，否则MediaPlayer将一直占用着音频解码硬件及其它系统资源
             * 。而这些资源是由所有应用共享的。
             * MediaPlayer有一个stop()方法。该方法可使MediaPlayer实例进入停止状态，等需要时再重新启动
             * 。不过，对于简单的音频播放应用，建议 使用release()方法销毁实例，并在需要时进行重见。基于以上原因，有一个简单可循的规则：
             * 只保留一个MediaPlayer实例，保留时长即音频文件 播放的时长。
             */
//            "?"加在变量名后，系统在任何情况不会报它的空指针异常。
//            "!!"加在变量名后，如果对象为null，那么系统一定会报异常！
            mPlayer!!.release()
            mPlayer = null
        }
    }

    /**
     * 播放
     */
    fun paly(c: Context) {
        /**
         * 开头就调用stop()方法，可避免用户多次单机Play按钮创建多个MediaPlayer实例的情况发生。
         */
        stop()
        /**
         * 音频文件放在res/raw目录下。目录raw负责存放那些不需要Android编译系统特别处理的各类文件。
         */
//        mPlayer = MediaPlayer.create(c, R.raw.ok);
        // mPlayer.setVolume(10, 10);
        mPlayer!!.setOnCompletionListener { stop() }
        mPlayer!!.start()
    }

}