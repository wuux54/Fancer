package java.ui.activity;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import player.R;
import player.media.custom.IjkMpControlVideoView;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/7/13
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
public class MainActivity extends AppCompatActivity {
    private IjkMpControlVideoView ijkController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url ="https://live-s3m.mediav.com/nativevideo/301448-63439be8acb6fd727f79bbfd11849529-bit_cloud768.mp4";
        ijkController = findViewById(R.id.videoController);
        ijkController.setPath(url);
        ijkController.start();
    }
}
