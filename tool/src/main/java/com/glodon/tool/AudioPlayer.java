package com.glodon.tool;

import android.content.Context;
import android.media.MediaPlayer;


/**
 * Created by zhangtianjie
 *
 * @email zhangtj-a@glodon.com
 * @date 2019/4/25 8:44 AM
 **/
public class AudioPlayer {

    private MediaPlayer mPlayer;

    /**
     * 暂停
     */
    public void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    /**
     * 停止
     */
    public void stop() {
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
            mPlayer.release();
            mPlayer = null;
        }
    }

    /**
     * 播放
     */
    public void paly(Context c) {
        /**
         * 开头就调用stop()方法，可避免用户多次单机Play按钮创建多个MediaPlayer实例的情况发生。
         */
        stop();

        /**
         * 音频文件放在res/raw目录下。目录raw负责存放那些不需要Android编译系统特别处理的各类文件。
         */
//        mPlayer = MediaPlayer.create(c, R.raw.ok);
        // mPlayer.setVolume(10, 10);
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                stop();
            }
        });


    /*    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setVolume(1f, 1f);
*/

        mPlayer.start();

    }

}
