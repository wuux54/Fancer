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
public interface IMediaPlayInfoListener {
    /**
     * 第一次加载，开始播放。此时准备：preparePlay
     */
    void prepareStart();

    /**
     * 开始播放  首次加载播放时，startPlay 在prepareFinish前调用
     */
    void startPlay();

    /**
     * 暂停播放
     */
    void pausePlay();

    /**
     * 准备播放
     */
    void prepareFinish();

    /**
     * 播放完成
     */
    void completePlay();

    /**
     * 播放出错
     *
     * @param errorMsg 错误信息
     */
    void errorPlay(String errorMsg);


    /**
     * 实时更新进度，1S刷新一次
     *
     * @param currProgress 当前播放进度
     */
    void updProgress(int currProgress);

    /**
     * 缓存进度更新
     *
     * @param percent 缓存进度
     */
    void updBufferProgress(int percent);


    /**
     * 加载缓冲中的loading
     *
     * @param isShow true:展示
     */
    void showLoading(boolean isShow);

}
