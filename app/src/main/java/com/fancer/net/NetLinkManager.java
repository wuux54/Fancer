package com.fancer.net;


import androidx.lifecycle.LifecycleOwner;

import com.fancer.entity.response.KYHome;
import com.fancer.net.api.Api;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者: 凡星-fancer
 * 日期: 2019/7/17.
 * 邮箱: W_SpongeBob@163.com
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @author peiqi_pig
 */
public class NetLinkManager {
    public static void getKyHomeLis3t(Observer<KYHome> observer, LifecycleOwner owner) {
        RetrofitManager.getInstance().getApiService(Api.HTTP_KY).kyHomeList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(AutoDispose.<KYHome>autoDisposable(AndroidLifecycleScopeProvider.from(owner)))
                .subscribe(observer);
    }
}
