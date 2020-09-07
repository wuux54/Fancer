//package player.vrplayer.vr;
//
///**
// * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
// * 在任何艰难困苦的时刻它都不会离你而去。
// *
// * @Author: 凡星-fancer
// * @Date: 2020/8/10
// * @E-mail: W_SpongeBob@163.com
// * @Desc：
// */
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.SurfaceTexture;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.Surface;
//import android.view.TextureView;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//
//import androidx.annotation.Nullable;
//
//import player.R;
//import tv.danmaku.ijk.media.player.IMediaPlayer;
//
//
///**
// * Created by hzqiujiadi on 16/7/6.
// * hzqiujiadi ashqalcn@gmail.com
// */
//public class IjkPlayerDemoActivity extends Activity implements TextureView.SurfaceTextureListener {
//
//    private Surface surface;
//
//    private MediaPlayerWrapper mMediaPlayerWrapper = new MediaPlayerWrapper();
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // no title
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        // full screen
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        setContentView(R.layout.activity_ijkdemo);
//
//        mMediaPlayerWrapper.init();
//        mMediaPlayerWrapper.setPreparedListener(new IMediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(IMediaPlayer mp) {
//                cancelBusy();
//            }
//        });
//
//        TextureView textureView = (TextureView) findViewById(R.id.video_view);
//        textureView.setSurfaceTextureListener(this);
//
//        Uri uri = getUri();
//        uri = Uri.parse("file:///android_asset/test.mp4");
//        if (uri != null){
//            mMediaPlayerWrapper.openRemoteFile(uri.toString());
//            mMediaPlayerWrapper.prepare();
//        }
//
//    }
//
//    public static void start(Context context, Uri uri){
//        Intent i = new Intent(context,IjkPlayerDemoActivity.class);
//        i.setData(uri);
//        context.startActivity(i);
//    }
//
//    protected Uri getUri() {
//        Intent i = getIntent();
//        if (i == null || i.getData() == null){
//            return null;
//        }
//        return i.getData();
//    }
//
//    @Override
//    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
//        surface = new Surface(surfaceTexture);
//        mMediaPlayerWrapper.setSurface(surface);
//
//    }
//
//    @Override
//    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
//
//    }
//
//    @Override
//    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
//        mMediaPlayerWrapper.setSurface(null);
//        this.surface = null;
//        return true;
//    }
//
//    @Override
//    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mMediaPlayerWrapper.destroy();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mMediaPlayerWrapper.pause();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mMediaPlayerWrapper.resume();
//    }
//
//    public void cancelBusy(){
//        findViewById(R.id.progress).setVisibility(View.GONE);
//    }
//}
