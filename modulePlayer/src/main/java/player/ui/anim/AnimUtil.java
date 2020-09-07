package player.ui.anim;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/7/15
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class AnimUtil {

    /**
     * Video 操作界面展示和隐藏。切换动画
     */
    public static void videoCtvSwitch(View view, float fromX, float toX, float fromY, float toY, long duration, boolean show) {
        if (view.getAnimation() != null && !view.getAnimation().hasEnded()) {
            return;
        }

        TranslateAnimation translateAnimation = new TranslateAnimation(fromX, toX, fromY, toY);
        AlphaAnimation alphaAnimation = new AlphaAnimation(show ? 0 : 1, show ? 1 : 0);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(duration);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(show ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(animationSet);
    }


    public static void videoCtvSwitchY(View view, boolean show, boolean isTop) {
        int height = view.getHeight();
        if (isTop) {
            videoCtvSwitch(view, 0, 0, show ? -height : 0, show ? 0 : -height, 1000, show);
        } else {
            videoCtvSwitch(view, 0, 0, show ? height : 0, show ? 0 : height, 1000, show);
        }
    }


    public static void videoCtvSwitchX(View view, boolean show, boolean isLeft) {
        int width = view.getWidth();
        if (isLeft) {
            videoCtvSwitch(view, show ? -width : 0, show ? 0 : -width, 0, 0, 1000, show);
        } else {
            videoCtvSwitch(view, show ? width : 0, show ? 0 : width, 0, 0, 1000, show);
        }
    }
}
