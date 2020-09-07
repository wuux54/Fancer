package player.media.custom;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.MediaController;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import player.media.ijk.IMediaPlayInfoListener;
import player.services.MediaPlayerService;
import tv.danmaku.ijk.media.player.AndroidMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;
import tv.danmaku.ijk.media.player.TextureMediaPlayer;
import wseemann.media.FFmpegMediaMetadataRetriever;

public class IjkMpController implements MediaController.MediaPlayerControl {
    private final String TAG = "MediaPlayController";
    private Uri mUri;
    private Map<String, String> mHeaders;

    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PREPARING = 1;
    private static final int STATE_PREPARED = 2;
    private static final int STATE_PLAYING = 3;
    public static final int STATE_PAUSED = 4;
    private static final int STATE_PLAYBACK_COMPLETED = 5;

    public int getCurrentState() {
        return mCurrentState;
    }

    private int mCurrentState = STATE_IDLE;
    private int mTargetState = STATE_IDLE;

    private FFmpegMediaMetadataRetriever retriever;
    private IMediaPlayer mMediaPlayer = null;
    private int mCurrentBufferPercentage;
    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener;
    private IMediaPlayer.OnInfoListener mOnInfoListener;
    private IMediaPlayer.OnTimedTextListener mOnTimedTextListener;
    private IMediaPlayer.OnVideoSizeChangedListener mOnSizeChangedListener;

    private IMediaPlayInfoListener mediaPlayInfoListener;

    private boolean mCanPause = true;
    private boolean mCanSeekBack;
    private boolean mCanSeekForward;

    private boolean isVideoController = false;

    private CompositeDisposable disposable;

    private boolean needProgress = false;

    void setNeedProgress(boolean needProgress) {
        this.needProgress = needProgress;
        stopTimer();
    }

    public void setIsVideo(boolean isVideoController) {
        this.isVideoController = isVideoController;
    }

    private Context mAppContext;
    private boolean usingAndroidPlayer = false;
    private boolean usingMediaCodec = false;
    private boolean usingMediaCodecAutoRotate = false;
    private boolean usingOpenSLES = false;
    private String pixelFormat = "";// Auto Select=,RGB 565=fcc-rv16,RGB
    private boolean enableBackgroundPlay = false;

    private Context mContext;

    private long mSeekWhenPrepared; // recording the seek position while

    private Context getAppContext() {
        return mContext;
    }

    public IjkMpController(Context context) {
        mContext = context;
        init(context);
    }

    private void init(Context context) {
        mAppContext = context.getApplicationContext();

        mCurrentState = STATE_IDLE;
        mTargetState = STATE_IDLE;
    }

    @Override
    public void start() {
        if (isInPlaybackState()) {
            mCurrentState = STATE_PLAYING;
            mMediaPlayer.start();
            if (mediaPlayInfoListener != null) {
                mediaPlayInfoListener.startPlay();
            }
            startTimer();
        }
        mTargetState = STATE_PLAYING;
    }

    @Override
    public void pause() {
        if (isInPlaybackState()) {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                mCurrentState = STATE_PAUSED;
            }

            if (mediaPlayInfoListener != null) {
                mediaPlayInfoListener.pausePlay();
            }

            stopTimer();
        }
        mTargetState = STATE_PAUSED;
    }


    @Override
    public int getDuration() {
        if (isInPlaybackState()) {
            return (int) mMediaPlayer.getDuration();
        }

        return -1;
    }

    @Override
    public int getCurrentPosition() {
        synchronized (IjkMpController.this) {
            if (isInPlaybackState()) {
                return (int) mMediaPlayer.getCurrentPosition();
            }
            return 0;
        }
    }


    @Override
    public void seekTo(int msec) {
        synchronized (IjkMpController.this) {
            if (isInPlaybackState()) {
                mMediaPlayer.seekTo(msec);
                mSeekWhenPrepared = 0;
            } else {
                mSeekWhenPrepared = msec;
            }
        }
    }

    @Override
    public boolean isPlaying() {
        return isInPlaybackState() && mMediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        if (mMediaPlayer != null) {
            return mCurrentBufferPercentage;
        }
        return 0;
    }

    @Override
    public boolean canPause() {
        return mCanPause;
    }

    @Override
    public boolean canSeekBackward() {
        return mCanSeekBack;
    }

    @Override
    public boolean canSeekForward() {
        return mCanSeekForward;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    /**
     * Sets video path.
     *
     * @param path the path of the video.
     */
    public void setPath(String path) {
        setUri(Uri.parse(path));
    }

    /**
     * Sets video URI.
     *
     * @param uri the URI of the video.
     */
    public void setUri(Uri uri) {
        setUri(uri, null);
    }

    /**
     * Sets video URI using specific headers.
     *
     * @param uri     the URI of the video.
     * @param headers the headers for the URI request. Note that the cross domain
     *                redirection is allowed by default, but that can be changed
     *                with key/value pairs through the headers parameter with
     *                "android-allow-cross-domain-redirect" as the key and "0" or
     *                "1" as the value to disallow or allow cross domain
     *                redirection.
     */
    public void setUri(Uri uri, Map<String, String> headers) {
        mUri = uri;
        mHeaders = headers;
        mSeekWhenPrepared = 0;
        preparePlay();
    }

    private void initMediaPlayer() throws IOException, IllegalArgumentException {
        if (mMediaPlayer != null) {
            return;
        }

        if (enableBackgroundPlay) {
            mMediaPlayer = new TextureMediaPlayer(mMediaPlayer);
            initBackground();
        } else if (usingAndroidPlayer) {
            mMediaPlayer = new AndroidMediaPlayer();
        } else {
            IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
            //TODO 设置是否打印debug （调试的时候使用IjkMediaPlayer.IJK_LOG_DEBUG，上线的时候使用IjkMediaPlayer.IJK_LOG_ERROR）
            ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_ERROR);

            if (usingMediaCodec) {
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
                if (usingMediaCodecAutoRotate) {
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
                } else {
                    ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 0);
                }
            } else {
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);
            }
            if (usingOpenSLES) {
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 1);
            } else {
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 0);
            }
            if (TextUtils.isEmpty(pixelFormat)) {
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32);
            } else {
                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", pixelFormat);
            }

            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);

            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "timeout", 10000000);
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "reconnect", 1);
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);

//            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"max-buffer-size",1024);
//            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "fflags", "fastseek");//设置seekTo能够快速seek到指定位置并播放
//            //播放前的探测Size，默认是1M, 改小一点会出画面更快
//            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 500);

            mMediaPlayer = ijkMediaPlayer;
        }

        // TODO: create SubtitleController in MediaPlayer, but we need
        // a context for the subtitle renderers
        final Context context = getAppContext();
        // REMOVED: SubtitleController
        // REMOVED: mAudioSession
        mMediaPlayer.setOnPreparedListener(mPreparedListener);
        mMediaPlayer.setOnCompletionListener(mCompletionListener);
        mMediaPlayer.setOnErrorListener(mErrorListener);
        mMediaPlayer.setOnInfoListener(mInfoListener);
        mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
        mMediaPlayer.setOnTimedTextListener(mTimedTextLister);
        mCurrentBufferPercentage = 0;

        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    private void preparePlay() {
        if (mUri == null) {
            return;
        }

        mCurrentState = STATE_PREPARING;

        if (mediaPlayInfoListener != null) {
            mediaPlayInfoListener.prepareStart();
        }

        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mCurrentState = STATE_IDLE;
//            AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
//            am.abandonAudioFocus(null);
        }


        AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        try {
            initMediaPlayer();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                mMediaPlayer.setDataSource(mAppContext, mUri, mHeaders);
            } else {
                mMediaPlayer.setDataSource(mUri.toString());
            }
            bindVideoWithMp();
            mMediaPlayer.prepareAsync();
        } catch (IOException | IllegalArgumentException ex) {
            Log.w(TAG, "Unable to open content: " + mUri, ex);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;
            mErrorListener.onError(mMediaPlayer, MediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
        } finally {
            // REMOVED: mPendingSubtitleTracks.clear();
        }
    }

    public void releaseMedia() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            mCurrentState = STATE_IDLE;
            mTargetState = STATE_IDLE;
            AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
            am.abandonAudioFocus(null);

            stopTimer();
        }
    }


    private IMediaPlayer.OnPreparedListener mPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {
            mCurrentState = STATE_PREPARED;
            if (mediaPlayInfoListener != null) {
                mediaPlayInfoListener.prepareFinish();
            }

            if (mSeekWhenPrepared != 0) {
                seekTo((int) mSeekWhenPrepared);
            }

            start();
        }
    };

    private IMediaPlayer.OnCompletionListener mCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            mCurrentState = STATE_PLAYBACK_COMPLETED;
            mTargetState = STATE_PLAYBACK_COMPLETED;

            if (mediaPlayInfoListener != null) {
                mediaPlayInfoListener.completePlay();
            }
            stopTimer();
        }
    };

    private IMediaPlayer.OnInfoListener mInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer mp, int what, int extra) {
            if (mOnInfoListener != null) {
                mOnInfoListener.onInfo(mp, what, extra);
            }


//            if (what == IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START
//                    || what == IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START) {
//                reTryCount = 0;
//                if (mediaPlayListener != null) {
//                    mediaPlayListener.onMediaPlayErrorRetry(true);
//                }
//            }
            switch (what) {
                case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    if (mediaPlayInfoListener != null) {
                        mediaPlayInfoListener.showLoading(false);
                    }
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    if (mediaPlayInfoListener != null) {
                        mediaPlayInfoListener.showLoading(true);
                    }
                    break;
            }
            return true;
        }
    };

    private IMediaPlayer.OnTimedTextListener mTimedTextLister = new IMediaPlayer.OnTimedTextListener() {
        @Override
        public void onTimedText(IMediaPlayer mp, IjkTimedText text) {
            if (mOnTimedTextListener != null) {
                mOnTimedTextListener.onTimedText(mp, text);
            }
        }
    };

    private IMediaPlayer.OnErrorListener mErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int framework_err, int impl_err) {
            Log.d(TAG, "Error: " + framework_err + "," + impl_err);
            mCurrentState = STATE_ERROR;
            mTargetState = STATE_ERROR;

            stopTimer();

            if (mediaPlayInfoListener != null) {
                mediaPlayInfoListener.errorPlay("Error: " + framework_err + "," + impl_err);
            }
            return true;
        }
    };

    private IMediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            if (mOnBufferingUpdateListener != null) {
                mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
            }
            mCurrentBufferPercentage = percent;

            if (mediaPlayInfoListener != null) {
                mediaPlayInfoListener.updBufferProgress(percent);
            }
        }
    };


    public void setOnBufferingUpdateListener(IMediaPlayer.OnBufferingUpdateListener l) {
        mOnBufferingUpdateListener = l;
    }

    /**
     * Register a callback to be invoked when an informational event occurs
     * during playback or setup.
     *
     * @param l The callback that will be run
     */
    public void setOnInfoListener(IMediaPlayer.OnInfoListener l) {
        mOnInfoListener = l;
    }

    public void setOnTimedTextListener(IMediaPlayer.OnTimedTextListener l) {
        mOnTimedTextListener = l;
    }

    /*
     * release the media player in any state
     */
    public void release(boolean clearTargetState) {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            // REMOVED: mPendingSubtitleTracks.clear();
            mCurrentState = STATE_IDLE;
            if (clearTargetState) {
                mTargetState = STATE_IDLE;
            }
            AudioManager am = (AudioManager) mAppContext.getSystemService(Context.AUDIO_SERVICE);
            am.abandonAudioFocus(null);
        }
    }


    public void suspend() {
        release(false);
    }

    public void resume() {
        preparePlay();
    }


    /**
     * 处于播放状态
     */
    private boolean isInPlaybackState() {
        return (mMediaPlayer != null
                && mCurrentState != STATE_ERROR
                && mCurrentState != STATE_IDLE
        );
    }


    // -------------------------
    // Extend: Background
    // -------------------------

    private void initBackground() {
        if (enableBackgroundPlay) {
            MediaPlayerService.intentToStart(getAppContext());
            MediaPlayerService.setMediaPlayer(mMediaPlayer);
        }
    }


    private void startTimer() {
        if (mediaPlayInfoListener == null || !needProgress) {
            return;
        }

        if (disposable == null) {
            disposable = new CompositeDisposable();
        }

        if (disposable.size() == 0) {
            Observable.interval(1,
                    TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable.add(d);
                        }

                        @Override
                        public void onNext(Long aLong) {
                            mediaPlayInfoListener.updProgress(getCurrentPosition());
                        }

                        @Override
                        public void onError(Throwable throwable) {
                        }

                        @Override
                        public void onComplete() {
                        }
                    });
        }

    }

    private void stopTimer() {
        if (disposable != null) {
            disposable.clear();
        }
    }

    private void bindVideoWithMp() {
        if (mMediaPlayer == null) {
            return;
        }

        if (!isVideoController) {
            mMediaPlayer.setDisplay(null);
            return;
        }

        if (mOnSizeChangedListener != null) {
            mMediaPlayer.setOnVideoSizeChangedListener(mOnSizeChangedListener);
        }

        mMediaPlayer.setScreenOnWhilePlaying(true);
    }

    IMediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    long getSeekWhenPrepared() {
        return mSeekWhenPrepared;

    }

    boolean targetStatePlaying() {
        return mTargetState == IjkMpController.STATE_PLAYING;
    }


    public void setMediaPlayInfoListener(IMediaPlayInfoListener mediaPlayInfoListener) {
        this.mediaPlayInfoListener = mediaPlayInfoListener;
    }


    public void setCanPause(boolean canPause) {
        mCanPause = canPause;
    }

    public void setUsingAndroidPlayer(boolean usingAndroidPlayer) {
        this.usingAndroidPlayer = usingAndroidPlayer;
    }

    public void setUsingMediaCodec(boolean usingMediaCodec) {
        this.usingMediaCodec = usingMediaCodec;
    }

    public void setUsingMediaCodecAutoRotate(boolean usingMediaCodecAutoRotate) {
        this.usingMediaCodecAutoRotate = usingMediaCodecAutoRotate;
    }

    public void setUsingOpenSLES(boolean usingOpen) {
        this.usingOpenSLES = usingOpen;
    }

    public void setPixelFormat(String pixelFormat) {
        this.pixelFormat = pixelFormat;
    }


    void setOnVideoSizeChange(IMediaPlayer.OnVideoSizeChangedListener mSizeChangedListener) {
        this.mOnSizeChangedListener = mSizeChangedListener;
    }

    public String getTextTime(int time) {
        int totalSeconds = time / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds)
                : String.format("%02d:%02d", minutes, seconds);
    }

}