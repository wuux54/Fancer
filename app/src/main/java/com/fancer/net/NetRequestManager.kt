package com.fancer.net

import androidx.lifecycle.LifecycleOwner
import app.util.LogUtils
import com.fancer.callback.NetRequestCallBack
import com.fancer.entity.response.KYHome
import com.fancer.net.api.Api
import com.fancer.net.observer.BaseObserver
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/30
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
class NetRequestManager {
    companion object {
        fun testAutoDisposable(owner: LifecycleOwner) {
            Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                .subscribe(object : io.reactivex.Observer<Long> {
                    override fun onComplete() {
                        LogUtils.i("model", "onComplete")
                    }

                    override fun onSubscribe(d: Disposable) {
                        LogUtils.i("model", "onSubscribe:d=$d")
                    }

                    override fun onNext(t: Long) {
                        LogUtils.i("model", "onNext:t=$t,")
                    }

                    override fun onError(e: Throwable) {
                        LogUtils.i("model", "onError=${e.message}")
                    }

                })

        }

        //RxJava:observable
        fun getKyHomeList(observer: Observer<KYHome>, owner: LifecycleOwner) {
            RetrofitManager.getInstance().getApiService(Api.HTTP_KY).kyHomeList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .`as`(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                .subscribe(observer)
        }


        //协程
        fun getKyHomeList2(callBack: NetRequestCallBack<KYHome>) {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val data = withContext(Dispatchers.IO) {
                        return@withContext RetrofitManager.getInstance().getApiService(Api.HTTP_KY)
                            .kyHomeList2().await()
                    }
                    callBack.success(data)

                } catch (e: Throwable) {
                    callBack.error(BaseObserver.errorResponse(e))
                }
            }
        }

        //协程
        fun getKyHomeListNext(url:String,callBack: NetRequestCallBack<KYHome>) {
            LogUtils.d("model",url)
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val data = withContext(Dispatchers.IO) {
                        return@withContext RetrofitManager.getInstance().getApiService(Api.HTTP_KY)
                            .kyHomeListNext(url).await()
                    }
                    callBack.success(data)

                } catch (e: Throwable) {
                    e.printStackTrace()
                    callBack.error(BaseObserver.errorResponse(e))
                }
            }
        }


    }
}