package player.media.ijk;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/7/17
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public interface IMediaPlayControllerListener {
    /**
     * 开始播放
     */
    void startPlay();

    /**
     * 暂停播放
     */
    void pausePlay();

    /**
     * 释放
     */
    void releaseMedia();



}
