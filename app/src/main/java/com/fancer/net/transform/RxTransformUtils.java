package com.fancer.net.transform;


import com.fancer.entity.response.HttpResult;
import com.fancer.net.exception.RetrofitErrorException;

import app.util.LogUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 凡星-fancer
 * 日期: 2019/7/18.
 * 邮箱: W_SpongeBob@163.com
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @author peiqi_pig
 */
public class RxTransformUtils {

    private RxTransformUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static final String TAG = "RxTransformUtils";

    public static <T> ObservableTransformer<T, T> defaultTransformSchedule() {

        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    public static <T> ObservableTransformer<T, T> ioTransformSchedule() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * -------------------------------统一返回处理------------------------------------------
     */
    private static <T> ObservableSource<T> createNewObservable(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(data);
                    emitter.onComplete();
                } catch (Exception e) {
                    LogUtils.e(TAG, "error: " + e.getMessage());
                    emitter.onError(e);
                }
            }
        });
    }

    public static <T> ObservableTransformer<HttpResult<T>, T> responseDataSchedule() {
        return new ObservableTransformer<HttpResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<HttpResult<T>> upstream) {
                return upstream.flatMap(new Function<HttpResult<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(HttpResult<T> result) throws Exception {
                        LogUtils.d(TAG, "apply: " + result.toString());
                        if (result.isSuccess()) {
                            return createNewObservable(result.getData());
                        } else {
                            return Observable.error(new RetrofitErrorException(result.getCode(), result.getMsg()));
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    //        return upstream -> upstream.flatMap(result -> {
//            if (result.isDataSuccess()) {
//                return createNewObservable(result.getData());
//            } else {
//                return Observable.error(new RetrofitErrorException(result.getCode(),result.getMsg()));
//            }
//        }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
//        }

    public static <T extends HttpResult> ObservableTransformer<T, T> responseSchedule() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.flatMap(new Function<T, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(T result) throws Exception {
                        if (result.isSuccess()) {
                            return createNewObservable(result);
                        } else {
                            return Observable.error(new RetrofitErrorException(result.getCode(), result.getMsg()));
                        }
                    }
                }).
                        subscribeOn(Schedulers.io()).
                        unsubscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread());
            }
        };
//                result -> {
//                    if (result.isDataSuccess()) {
//                        return createNewObservable(result);
//                    } else {
//                        return Observable.error(new RetrofitErrorException(result.getCode(), result.getMsg()));
//                    }
//                }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }



    public static <T> ObservableTransformer<HttpResult<T>, T> responseEncryptDataSchedule() {
        return new ObservableTransformer<HttpResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<HttpResult<T>> upstream) {
                return upstream.flatMap(new Function<HttpResult<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(HttpResult<T> result) throws Exception {
                        LogUtils.d(TAG, "apply: " + result.toString());
                        if (result.isSuccess()) {
                            return createNewObservable(result.getData());
                        } else {
                            return Observable.error(new RetrofitErrorException(result.getCode(), result.getMsg()));
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T extends HttpResult> ObservableTransformer<T, T> responseEncryptSchedule() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.flatMap(new Function<T, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(T result) throws Exception {
                        if (result.isSuccess()) {
                            return createNewObservable(result);
                        } else {
                            return Observable.error(new RetrofitErrorException(result.getCode(), result.getMsg()));
                        }
                    }
                }).
                        subscribeOn(Schedulers.io()).
                        unsubscribeOn(Schedulers.io()).
                        observeOn(AndroidSchedulers.mainThread());
            }
        };
//                result -> {
//                    if (result.isDataSuccess()) {
//                        return createNewObservable(result);
//                    } else {
//                        return Observable.error(new RetrofitErrorException(result.getCode(), result.getMsg()));
//                    }
//                }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    }
