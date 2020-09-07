package player.media.pano;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.vr.sdk.widgets.common.VrEventListener;
import com.google.vr.sdk.widgets.common.VrWidgetRenderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/8/13
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */

class VrPanoramaRenderer extends VrWidgetRenderer {
    private static final String TAG = VrPanoramaRenderer.class.getSimpleName();
    private static final boolean DEBUG = false;
    private volatile ApiRequest lastLoadImageRequest;

    public VrPanoramaRenderer(Context context, GLThreadScheduler glThreadScheduler, float xMetersPerPixel, float yMetersPerPixel) {
        super(context, glThreadScheduler, xMetersPerPixel, yMetersPerPixel,0);
        System.loadLibrary("panorenderer");
    }

    public void loadImageFromBitmap(Bitmap bitmap, VrPanoramaView.Options options, VrEventListener eventListener) {
        this.lastLoadImageRequest = new VrPanoramaRenderer.LoadBitmapRequest(bitmap, options, eventListener);
        this.postApiRequestToGlThread(this.lastLoadImageRequest);
    }

    public void loadImageFromByteArray(byte[] jpegImageData, VrPanoramaView.Options options, VrEventListener eventListener) {
        this.lastLoadImageRequest = new VrPanoramaRenderer.LoadImageFromByteArrayRequest(jpegImageData, options, eventListener);
        this.postApiRequestToGlThread(this.lastLoadImageRequest);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        if (this.lastLoadImageRequest != null) {
            this.executeApiRequestOnGlThread(this.lastLoadImageRequest);
        }

    }

    @Override
    protected native long nativeCreate(ClassLoader classLoader, Context appContext, float initialYaw);

    @Override
    protected native void nativeResize(long nativeRenderer, int widthPixels, int heightPixels, float xMetersPerPixel, float yMetersPerPixel, int screenRotation);

    @Override
    protected native void nativeDestroy(long nativeRenderer);

    @Override
    protected native void nativeRenderFrame(long nativeRenderer);

    @Override
    protected native void nativeSetStereoMode(long nativeRenderer, boolean stereoMode);

    protected native void nativeSetPureTouchTracking(long nativeRenderer, boolean setPureTouchTracking);

    @Override
    protected native void nativeOnPause(long nativeRenderer);

    @Override
    protected native void nativeOnResume(long nativeRenderer);

    @Override
    protected native void nativeOnPanningEvent(long nativeRenderer, float translationPixelX, float translationPixelY);

    @Override
    protected native void nativeGetHeadRotation(long nativeRenderer, float[] yawAndPitchOut);

    private native void nativeLoadImageFromBitmap(long nativeRenderer, Bitmap bitmap, VrPanoramaView.Options options, VrEventListener eventListener);

    private native void nativeLoadImageFromByteArray(long nativeRenderer, byte[] encodedImageData, VrPanoramaView.Options options, VrEventListener eventListener);

    private class LoadImageFromByteArrayRequest implements ApiRequest {
        public final byte[] jpegImageData;
        public final VrPanoramaView.Options options;
        public final VrEventListener eventListener;

        public LoadImageFromByteArrayRequest(byte[] jpegImageData, VrPanoramaView.Options options, VrEventListener eventListener) {
            this.jpegImageData = jpegImageData;
            this.options = options;
            this.eventListener = eventListener;
        }

        @Override
        public void execute() {
            VrPanoramaRenderer.this.nativeLoadImageFromByteArray(VrPanoramaRenderer.this.getNativeRenderer(), this.jpegImageData, this.options, this.eventListener);
        }
    }

    private class LoadBitmapRequest implements ApiRequest {
        public final Bitmap bitmap;
        public final VrPanoramaView.Options options;
        public final VrEventListener eventListener;

        public LoadBitmapRequest(Bitmap bitmap, VrPanoramaView.Options options, VrEventListener eventListener) {
            this.bitmap = bitmap;
            this.options = options;
            this.eventListener = eventListener;
        }

        @Override
        public void execute() {
            VrPanoramaRenderer.this.nativeLoadImageFromBitmap(VrPanoramaRenderer.this.getNativeRenderer(), this.bitmap, this.options, this.eventListener);
        }
    }
}
