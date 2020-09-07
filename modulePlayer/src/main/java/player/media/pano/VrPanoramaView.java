package player.media.pano;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;

import com.google.vr.cardboard.annotations.UsedByNative;
import com.google.vr.sdk.widgets.common.VrWidgetRenderer;
import com.google.vr.sdk.widgets.common.VrWidgetView;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/8/13
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */

public class VrPanoramaView extends VrWidgetView {
    private static final String TAG = VrPanoramaView.class.getSimpleName();
    private static final boolean DEBUG = false;
    private VrPanoramaRenderer renderer;
    private VrPanoramaEventListener eventListener = new VrPanoramaEventListener();

    public VrPanoramaView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VrPanoramaView(Context context) {
        super(context);
    }

    @Override
    protected VrWidgetRenderer createRenderer(Context context, VrWidgetRenderer.GLThreadScheduler glThreadScheduler,  float xMetersPerPixel, float yMetersPerPixel, int i) {
        this.renderer = new VrPanoramaRenderer(this.getContext(), glThreadScheduler, xMetersPerPixel, yMetersPerPixel);
        return this.renderer;
    }

    public void loadImageFromBitmap(Bitmap bitmap, VrPanoramaView.Options options) {
        if (options == null) {
            options = new VrPanoramaView.Options();
        } else {
            options.validate();
        }

        this.renderer.loadImageFromBitmap(bitmap, options, this.eventListener);
    }

    public void loadImageFromByteArray(byte[] jpegImageData, VrPanoramaView.Options options) {
        if (options == null) {
            options = new VrPanoramaView.Options();
        } else {
            options.validate();
        }

        this.renderer.loadImageFromByteArray(jpegImageData, options, this.eventListener);
    }

    public void setEventListener(VrPanoramaEventListener eventListener) {
        super.setEventListener(eventListener);
        this.eventListener = eventListener;
    }

    @UsedByNative
    public static class Options {
        private static final int TYPE_START_MARKER = 0;
        public static final int TYPE_MONO = 1;
        public static final int TYPE_STEREO_OVER_UNDER = 2;
        private static final int TYPE_END_MARKER = 3;
        @UsedByNative
        public int inputType = 1;

        public Options() {
        }

        void validate() {
            if (this.inputType <= 0 || this.inputType >= 3) {
                String var10000 = VrPanoramaView.TAG;
                int var1 = this.inputType;
                Log.e(var10000, (new StringBuilder(38)).append("Invalid Options.inputType: ").append(var1).toString());
                this.inputType = 1;
            }

        }
    }
}

