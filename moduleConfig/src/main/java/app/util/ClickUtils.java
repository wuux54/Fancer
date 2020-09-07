package app.util;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2019/12/20
 * @E-mail: W_SpongeBob@163.com
 * @Desc：点击工具类
 */
public class ClickUtils {
    private static long currData = 0L;
    private static final int TIME_DURATION = 800;

    public static boolean isFirstClick() {
        long l = System.currentTimeMillis();
        if (l - currData > TIME_DURATION) {
            currData = l;
            return true;
        }
        return false;
    }

    public static boolean isFastClick() {
        long l = System.currentTimeMillis();
        if (l - currData < TIME_DURATION) {
            return true;
        }
        currData = l;
        return false;
    }

    public static boolean isFastClick2ViewPage() {
        long l = System.currentTimeMillis();
        if (l - currData < TIME_DURATION) {
            return true;
        }
        return false;
    }
}
