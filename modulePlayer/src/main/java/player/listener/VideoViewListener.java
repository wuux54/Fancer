package player.listener;

import player.media.custom.IjkMpVideoView;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/7/29
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public interface VideoViewListener {
    /**
     * 滑动回调
     */
    void videoViewScroll(boolean isRight,
                         IjkMpVideoView.Position startEvent,
                         IjkMpVideoView.Position endEvent);

    /**
     * 横向滑动
     *
     * @param isRight  右侧
     * @param distance 距离  >0  右向滑动
     */
    void videoViewScrollX(boolean isRight, float distance);

    /**
     * 纵向滑动
     *
     * @param isRight  右侧
     * @param distance 距离  >0  向上滑动
     */
    void videoViewScrollY(boolean isRight, float distance);

    /**
     * 点击
     */
    void videoViewClick();
}
