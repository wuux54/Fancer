package player.media;

import android.app.Application;

import com.danikula.videocache.HttpProxyCacheServer;

/**
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/8/11
 * @E-mail: W_SpongeBob@163.com
 * @Desc：Android 音视频媒体边播边缓存  单例
 */
public class MediaPlayCacheManager {
    /**
     * 单例获取
     */
    private volatile static MediaPlayCacheManager mcManager;
    private volatile static HttpProxyCacheServer proxyCacheServer;

    private Application appContext;

    private MediaPlayCacheManager(Application appContext) {
        this.appContext = appContext;
        createProxy();
    }

    private void createProxy() {
        proxyCacheServer = new HttpProxyCacheServer.Builder(appContext)
                // 1 Gb for cache
                .maxCacheSize(1024 * 1024 * 1024)
                .build();
    }

    public static synchronized MediaPlayCacheManager getInstance(Application appContext) {
        return newInstance(appContext);
    }

    private static synchronized MediaPlayCacheManager newInstance(Application appContext) {
        if (mcManager == null) {
            synchronized (MediaPlayCacheManager.class) {
                mcManager = new MediaPlayCacheManager(appContext);
            }
        }
        return mcManager;
    }

    public HttpProxyCacheServer getProxyCacheServer() {
        return proxyCacheServer;
    }
}
