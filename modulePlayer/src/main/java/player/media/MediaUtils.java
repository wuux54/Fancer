package player.media;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.widget.ImageView;

import java.io.File;
import java.util.HashMap;

import wseemann.media.FFmpegMediaMetadataRetriever;

import static android.media.MediaMetadataRetriever.OPTION_CLOSEST_SYNC;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/8/5
 * @E-mail: W_SpongeBob@163.com
 * @Desc： 获取MediaVideo的缩略图。
 */
public class MediaUtils {

    /**
     * 点击按钮，获取视频缩略图
     */
    public void getVideoInfo(ImageView imageView, String url,long time) {
        Bitmap videoThumbnail = null;

        //获取本地视频缩略图，在sdk根目录下准备一个test.mp4的文件
//        String videoPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.mp4";
//         videoThumbnail = getVideoThumbnail(videoPath);

        //获取网络视频缩略图
        videoThumbnail = getNetVideoThumbnail(url,time);

        imageView.setImageBitmap(videoThumbnail);

    }

    /**
     * 获取本地视频缩略图
     */
    public Bitmap getVideoThumbnail(String filePath) {
        Bitmap b=null;
        //使用MediaMetadataRetriever
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        //FFmpegMediaMetadataRetriever
        FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();
        File file = new File(filePath);
        try {

            retriever.setDataSource(file.getPath());
            b=retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return b;
    }


    /**
     * 获取网络视频缩略图
     */
    public Bitmap getNetVideoThumbnail(String url,long time) {
        Bitmap b=null;
        //使用MediaMetadataRetriever
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        //FFmpegMediaMetadataRetriever
        FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();

        try {
            retriever.setDataSource(url,new HashMap<String, String>());
            b=retriever.getFrameAtTime(time,FFmpegMediaMetadataRetriever.OPTION_CLOSEST);
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return b;
    }


    public Bitmap getVideoBitmap(String url,long time){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(url);
        return mmr.getFrameAtTime(time, OPTION_CLOSEST_SYNC);
    }


}
