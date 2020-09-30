package player.media.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import player.listener.VideoViewListener;
import player.media.ijk.IMediaController;
import player.media.ijk.IMediaPlayInfoListener;
import player.media.ijk.IRenderView;
import player.media.ijk.SurfaceRenderView;
import player.media.ijk.TextureRenderView;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import wseemann.media.FFmpegMediaMetadataRetriever;

public class IjkMpVideoView extends FrameLayout implements MediaController.MediaPlayerControl {
    private final String TAG = "VideoMediaPlayView";

    private IRenderView.ISurfaceHolder mSurfaceHolder = null;
    private int mVideoWidth;
    private int mVideoHeight;
    private int mSurfaceWidth;
    private int mSurfaceHeight;
    private int mVideoRotationDegree;
    private IMediaController mMediaController;

    private IRenderView mRenderView;
    private int mVideoSarNum;
    private int mVideoSarDen;

    private boolean enableSurfaceView = true;
    private boolean enableTextureView = false;
    private boolean enableNoView = false;

    private IMediaPlayInfoListener mediaPlayInfoListener;
    private VideoViewListener videoViewListener;

    private long lastTouchTime;

    private IjkMpController ijkMpController;
    private int centerX;

    private Position lastPosition = new Position(0, 0);
    private boolean isRight;

    private FFmpegMediaMetadataRetriever retriever;
    private CompositeDisposable retrieverDisposable;

    public void setVideoViewListener(VideoViewListener videoViewListener) {
        this.videoViewListener = videoViewListener;
    }

    public void setMediaPlayInfoListener(IMediaPlayInfoListener mediaPlayInfoListener) {
        this.mediaPlayInfoListener = mediaPlayInfoListener;
    }

    public IjkMpVideoView(Context context) {
        super(context);
        initVideoView(context);
    }

    public IjkMpVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVideoView(context);
    }

    public IjkMpVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IjkMpVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initVideoView(context);
    }


    private void initVideoView(Context context) {
        ijkMpController = new IjkMpController(context);
        ijkMpController.setIsVideo(true);
        setNeedProgress(true);

        initRenders();

        mVideoWidth = 0;
        mVideoHeight = 0;

        setFocusable(true);
        setFocusableInTouchMode(true);
        setLongClickable(true);
        requestFocus();

        initEvent();
    }

    private void initEvent() {
        ijkMpController.setOnVideoSizeChange(mSizeChangedListener);
        ijkMpController.setOnInfoListener((mp, what, extra) -> {
            switch (what) {
                case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                    mVideoRotationDegree = extra;
                    Log.d(TAG, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + extra);
                    if (mRenderView != null) {
                        mRenderView.setVideoRotation(extra);
                    }
                    break;
            }
            return true;
        });
        ijkMpController.setMediaPlayInfoListener(new IMediaPlayInfoListener() {

            @Override
            public void prepareStart() {
                if (mediaPlayInfoListener != null) {
                    mediaPlayInfoListener.prepareStart();
                }
            }

            @Override
            public void startPlay() {
                if (mediaPlayInfoListener != null) {
                    mediaPlayInfoListener.startPlay();
                }
            }

            @Override
            public void pausePlay() {
                if (mediaPlayInfoListener != null) {
                    mediaPlayInfoListener.pausePlay();
                }
            }

            @Override
            public void prepareFinish() {
                if (mMediaController != null) {
                    mMediaController.setEnabled(true);
                }
                mVideoWidth = ijkMpController.getMediaPlayer().getVideoWidth();
                mVideoHeight = ijkMpController.getMediaPlayer().getVideoHeight();

                if (mVideoWidth != 0 && mVideoHeight != 0) {
                    if (mRenderView != null) {
                        mRenderView.setVideoSize(mVideoWidth, mVideoHeight);
                        mRenderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
                        if (!mRenderView.shouldWaitForResize() || mSurfaceWidth == mVideoWidth && mSurfaceHeight == mVideoHeight) {
                            if (mMediaController != null) {
                                mMediaController.show();
                            }
                        }
                    }
                }


                if (mediaPlayInfoListener != null) {
                    mediaPlayInfoListener.prepareFinish();
                }
            }

            @Override
            public void completePlay() {
                if (mMediaController != null) {
                    mMediaController.hide();
                }

                if (mediaPlayInfoListener != null) {
                    mediaPlayInfoListener.completePlay();
                }
            }

            @Override
            public void errorPlay(String errorMsg) {
                if (mMediaController != null) {
                    mMediaController.hide();
                }

                if (mediaPlayInfoListener != null) {
                    mediaPlayInfoListener.errorPlay(errorMsg);
                }
            }

            @Override
            public void updProgress(int progress) {

                if (mediaPlayInfoListener != null) {
                    mediaPlayInfoListener.updProgress(progress);
                }
            }

            @Override
            public void updBufferProgress(int percent) {
                if (mediaPlayInfoListener != null) {
                    mediaPlayInfoListener.updBufferProgress(percent);
                }
            }

            @Override
            public void showLoading(boolean isShow) {
                if (mediaPlayInfoListener != null) {
                    mediaPlayInfoListener.showLoading(isShow);
                }
            }
        });
    }

    public void setRenderView(IRenderView renderView) {
        if (mRenderView != null) {
            ijkMpController.getMediaPlayer().setDisplay(null);
            View renderUiView = mRenderView.getView();
            mRenderView.removeRenderCallback(mShCallback);
            mRenderView = null;
            removeView(renderUiView);
        }

        if (renderView == null) {
            return;
        }

        mRenderView = renderView;
        renderView.setAspectRatio(mCurrentAspectRatio);
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            renderView.setVideoSize(mVideoWidth, mVideoHeight);
        }
        if (mVideoSarNum > 0 && mVideoSarDen > 0) {
            renderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
        }

        View renderUIView = mRenderView.getView();
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        renderUIView.setLayoutParams(lp);
        addView(renderUIView);

        mRenderView.addRenderCallback(mShCallback);
        mRenderView.setVideoRotation(mVideoRotationDegree);
    }

    public void setRender(int render) {
        switch (render) {
            case RENDER_NONE:
                setRenderView(null);
                break;
            case RENDER_TEXTURE_VIEW: {
                TextureRenderView renderView = new TextureRenderView(getContext());
                IMediaPlayer mMediaPlayer = ijkMpController.getMediaPlayer();
                //mediaPlayer创建后，更改RenderView
                if (mMediaPlayer != null) {
                    renderView.getSurfaceHolder().bindToMediaPlayer(mMediaPlayer);
                    renderView.setVideoSize(mMediaPlayer.getVideoWidth(), mMediaPlayer.getVideoHeight());
                    renderView.setVideoSampleAspectRatio(mMediaPlayer.getVideoSarNum(), mMediaPlayer.getVideoSarDen());
                    renderView.setAspectRatio(mCurrentAspectRatio);
                }
                setRenderView(renderView);
                break;
            }
            case RENDER_SURFACE_VIEW: {
                SurfaceRenderView renderView = new SurfaceRenderView(getContext());
                setRenderView(renderView);
                break;
            }
            default:
                Log.e(TAG, String.format(Locale.getDefault(), "invalid render %d\n", render));
                break;
        }
    }

    public void setPath(String uri) {
        setUri(Uri.parse(uri));
    }

    public void setUri(Uri uri) {
        setVideoUri(uri, null);
    }

    public void setVideoUri(Uri uri, Map<String, String> headers) {
        ijkMpController.setUri(uri, headers);

        if (retriever != null) {
            retriever.release();
            retriever = null;
        }
        //mmr.release();
        //mmr = new MediaMetadataRetriever();
        retriever = new FFmpegMediaMetadataRetriever();
        new Thread(() -> {
            try {
                retriever.setDataSource(uri.toString());
                //另外一种获取视频帧图片的方法
                //mmr.setDataSource(url, new HashMap<String, String>());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                retriever = null;
            }
        }).start();
        requestLayout();
        invalidate();
    }


    /**
     * 获取视频帧图片
     *
     * @param time 微秒
     */
    public void setBitMap(int time, ImageView ivBitmap) {
        if (retriever == null) {
            return;
        }
//        long currTime = System.currentTimeMillis();
//        if (currTime - lastSetTime < 800) {
//            return;
//        }
//        lastSetTime = currTime;
//        new Thread(() -> {
//            try {
//                //Bitmap b = mmr.getFrameAtTime(time);
//                Bitmap b = retriever.getFrameAtTime(time);
//                new Handler(Looper.getMainLooper()).post(()
//                        -> ivBitmap.setImageBitmap(b));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
        if (retrieverDisposable == null) {
            retrieverDisposable=new CompositeDisposable();
        }


        Single.create((SingleOnSubscribe<Bitmap>) emitter
                -> {
            Bitmap frameAtTime = retriever.getFrameAtTime(time);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            frameAtTime.compress(Bitmap.CompressFormat.JPEG, 1, bos);

            Matrix matrix = new Matrix();
            matrix.setScale(0.5f, 0.5f);

            emitter.onSuccess(Bitmap.createBitmap(frameAtTime, 0, 0, frameAtTime.getWidth(),
                    frameAtTime.getHeight(), matrix, true));
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Bitmap>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        retrieverDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(Bitmap b) {
                        ivBitmap.setImageBitmap(b);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public void setMediaController(IMediaController controller) {
        if (mMediaController != null) {
            mMediaController.hide();
        }
        mMediaController = controller;
    }


    IMediaPlayer.OnVideoSizeChangedListener mSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
            mVideoWidth = mp.getVideoWidth();
            mVideoHeight = mp.getVideoHeight();
            mVideoSarNum = mp.getVideoSarNum();
            mVideoSarDen = mp.getVideoSarDen();
            if (mVideoWidth != 0 && mVideoHeight != 0) {
                if (mRenderView != null) {
                    mRenderView.setVideoSize(mVideoWidth, mVideoHeight);
                    mRenderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
                }
                requestLayout();
            }
        }
    };


    IRenderView.IRenderCallback mShCallback = new IRenderView.IRenderCallback() {
        @Override
        public void onSurfaceChanged(@NonNull IRenderView.ISurfaceHolder holder, int format, int w, int h) {
            if (holder.getRenderView() != mRenderView) {
                Log.e(TAG, "onSurfaceChanged: unmatched render callback\n");
                return;
            }

            mSurfaceWidth = w;
            mSurfaceHeight = h;
            boolean hasValidSize = !mRenderView.shouldWaitForResize() || (mVideoWidth == w && mVideoHeight == h);
            //保证横竖屏切换，能继续播放
            if (ijkMpController.targetStatePlaying() && hasValidSize) {
                if (ijkMpController.getSeekWhenPrepared() != 0) {
                    ijkMpController.seekTo((int) ijkMpController.getSeekWhenPrepared());
                }
                ijkMpController.start();
            }
        }

        @Override
        public void onSurfaceCreated(@NonNull IRenderView.ISurfaceHolder holder, int width, int height) {
            if (holder.getRenderView() != mRenderView) {
                Log.e(TAG, "onSurfaceCreated: unmatched render callback\n");
                return;
            }

            mSurfaceHolder = holder;
            mSurfaceHolder.bindToMediaPlayer(ijkMpController.getMediaPlayer());
        }

        @Override
        public void onSurfaceDestroyed(@NonNull IRenderView.ISurfaceHolder holder) {
            if (holder.getRenderView() != mRenderView) {
                Log.e(TAG, "onSurfaceDestroyed: unmatched render callback\n");
                return;
            }
            if (ijkMpController.getMediaPlayer() != null) {
                ijkMpController.getMediaPlayer().setDisplay(null);
            }
            mSurfaceHolder = null;
        }
    };

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastTouchTime = System.currentTimeMillis();
                lastPosition.x = ev.getX();
                lastPosition.y = ev.getY();
                isRight = ev.getX() > centerX;
                break;
            case MotionEvent.ACTION_MOVE:
                float x = ev.getX();
                float y = ev.getY();

                float distanceX = x - lastPosition.x;
                float distanceY = y - lastPosition.y;

                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    if (Math.abs(distanceX) < 10) {
                        break;
                    }
                    //X滑动
                    videoViewScrollX(isRight, distanceX);
                } else {
                    if (Math.abs(distanceY) < 10) {
                        break;
                    }
                    //y滑动
                    videoViewScrollY(isRight, distanceY);
                }
                //滑动
                videoViewScroll(isRight, lastPosition, new Position(x, y));
                break;
            case MotionEvent.ACTION_UP:
                long crrTime = System.currentTimeMillis();
                //点击
                if (crrTime - lastTouchTime < 500) {
                    videoViewClick();
                }
                break;
            default:
                break;
        }

        return true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mVideoWidth = this.getWidth();
        mVideoHeight = this.getHeight();

        centerX = mVideoWidth / 2;
    }


    @Override
    public boolean onTrackballEvent(MotionEvent ev) {
        if (mMediaController != null) {
            toggleMediaControlsVisibility();
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isKeyCodeSupported = keyCode != KeyEvent.KEYCODE_BACK && keyCode != KeyEvent.KEYCODE_VOLUME_UP && keyCode != KeyEvent.KEYCODE_VOLUME_DOWN &&
                keyCode != KeyEvent.KEYCODE_VOLUME_MUTE && keyCode != KeyEvent.KEYCODE_MENU && keyCode != KeyEvent.KEYCODE_CALL && keyCode != KeyEvent
                .KEYCODE_ENDCALL;
        if (isKeyCodeSupported && mMediaController != null) {
            if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
                if (ijkMpController.isPlaying()) {
                    ijkMpController.pause();
                    mMediaController.show();
                } else {
                    ijkMpController.start();
                    mMediaController.hide();
                }
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY) {
                if (!ijkMpController.isPlaying()) {
                    ijkMpController.start();
                    mMediaController.hide();
                }
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP || keyCode == KeyEvent.KEYCODE_MEDIA_PAUSE) {
                if (ijkMpController.isPlaying()) {
                    ijkMpController.pause();
                    mMediaController.show();
                }
                return true;
            } else {
                toggleMediaControlsVisibility();
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void toggleMediaControlsVisibility() {
        if (mMediaController.isShowing()) {
            mMediaController.hide();
        } else {
            mMediaController.show();
        }
    }


    private static final int[] s_allAspectRatio = {IRenderView.AR_ASPECT_FIT_PARENT, IRenderView.AR_ASPECT_FILL_PARENT, IRenderView
            .AR_ASPECT_WRAP_CONTENT, IRenderView.AR_MATCH_PARENT, IRenderView.AR_16_9_FIT_PARENT, IRenderView.AR_4_3_FIT_PARENT};
    private int mCurrentAspectRatioIndex = 3;//默认是不拉伸填充
    private int mCurrentAspectRatio = s_allAspectRatio[mCurrentAspectRatioIndex];

    public int toggleAspectRatio() {
        mCurrentAspectRatioIndex++;
        if (mCurrentAspectRatioIndex > 5) {
            mCurrentAspectRatioIndex = 0;
        }
        mCurrentAspectRatio = s_allAspectRatio[mCurrentAspectRatioIndex];
        if (mRenderView != null) {
            mRenderView.setAspectRatio(mCurrentAspectRatio);
        }
        return mCurrentAspectRatio;
    }

    // -------------------------
    // Extend: Render
    // -------------------------
    public static final int RENDER_NONE = 0;
    public static final int RENDER_SURFACE_VIEW = 1;
    public static final int RENDER_TEXTURE_VIEW = 2;

    private List<Integer> mAllRenders = new ArrayList<>();
    private int mCurrentRenderIndex = 0;
    private int mCurrentRender = RENDER_NONE;

    private void initRenders() {
        mAllRenders.clear();

        if (enableSurfaceView) {
            mAllRenders.add(RENDER_SURFACE_VIEW);
        }
        if (enableTextureView && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            mAllRenders.add(RENDER_TEXTURE_VIEW);
        }
        if (enableNoView) {
            mAllRenders.add(RENDER_NONE);
        }

        if (mAllRenders.isEmpty()) {
            mAllRenders.add(RENDER_SURFACE_VIEW);
        }
        mCurrentRender = mAllRenders.get(mCurrentRenderIndex);
        setRender(mCurrentRender);
    }

    public int toggleRender() {
        mCurrentRenderIndex++;
        mCurrentRenderIndex %= mAllRenders.size();

        mCurrentRender = mAllRenders.get(mCurrentRenderIndex);
        setRender(mCurrentRender);
        return mCurrentRender;
    }

    public void setAspectRatio(int aspectRatio) {
        for (int i = 0; i < s_allAspectRatio.length; i++) {
            if (s_allAspectRatio[i] == aspectRatio) {
                mCurrentAspectRatioIndex = i;
                mCurrentAspectRatio = s_allAspectRatio[mCurrentAspectRatioIndex];
                if (mRenderView != null) {
                    mRenderView.setAspectRatio(mCurrentAspectRatio);
                }
                break;
            }
        }
    }

    public void setNeedProgress(boolean needProgress) {
        ijkMpController.setNeedProgress(needProgress);
    }

    public String getTextTime(int time) {
        return ijkMpController.getTextTime(time);
    }

    public void releaseMedia() {
        ijkMpController.releaseMedia();
        ijkMpController = null;
        if (retriever != null) {
            retriever.release();
        }
        if (retrieverDisposable != null) {
            retrieverDisposable.clear();
            retrieverDisposable = null;
        }

    }


    /**
     * 滑动回调
     */
    public void videoViewScroll(boolean isRight,
                                Position startPosition,
                                Position endPosition) {
        if (videoViewListener != null) {
            videoViewListener.videoViewScroll(isRight, startPosition, endPosition);
        }
    }

    /**
     * 横向滑动
     *
     * @param isRight  右侧
     * @param distance 距离  >0  右向滑动
     */
    public void videoViewScrollX(boolean isRight, float distance) {
        if (videoViewListener != null) {
            videoViewListener.videoViewScrollX(isRight, distance);
        }
    }

    /**
     * 纵向滑动
     *
     * @param isRight  右侧
     * @param distance 距离  >0  向上滑动
     */
    public void videoViewScrollY(boolean isRight, float distance) {
        if (videoViewListener != null) {
            videoViewListener.videoViewScrollY(isRight, distance);
        }
    }

    /**
     * 点击
     */
    public void videoViewClick() {
        if (mMediaController != null) {
            toggleMediaControlsVisibility();
        }

        if (videoViewListener != null) {
            videoViewListener.videoViewClick();
        }
    }

    @Override
    public void start() {
        ijkMpController.start();
    }

    @Override
    public void pause() {
        ijkMpController.pause();
    }

    @Override
    public int getDuration() {
        return ijkMpController.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return ijkMpController.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        ijkMpController.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return ijkMpController.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return ijkMpController.getBufferPercentage();
    }

    @Override
    public boolean canPause() {
        return ijkMpController.canPause();
    }

    @Override
    public boolean canSeekBackward() {
        return ijkMpController.canSeekBackward();
    }

    @Override
    public boolean canSeekForward() {
        return ijkMpController.canSeekForward();
    }

    @Override
    public int getAudioSessionId() {
        return ijkMpController.getAudioSessionId();
    }

    public int getCurrentState() {
        return ijkMpController.getCurrentState();
    }

    public static class Position {
        float x;
        float y;

        Position(float iX, float iY) {
            this.x = iX;
            this.y = iY;
        }
    }

}
