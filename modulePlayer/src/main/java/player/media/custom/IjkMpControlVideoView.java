package player.media.custom;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.danikula.videocache.HttpProxyCacheServer;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import app.ui.dialog.LoadDialogManager;
import app.util.LogUtils;
import app.util.ToastUtil;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import player.R;
import player.listener.VideoViewListener;
import player.media.MediaPlayCacheManager;
import player.media.ijk.IMediaController;
import player.media.ijk.IMediaPlayInfoListener;
import player.ui.anim.AnimUtil;

import static player.media.custom.IjkMpController.STATE_PAUSED;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/7/23
 * @E-mail: W_SpongeBob@163.com
 * @Desc： 带控制器的ijkVideoController
 */
@SuppressWarnings("LambdaCanBeReplacedWithAnonymous")
public class IjkMpControlVideoView extends FrameLayout implements IMediaController {
    IjkMpVideoView ijkMpVideoView;
    ConstraintLayout viewVideoController;

    private FrameLayout flLeft;
    private FrameLayout flTop;
    private FrameLayout flRight;
    private FrameLayout flBottom;
    private FrameLayout flCenter;
    private ImageView ivBitmap;

    private SeekBar videoSeekBar;
    private TextView tvProgress;
    private TextView tvDuration;

    private ImageButton btnPlay;
    private ImageButton btnNext;

    private boolean seekIsTouch = false;

    private LoadDialogManager loadDialogManager;
    private CompositeDisposable disposable = new CompositeDisposable();

    IMediaPlayInfoListener iMediaPlayInfoListener = new IMediaPlayInfoListener() {
        @Override
        public void prepareStart() {
            showLoading(true);
        }

        @Override
        public void startPlay() {
            btnPlay.setSelected(true);
        }

        @Override
        public void pausePlay() {
            btnPlay.setSelected(false);
        }

        @Override
        public void prepareFinish() {
            tvDuration.setText(ijkMpVideoView.getTextTime(ijkMpVideoView.getDuration()));
            videoSeekBar.setMax(ijkMpVideoView.getDuration());
            showLoading(false);
            intervalHide();
        }

        @Override
        public void completePlay() {
            videoSeekBar.setProgress(ijkMpVideoView.getDuration());
            showLoading(false);
            btnPlay.setSelected(false);
        }

        @Override
        public void errorPlay(String errorMsg) {
            showLoading(false);
            btnPlay.setSelected(false);
        }

        @Override
        public void updProgress(int currProgress) {
            if (!seekIsTouch) {
                videoSeekBar.setProgress(currProgress);
            }
        }

        @Override
        public void updBufferProgress(int percent) {
            if (percent > 98) {
                percent = 100;
            }
            videoSeekBar.setSecondaryProgress((int) ((percent / 100f) * ijkMpVideoView.getDuration()));
        }

        @Override
        public void showLoading(boolean isShow) {
            if (isShow) {
                loadDialogManager.showDefProgress();
            } else {
                loadDialogManager.hideDialog();
            }
        }
    };
    private HttpProxyCacheServer proxyCacheServer;

    public IjkMpControlVideoView(Context context) {
        super(context);
        init();
    }

    public IjkMpControlVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IjkMpControlVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IjkMpControlVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        initView();
        bindView();
        initData();
        initEvent();
    }

    private void initData() {
        loadDialogManager = LoadDialogManager.newInstance(getContext());

        proxyCacheServer = MediaPlayCacheManager.
                getInstance((Application) getContext().getApplicationContext())
                .getProxyCacheServer();
    }

    private void initView() {
        viewVideoController = (ConstraintLayout) View.inflate(getContext(), R.layout.view_player, null);

        ijkMpVideoView = viewVideoController.findViewById(R.id.ijkVideo);

        flLeft = viewVideoController.findViewById(R.id.fl_left);
        flTop = viewVideoController.findViewById(R.id.fl_top);
        flRight = viewVideoController.findViewById(R.id.fl_right);
        flBottom = viewVideoController.findViewById(R.id.fl_bottom);
        flCenter = viewVideoController.findViewById(R.id.fl_center);
        ivBitmap = viewVideoController.findViewById(R.id.iv_bitmap);

        videoSeekBar = flBottom.findViewById(R.id.videoSeekBar);
        tvProgress = flBottom.findViewById(R.id.tvProgress);
        tvDuration = flBottom.findViewById(R.id.tvDuration);

        btnPlay = flBottom.findViewById(R.id.btnPlay);
        btnNext = flBottom.findViewById(R.id.btnNext);

        btnNext.setVisibility(GONE);
        ivBitmap.setVisibility(GONE);
    }

    private void initEvent() {
        ijkMpVideoView.setMediaController(this);

        videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //展示拖拽图片
                    //这里progress 是毫秒，而帧获取需要微秒，所以*1000
                    ijkMpVideoView.setBitMap(progress * 1000, ivBitmap);
                }
                tvProgress.setText(ijkMpVideoView.getTextTime(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekIsTouch = true;
                ivBitmap.setVisibility(VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!ijkMpVideoView.isPlaying()) {
                    ijkMpVideoView.start();
                }
                ijkMpVideoView.seekTo(seekBar.getProgress());
                seekIsTouch = false;
                ivBitmap.setVisibility(GONE);
            }
        });

        btnPlay.setOnClickListener(v -> {
            if (ijkMpVideoView.isPlaying()) {
                ijkMpVideoView.pause();
                btnPlay.setSelected(false);
            } else {
                ijkMpVideoView.start();
                btnPlay.setSelected(true);
            }
        });

        ijkMpVideoView.setMediaPlayInfoListener(iMediaPlayInfoListener);

        ijkMpVideoView.setVideoViewListener(new VideoViewListener() {
            @Override
            public void videoViewScroll(boolean isRight, IjkMpVideoView.Position startEvent, IjkMpVideoView.Position endEvent) {

            }

            @Override
            public void videoViewScrollX(boolean isRight, float distance) {
                int width = ijkMpVideoView.getWidth();
                DecimalFormat decimalFormat = new DecimalFormat("##0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                String p = decimalFormat.format(distance / width);//format 返回的是字符串
                ToastUtil.showToastViewNor("distance x percent --> " + p);
            }

            @Override
            public void videoViewScrollY(boolean isRight, float distance) {
                int height = ijkMpVideoView.getHeight();
                DecimalFormat decimalFormat = new DecimalFormat("##0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
                String p = decimalFormat.format(distance / height);//format 返回的是字符串
                ToastUtil.showToastViewNor("distance y percent --> " + p);
            }

            @Override
            public void videoViewClick() {

            }
        });
    }

    private void bindView() {
        addView(viewVideoController);
    }


    private void showCtv(boolean needShow) {
        AnimUtil.videoCtvSwitchY(flTop, needShow, true);
        AnimUtil.videoCtvSwitchY(flBottom, needShow, false);
        AnimUtil.videoCtvSwitchX(flLeft, needShow, true);
        AnimUtil.videoCtvSwitchX(flRight, needShow, false);
    }


    public void setPath(String url) {
        String proxyUrl = proxyCacheServer
                .getProxyUrl(url);

        LogUtils.d("URL",proxyUrl);
        ijkMpVideoView.setPath(proxyUrl);
    }


    public void start() {
        ijkMpVideoView.start();
    }

    public void pause() {
        ijkMpVideoView.pause();
    }

    public void releaseMedia() {
        ijkMpVideoView.releaseMedia();
        disposable.clear();
    }

    public boolean isPlaying() {
        return ijkMpVideoView.isPlaying();
    }

    @Override
    public void hide() {
        showCtv(false);
    }

    @Override
    public boolean isShowing() {
        return flTop.getVisibility() == View.VISIBLE;
    }

    @Override
    public void setAnchorView(View view) {

    }

    @Override
    public void setMediaPlayer(MediaController.MediaPlayerControl player) {

    }

    @Override
    public void show(int timeout) {

    }


    @Override
    public void show() {
        showCtv(true);
        intervalHide();
    }

    //倒计时隐藏
    private void intervalHide() {
        disposable.clear();
        disposable.add(Flowable.intervalRange(0, 3 + 1, 0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    if (!isShowing()
                            || !isPlaying()
                            || ijkMpVideoView.getCurrentState() == STATE_PAUSED) {
                        return;
                    }
                    hide();
                })
                .subscribe());
    }

    @Override
    public void showOnce(View view) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (isShowing()) {
                intervalHide();
            }
        } else if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            disposable.clear();
        }
        return super.dispatchTouchEvent(ev);

    }
}
