package player.media.pano;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.vr.cardboard.annotations.UsedByNative;
import com.google.vr.sdk.widgets.common.VrEventListener;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/8/13
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */

public class VrPanoramaEventListener extends VrEventListener {
    public static volatile boolean isLoadSuccessful = false;
    private static final String TAG = VrPanoramaEventListener.class.getSimpleName();
    private static final boolean DEBUG = false;
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    public VrPanoramaEventListener() {
    }

    @UsedByNative
    private void onLoadSuccessJni() {
        this.uiHandler.post(new Runnable() {
            @Override
            public void run() {
                VrPanoramaEventListener.this.onLoadSuccess();
                VrPanoramaEventListener.isLoadSuccessful = true;
            }
        });
    }

    @UsedByNative
    private void onLoadErrorJni(final String errorMessage) {
        String var10000 = TAG;
        int var2 = this.hashCode();
        Log.e(var10000, (new StringBuilder(20 + String.valueOf(errorMessage).length())).append(var2).append(".onError ").append(errorMessage).toString());
        this.uiHandler.post(new Runnable() {
            @Override
            public void run() {
                VrPanoramaEventListener.this.onLoadError(errorMessage);
            }
        });
    }
}
