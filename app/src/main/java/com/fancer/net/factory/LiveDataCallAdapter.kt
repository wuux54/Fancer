package com.fancer.net.factory

import androidx.lifecycle.LiveData
import com.fancer.entity.response.HttpResult
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 *
 * 代码是最为耐心、最能忍耐和最令人愉快的伙伴，
 * 在任何艰难困苦的时刻它都不会离你而去。
 *
 * @Author: 凡星-fancer
 * @Date: 2020/6/24
 * @E-mail: W_SpongeBob@163.com
 * @Desc：
 */
class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<HttpResult<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<HttpResult<R>> {
        return object : LiveData<HttpResult<R>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
//                            postValue(HttpResult.create(response))
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
//                            postValue(HttpResult.create(throwable))
                        }
                    })
                }
            }
        }
    }
}