package app.util;

import android.util.Log;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2019/7/22
 * @E-mail: W_SpongeBob@163.com
 * @Desc：log管理类
 */
public class LogUtils {
    private LogUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static int level = Log.VERBOSE;

    public static void setLevel(int level) {
        LogUtils.level = level;
    }

    public static void v(String tag, String msg) {
        if (level <= Log.VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (level <= Log.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (level <= Log.INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (level <= Log.WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (level <= Log.ERROR) {
            Log.e(tag, msg);
        }
    }

}
