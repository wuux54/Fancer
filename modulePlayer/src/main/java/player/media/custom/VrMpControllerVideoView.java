package player.media.custom;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;

import player.R;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/8/7
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class VrMpControllerVideoView extends FrameLayout {

    /**
     * 播放360度全景视频的的控件
     */
    private VrVideoView vr_video_view;
    /**
     * 拖动进度的进度条
     */
    private SeekBar seek_bar;
    /**
     * 声音开关
     */
    private ImageButton volume_toggle;
    /**
     * 播放按钮
     */
    private ImageButton play_toggle;

    /**
     * 声音是否开启
     */
    private boolean isMuted;
    /**
     * 播放暂停
     */
    private boolean isPlay = true;
    /**
     * 打印调试的TAG
     */
    private final String TAG = "VrVideoView";
    private View vrVideoViewCtl;
    private VrVideoView.Options options;

    public VrMpControllerVideoView(@NonNull Context context) {
        super(context);
        init();
    }

    public VrMpControllerVideoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VrMpControllerVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VrMpControllerVideoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {

        initView();
        initEvent();
        bindView();

        initData();
    }

    private void bindView() {
        this.addView(vrVideoViewCtl);
    }

    private void initData() {
        /*
         * 设置加载设置
         */
        options = new VrVideoView.Options();
        options.inputType = VrVideoView.Options.TYPE_STEREO_OVER_UNDER;

    }

    private void initEvent() {
        /*
         * 设置加载监听
         */
        vr_video_view.setEventListener(new VrVideoEventListener() {
            /**
             * 视频播放完成回调
             */
            @Override
            public void onCompletion() {
                super.onCompletion();
                /*播放完成后跳转到开始重新播放**/
                vr_video_view.seekTo(0);
                setIsPlay(false);
                Log.d(TAG, "onCompletion()");
            }

            /**
             * 加载每一帧视频的回调
             */
            @Override
            public void onNewFrame() {
                super.onNewFrame();
                seek_bar.setProgress((int) vr_video_view.getCurrentPosition());
                Log.d(TAG, "onNewFrame()");
            }

            /**
             * 点击VR视频回调
             */
            @Override
            public void onClick() {
                super.onClick();
                Log.d(TAG, "onClick()");
            }

            /**
             * 加载VR视频失败回调
             * @param errorMessage
             */
            @Override
            public void onLoadError(String errorMessage) {
                super.onLoadError(errorMessage);
                Log.d(TAG, "onLoadError()->errorMessage=" + errorMessage);
            }

            /**
             * 加载VR视频成功回调
             */
            @Override
            public void onLoadSuccess() {
                super.onLoadSuccess();
                /*加载成功后设置回调**/
                seek_bar.setMax((int) vr_video_view.getDuration());
                Log.d(TAG, "onNewFrame()");
            }

            /**
             * 显示模式改变回调
             * 1.默认
             * 2.全屏模式
             * 3.VR观看模式，即横屏分屏模式
             * @param newDisplayMode 模式
             */
            @Override
            public void onDisplayModeChanged(int newDisplayMode) {
                super.onDisplayModeChanged(newDisplayMode);
                Log.d(TAG, "onDisplayModeChanged()->newDisplayMode=" + newDisplayMode);
            }
        });

        /*设置声音按钮点击监听**/
        volume_toggle.setOnClickListener(v -> setIsMuted(!isMuted));
        /*设置播放暂停按钮点击监听**/
        play_toggle.setOnClickListener(v -> setIsPlay(!isPlay));

        /*设置进度条拖动监听**/
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * 进度条拖动改变监听
             * @param seekBar 拖动条
             * @param progress 进度
             * @param fromUser 是否是用户手动操作的
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    /*调节视频进度**/
                    vr_video_view.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initView() {
        vrVideoViewCtl = View.inflate(getContext(), R.layout.view_video_vr_controller, null);
        vr_video_view = vrVideoViewCtl.findViewById(R.id.vr_video_view);
        seek_bar = vrVideoViewCtl.findViewById(R.id.seek_bar);
        volume_toggle = vrVideoViewCtl.findViewById(R.id.volume_toggle);
        play_toggle = vrVideoViewCtl.findViewById(R.id.play_toggle);

        vr_video_view.setFullscreenButtonEnabled(false);
        vr_video_view.setInfoButtonEnabled(false);
        vr_video_view.setStereoModeButtonEnabled(false);
    }

    /**
     * 设置声音开关
     *
     * @param isMuted 开关
     */
    private void setIsMuted(boolean isMuted) {
        this.isMuted = isMuted;
        volume_toggle.setImageResource(isMuted ? R.drawable.volume_off : R.drawable.volume_on);
        vr_video_view.setVolume(isMuted ? 0.0f : 1.0f);
    }

    /**
     * 设置播放暂停
     *
     * @param isPlay 播放暂停
     */
    public void setIsPlay(boolean isPlay) {
        if (this.isPlay == isPlay) {
            return;
        }

        this.isPlay = isPlay;
        play_toggle.setImageResource(isPlay ? R.drawable.pause : R.drawable.play);
        if (isPlay) {
            vr_video_view.playVideo();
        } else {
            vr_video_view.pauseVideo();
        }
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setNetPath(String url) {
        try {
            /*加载VR视频**/
            vr_video_view.loadVideo(Uri.parse(url), options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAssetPath(String fileName) {
        try {
            /*加载VR视频**/
            vr_video_view.loadVideoFromAsset(fileName, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
