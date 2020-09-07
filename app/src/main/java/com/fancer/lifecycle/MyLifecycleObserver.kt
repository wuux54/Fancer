package com.fancer.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/23
 * @E-mail: W_SpongeBob@163.com
 * @Desc：全局生命周期监听，base类中调用
 */
class MyLifecycleObserver : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {

    }


    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {

    }
}