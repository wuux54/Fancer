package com.fancer.net;

import com.fancer.MainApplication;
import com.fancer.net.api.Api;
import com.fancer.net.api.ApiService;
import com.fancer.net.convert.CustomGsonConverterFactory;
import com.fancer.net.ssl.SslContextFactory;
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.Authenticator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import app.util.LogUtils;
import app.util.NetUtils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.fancer.net.api.Api.URL_DEFAULT;


/**
 * 作者: 凡星-fancer
 * 日期: 2019/7/16.
 * 邮箱: W_SpongeBob@163.com
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @author peiqi
 * 可与LiveData结合，处理数据。
 */
public class RetrofitManager {
    private final int connectTimeout = 10;
    private final int readTimeout = 10;

    private final int imgConnectTimeout = 30;
    private final int imgReadTimeout = 30;

    private static volatile RetrofitManager retrofitManager;

    private static Map<String, Retrofit> retrofitMap = new HashMap<>();

    private static Map<String, ApiService> apiServiceMap = new HashMap<>();

    private RetrofitManager() {
    }

    private static synchronized void createManager() {

        if (null == retrofitManager) {
            retrofitManager = new RetrofitManager();
        }

    }

    private Retrofit getRetrofit(String baseUrl) {
        if (null == baseUrl || baseUrl.isEmpty()) {
            baseUrl = URL_DEFAULT;
        }

        if (!retrofitMap.containsKey(baseUrl)) {
            Retrofit retrofit = createRetrofit(baseUrl);
            retrofitMap.put(baseUrl, retrofit);
            return retrofit;
        }
        return retrofitMap.get(baseUrl);
    }


    private Retrofit createRetrofit(String baseUrl) {
        switch (baseUrl) {
            case Api.HTTP_KY:
                //Kotlin协程
                return new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addCallAdapterFactory(CoroutineCallAdapterFactory.create())
                        .addConverterFactory(CustomGsonConverterFactory.create())
                        .client(getOkHttpClient(baseUrl))
                        .build();
            case URL_DEFAULT:
            default:
                return new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(CustomGsonConverterFactory.create())
                        .client(getOkHttpClient(baseUrl))
                        .build();
        }

    }


    private OkHttpClient getOkHttpClient(String baseUrl) {
        return createNormalOkHttpClient();
    }


    private OkHttpClient createNormalOkHttpClient() {
        SSLSocketFactory sslSocketFactory = new SslContextFactory().getSslSocket(
                MainApplication.Companion.applicationContext()).getSocketFactory();
        return new OkHttpClient.Builder()
                //请求会回调一次
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                //请求和返回各会回调一次
                .addNetworkInterceptor(createNormalNetworkInterceptor())
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .cache(createNormalCache())
                //https 证书
                .sslSocketFactory(sslSocketFactory)
                //信任所有域名
                .hostnameVerifier(getHostnameVerifier())
//                .authenticator(createNormalAuthenticator())
                .build();
    }


    /**
     * 获取HostnameVerifier
     */
    private static HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                // true表示信任所有域名
                return true;
            }
        };
    }

    private Interceptor createNormalNetworkInterceptor() {

        Interceptor netInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();//获取请求
                //这里就是说判读我们的网络条件，要是有网络的话我么就直接获取网络上面的数据，要是没有网络的话我么就去缓存里面取数据
                if (!NetUtils.isConnected(MainApplication.Companion.applicationContext())) {
                    request = request.newBuilder()
                            //这个的话内容有点多啊，大家记住这么写就是只从缓存取，想要了解这个东西我等下在
                            // 给大家写连接吧。大家可以去看下，获取大家去找拦截器资料的时候就可以看到这个方面的东西反正也就是缓存策略。
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                    LogUtils.d("CacheInterceptor", "no network");
                }
                Response originalResponse = chain.proceed(request);
                if (NetUtils.isConnected(MainApplication.Companion.applicationContext())) {
                    //这里大家看点开源码看看.header .removeHeader做了什么操作很简答，就是的加字段和减字段的。
                    String cacheControl = request.cacheControl().toString();
                    return originalResponse.newBuilder()
                            //这里设置的为0就是说不进行缓存，我们也可以设置缓存时间
                            .header("Cache-Control", "public, max-age=" + 10)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    int maxTime = 4 * 24 * 60 * 60;
                    return originalResponse.newBuilder()
                            //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxTime)
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };

        return netInterceptor;
    }

    private Interceptor createNormalInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
//                        .header("Authorization", "")
                        .build();
                return chain.proceed(request);
            }
        };
    }

    private Authenticator createNormalAuthenticator() {

        return null;
    }


    private Cache createNormalCache() {
        File pigCache = new File(MainApplication.Companion.applicationContext().getCacheDir(), "peiQiPig");
        return new Cache(pigCache, 1024 * 1024);
    }




    /*------------------------------------------public-----------------------------------------------------*/

    public static RetrofitManager getInstance() {
        createManager();
        return retrofitManager;
    }

    public ApiService getDefaultApiService() {
        return getApiService(null);
    }

    public ApiService getApiService(String baseUrl) {
        if (null == baseUrl || baseUrl.isEmpty()) {
            baseUrl = URL_DEFAULT;
        }

        if (!apiServiceMap.containsKey(baseUrl)) {
            ApiService apiService = getRetrofit(baseUrl)
                    .create(ApiService.class);
            apiServiceMap.put(baseUrl, apiService);
            return apiService;
        }
        return apiServiceMap.get(baseUrl);
    }

    @NotNull
    public Retrofit getNewRetrofit(String baseUrls) {
        return new Retrofit.Builder().client(
                new OkHttpClient.Builder()
                        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                        .readTimeout(readTimeout, TimeUnit.SECONDS)
                        .build())
                .baseUrl(baseUrls)
                .addConverterFactory(CustomGsonConverterFactory.create())
                .build();
    }

}
