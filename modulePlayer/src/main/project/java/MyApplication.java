package java;

import android.util.Log;

import androidx.multidex.MultiDexApplication;

import app.util.LogUtils;
import app.util.ToastUtil;


/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/7/13
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
     super.onCreate();
        ToastUtil.setApplication(this);
        LogUtils.setLevel(Log.VERBOSE);
    }
}
